package edu.ntnu.idatt2003.idatt2003boardgame.controller;
import java.util.List;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Board;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Tile;
import edu.ntnu.idatt2003.idatt2003boardgame.model.dice.Dice;
import edu.ntnu.idatt2003.idatt2003boardgame.util.LoopingIterator;

public class GameController {

    private VisualController visualController;

    private final Board board;
    private final List<Tile> tiles;
    private final List<Player> players;
    private final Dice dice;
    private Player playerWhoseTurn;
    private final LoopingIterator<Player> playerIterator;
;
    private Player playerToSkip = null;

    public GameController(Board board, List<Player> players) {
        this.board = board;
        this.tiles = board.getTiles();
        this.players = players;
        this.playerIterator = new LoopingIterator<>(players);
        this.dice = new Dice(1);
        this.playerWhoseTurn = playerIterator.next();

    }

    public void start() {    
        for (Player player : players) {
            board.getTiles().get(0).addPlayer(player);
        }

    }

    public void movePlayer(Player player, int tileNumber) {
        tiles.get(player.getPosition() - 1).popPlayer();

        player.setPosition(tileNumber);
        Tile targetTile = tiles.get(tileNumber - 1);
        targetTile.addPlayer(player);

        if (!(targetTile.getEffect() == null)) {
            targetTile.getEffect().execute(player, this);
        }

        visualController.updateEntireBoard();
    }

    public void moveBy(Player player, int steps) {
        int nextPosition = player.getPosition() + steps;
        movePlayer(player, nextPosition);

    }

    public void handleRollDice() {
        int diceRoll = dice.roll();
        moveBy(playerWhoseTurn, diceRoll); 
        visualController.displayRoll(diceRoll);
        advanceTurn();
        
    }

    public void markPlayerToSkip(Player player) {
        playerToSkip = player;
    }

    public Player getCurrentPlayer() {
        return playerWhoseTurn;
    }
    
    public void advanceTurn() {
        playerWhoseTurn = playerIterator.next();

        if (playerToSkip != null && playerToSkip.equals(playerWhoseTurn)) {
            playerToSkip = null;
            playerWhoseTurn = playerIterator.next();
        }
    }

    public void setVisualController(VisualController takenVisualController) {
        visualController = takenVisualController; 
    }
    
}
