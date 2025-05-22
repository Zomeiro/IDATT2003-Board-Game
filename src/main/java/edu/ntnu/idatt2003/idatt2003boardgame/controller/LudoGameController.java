package edu.ntnu.idatt2003.idatt2003boardgame.controller;

import edu.ntnu.idatt2003.idatt2003boardgame.model.*;
import edu.ntnu.idatt2003.idatt2003boardgame.model.dice.Dice;
import edu.ntnu.idatt2003.idatt2003boardgame.util.LoopingIterator;
import edu.ntnu.idatt2003.idatt2003boardgame.model.observer.GameModel;
import edu.ntnu.idatt2003.idatt2003boardgame.model.observer.GameModelObserver;

import java.util.*;

public class LudoGameController {

    private final GameModel model = new GameModel();
    private final Dice dice = new Dice(1);
    
    private final LudoBoard board;
    private final List<LudoPlayer> players;
    private final Map<String, List<Integer>> colorPaths;
    private final LoopingIterator<LudoPlayer> playerIterator;
    
    private LudoPlayer currentPlayer;
    private boolean gameFinished = false;
    private int consecutiveSixes = 0;

    public LudoGameController(LudoBoard board, List<LudoPlayer> players) {
        this.board = board;
        this.players = players;
        this.colorPaths = board.getColorPaths();
        this.playerIterator = new LoopingIterator<>(players);
        this.currentPlayer = playerIterator.next();
        
        // Initialize pieces to be in yard (LudoPlayer constructor already creates pieces)
        initializePieces();
    }
    
    private void initializePieces() {
        // LudoPlayer already creates 4 pieces in constructor, just need to set them to yard
        for (LudoPlayer player : players) {
            for (LudoPiece piece : player.getPieces()) {
                piece.setPosition(-1); // Start in yard
            }
        }
    }

    public void start() {
        model.fireTurnChanged(currentPlayer);
    }

    public void handleRoll() {
        if (gameFinished) return;

        int roll = dice.roll();
        model.fireDiceRolled(roll);
        
        // Handle three consecutive sixes rule
        if (roll == 6) {
            consecutiveSixes++;
            if (consecutiveSixes >= 3) {
                // Skip turn on third consecutive six
                consecutiveSixes = 0;
                advanceTurn();
                return;
            }
        } else {
            consecutiveSixes = 0;
        }

        boolean moved = tryMovePiece(currentPlayer, roll);
        
        if (hasPlayerWon(currentPlayer)) {
            model.fireGameWon(currentPlayer);
            gameFinished = true;
            return;
        }

        // Roll again on 6, otherwise advance turn
        if (roll != 6) {
            advanceTurn();
        } else {
            model.fireTurnChanged(currentPlayer);
        }
    }
    
    private void advanceTurn() {
        currentPlayer = playerIterator.next();
        consecutiveSixes = 0;
        model.fireTurnChanged(currentPlayer);
    }

    private boolean tryMovePiece(LudoPlayer player, int roll) {
        List<LudoPiece> pieces = player.getPieces();
        
        // If roll is 6, try to spawn a piece from yard first
        if (roll == 6) {
            for (LudoPiece piece : pieces) {
                if (piece.isInYard()) {
                    spawnPiece(piece);
                    return true;
                }
            }
        }
        
        // Try to move an existing piece
        for (LudoPiece piece : pieces) {
            if (!piece.isInYard() && !piece.isFinished()) {
                int newPosition = piece.getPosition() + roll;
                if (newPosition <= 56) { // 56 is the last position before finish (57+ is finished)
                    movePiece(piece, newPosition);
                    return true;
                }
            }
        }
        
        return false; // No valid moves
    }
    
    private void spawnPiece(LudoPiece piece) {
        piece.setPosition(0); // Start position on the path
        
        // Get the actual tile index for start position
        LudoColor color = piece.getColor();
        String colorString = color.toString(); // Convert enum to string for map lookup
        List<Integer> path = colorPaths.get(colorString);
        int startTileIndex = path.get(4); // Index 4 is start tile (after 4 home tiles)
        
        // Check for knockout at start position
        checkKnockout(piece, startTileIndex);
        
        // Place piece on board
        Tile startTile = board.getTiles().get(startTileIndex);
        startTile.addPiece(piece);
        
        model.firePieceMoved(piece, startTileIndex);
    }
    
