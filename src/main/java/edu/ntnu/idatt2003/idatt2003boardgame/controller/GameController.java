package edu.ntnu.idatt2003.idatt2003boardgame.controller;

import java.util.List;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Board;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Tile;
import edu.ntnu.idatt2003.idatt2003boardgame.model.dice.Dice;
import edu.ntnu.idatt2003.idatt2003boardgame.util.LoopingIterator;
import edu.ntnu.idatt2003.idatt2003boardgame.model.observer.GameModel;
import edu.ntnu.idatt2003.idatt2003boardgame.model.observer.GameModelObserver;

/**
 * Manages the game logic, player turns, movements, and interactions
 * with the game board for a board game session. It notifies observers of game events.
 * 
 * @author Bjørn Adam Vangen
 */
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

    /**
     * Constructs a GameController instance.
     *
     * @param board The game board.
     * @param players The list of players participating in the game.
     */
    public GameController(Board board, List<Player> players) {
        this.board = board;
        this.tiles = board.getTiles();
        this.players = players;
        this.playerIterator = new LoopingIterator<>(players);
        this.dice = new Dice(1);
        this.playerWhoseTurn = playerIterator.next();
    }

    /**
     * Starts the game by placing players on the initial tile and
     * signaling the first player's turn.
     */
    public void start() {
        for (Player player : players) {
            board.getTiles().get(0).addPlayer(player);
        }
        gameModel.fireTurnChanged(playerWhoseTurn);
    }

    /**
     * Moves a player to a specific tile number.
     * This involves removing the player from their current tile, updating their position,
     * adding them to the new tile, firing a moved event, and executing any effect on the new tile.
     *
     * @param player The player to move.
     * @param tileNumber The 1-based tile number to move the player to.
     */
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

    /**
     * Moves a player by a given number of steps from their current position.
     *
     * @param player The player to move.
     * @param steps The number of steps to move.
     */
    public void moveBy(Player player, int steps) {
        movePlayer(player, player.getPosition() + steps);
    }

    /**
     * Handles the action of a player rolling the dice.
     * It determines the dice outcome, calculates the new position,
     * checks for a win condition, moves the player, and advances the turn.
     */
    public void handleRollDice() {
        int diceRoll = dice.roll();
        gameModel.fireDiceRolled(diceRoll);

        int nextPosition = playerWhoseTurn.getPosition() + diceRoll;

        if (nextPosition >= board.getTileCount()) {
            winner = playerWhoseTurn;
            finished = true;
            gameModel.fireGameWon(playerWhoseTurn);
            return;
        }

        moveBy(playerWhoseTurn, diceRoll);
        advanceTurn();
    }

    /**
     * Advances the game to the next player's turn.
     * If a player is marked to be skipped, their turn is skipped,
     * and the turn moves to the subsequent player.
     */
    public void advanceTurn() {
        playerWhoseTurn = playerIterator.next();
        if (playerToSkip != null && playerToSkip.equals(playerWhoseTurn)) {
            playerToSkip = null;
            playerWhoseTurn = playerIterator.next();
        }
        gameModel.fireTurnChanged(playerWhoseTurn);
    }

    /**
     * Marks a player to skip their next turn.
     *
     * @param player The player to be marked for skipping a turn.
     */
    public void markPlayerToSkip(Player player) {
        playerToSkip = player;
    }

    /**
     * Gets the player whose current turn it is.
     *
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return playerWhoseTurn;
    }

    /**
     * Gets the internal game model used for observer notifications.
     *
     * @return The GameModel instance.
     */
    public GameModel getModel() {
        return gameModel;
    }

    /**
     * Sets the visual controller associated with this game logic controller.
     *
     * @param takenGameViewController The GameViewController to be associated.
     */
    public void setVisualController(GameViewController takenGameViewController) {
        gameViewController = takenGameViewController;
    }

    /**
     * Checks if the game has concluded.
     *
     * @return True if the game is finished, false otherwise.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Gets the player who won the game.
     *
     * @return The winning Player, or null if no winner has been determined yet.
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * Adds an observer to listen for game events.
     *
     * @param observer The observer to add.
     */
    public void addObserver(GameModelObserver observer) {
        gameModel.addListener(observer);
    }

    /**
     * Removes an observer from listening to game events.
     *
     * @param observer The observer to remove.
     */
    public void removeObserver(GameModelObserver observer) {
        gameModel.removeListener(observer);
    }
}