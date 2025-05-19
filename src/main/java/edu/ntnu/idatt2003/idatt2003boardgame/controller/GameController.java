package edu.ntnu.idatt2003.idatt2003boardgame.controller;

import java.util.List;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Board;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Tile;
import edu.ntnu.idatt2003.idatt2003boardgame.model.dice.Dice;
import edu.ntnu.idatt2003.idatt2003boardgame.util.LoopingIterator;
import edu.ntnu.idatt2003.idatt2003boardgame.model.observer.GameModel;
import edu.ntnu.idatt2003.idatt2003boardgame.model.observer.GameModelObserver;

public class GameController {

    private GameViewController gameViewController;
    private final GameModel gameModel = new GameModel();

    private final Board board;
    private final List<Tile> tiles;
    private final List<Player> players;
    private final Dice dice;
    private Player playerWhoseTurn;
    private final LoopingIterator<Player> playerIterator;
    private Player playerToSkip = null;
    private Player winner = null;
    private boolean finished = false;

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
        gameModel.fireTurnChanged(playerWhoseTurn);
    }

    public void movePlayer(Player player, int tileNumber) {
        tiles.get(player.getPosition() - 1).popPlayer();
        player.setPosition(tileNumber);
        Tile targetTile = tiles.get(tileNumber - 1);
        targetTile.addPlayer(player);
        gameModel.firePlayerMoved(player, tileNumber);
        if (targetTile.getEffect() != null) {
            targetTile.getEffect().execute(player, this);
        }
    }

    public void moveBy(Player player, int steps) {
        movePlayer(player, player.getPosition() + steps);
    }

    public void handleRollDice() {
        int diceRoll = dice.roll();
        gameModel.fireDiceRolled(diceRoll);
        moveBy(playerWhoseTurn, diceRoll);
        if (playerWhoseTurn.getPosition() >= board.getTileCount()) {
            winner = playerWhoseTurn;
            finished = true;
            gameModel.fireGameWon(playerWhoseTurn);
        }
        if (!finished) {
            advanceTurn();
        }
    }

    public void advanceTurn() {
        playerWhoseTurn = playerIterator.next();
        if (playerToSkip != null && playerToSkip.equals(playerWhoseTurn)) {
            playerToSkip = null;
            playerWhoseTurn = playerIterator.next();
        }
        gameModel.fireTurnChanged(playerWhoseTurn);
    }

    public void markPlayerToSkip(Player player) {
        playerToSkip = player;
    }

    public Player getCurrentPlayer() {
        return playerWhoseTurn;
    }

    public GameModel getModel() {
        return gameModel;
    }

    public void setVisualController(GameViewController takenGameViewController) {
        gameViewController = takenGameViewController;
    }

    public boolean isFinished() {
        return finished;
    }

    public Player getWinner() {
        return winner;
    }

    public void addObserver(GameModelObserver observer) {
        gameModel.addListener(observer);
    }

    public void removeObserver(GameModelObserver observer) {
        gameModel.removeListener(observer);
    }
}