    private void movePiece(LudoPiece piece, int newPosition) {
        LudoColor color = piece.getColor();
        String colorString = color.toString(); // Convert enum to string for map lookup
        List<Integer> path = colorPaths.get(colorString);
        
        // Remove from current tile
        int oldTileIndex = getTileIndexForPosition(piece, piece.getPosition());
        if (oldTileIndex >= 0) {
            board.getTiles().get(oldTileIndex).removePiece(piece);
        }
        
        // Update position
        piece.setPosition(newPosition);
        
        // Get new tile index
        int newTileIndex = getTileIndexForPosition(piece, newPosition);
        
        if (newTileIndex >= 0 && !piece.isFinished()) {
            // Check for knockout
            checkKnockout(piece, newTileIndex);
            
            // Place on new tile
            Tile newTile = board.getTiles().get(newTileIndex);
            newTile.addPiece(piece);
        }
        
        model.firePieceMoved(piece, newTileIndex);
    }
    
    private int getTileIndexForPosition(LudoPiece piece, int position) {
        if (position < 0) return -1; // In yard
        if (position > 56) return -1; // Finished
        
        LudoColor color = piece.getColor();
        String colorString = color.toString(); // Convert enum to string for map lookup
        List<Integer> path = colorPaths.get(colorString);
        
        // Position 0-50 are main loop + start (indices 4-54 in path)
        // Position 51-56 are center path (indices 55-60 in path)
        if (position + 4 < path.size()) {
            return path.get(position + 4);
        }
        
        return -1; // Finished or invalid
    }
    
    private void checkKnockout(LudoPiece movingPiece, int tileIndex) {
        Tile tile = board.getTiles().get(tileIndex);
        
        // Check if there are opponent pieces on this tile
        List<LudoPiece> piecesToRemove = new ArrayList<>();
        for (LudoPiece existingPiece : tile.getPieces()) {
            if (!existingPiece.getOwner().equals(movingPiece.getOwner())) {
                piecesToRemove.add(existingPiece);
            }
        }
        
        // Send knocked out pieces back to yard
        for (LudoPiece piece : piecesToRemove) {
            tile.removePiece(piece);
            piece.setPosition(-1); // Back to yard
        }
    }
    
    private boolean hasPlayerWon(LudoPlayer player) {
        List<LudoPiece> pieces = player.getPieces();
        for (LudoPiece piece : pieces) {
            if (!piece.isFinished()) {
                return false;
            }
        }
        return true;
    }
    
    // Public getters and methods
    public LudoPlayer getCurrentPlayer() {
        return currentPlayer;
    }
    
    public List<LudoPiece> getPlayerPieces(LudoPlayer player) {
        return player.getPieces();
    }
    
    public boolean canPlayerMove(LudoPlayer player, int roll) {
        List<LudoPiece> pieces = player.getPieces();
        
        // Can spawn on 6
        if (roll == 6) {
            for (LudoPiece piece : pieces) {
                if (piece.isInYard()) return true;
            }
        }
        
        // Can move existing pieces
        for (LudoPiece piece : pieces) {
            if (!piece.isInYard() && !piece.isFinished()) {
                int newPos = piece.getPosition() + roll;
                if (newPos <= 56) return true; // 56 is the last valid position
            }
        }
        
        return false;
    }

    public void addObserver(GameModelObserver observer) {
        model.addListener(observer);
    }

    public void removeObserver(GameModelObserver observer) {
        model.removeListener(observer);
    }

    public GameModel getModel() {
        return model;
    }

    public boolean isFinished() {
        return gameFinished;
    }
    
    public LudoBoard getBoard() {
        return board;
    }
    
    public List<LudoPlayer> getPlayers() {
        return players;
    }
}