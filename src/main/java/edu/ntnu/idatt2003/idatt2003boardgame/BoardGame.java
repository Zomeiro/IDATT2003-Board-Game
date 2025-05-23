package edu.ntnu.idatt2003.idatt2003boardgame;

import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameController;
import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameViewController;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Board;
import edu.ntnu.idatt2003.idatt2003boardgame.model.GameConfig;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.model.observer.GameModelObserver;
import edu.ntnu.idatt2003.idatt2003boardgame.model.observer.LoggerObserver;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.BoardView;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.GameConsoleView; 

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Represents a facade object for a complete board game session.
 * It encapsulates the main components such as the board, players, game logic controller,
 * UI controller, and views. It also acts as a GameModelObserver to potentially
 * relay events to its own set of observers.
 * 
 * @author Bjørn Adam Vangen
 */
public final class BoardGame implements GameModelObserver {

    private final Board board;
    private final List<Player> players;
    private final GameController gameController;
    private final GameViewController gameViewController;
    private final BoardView boardView;
    private final GameConsoleView gameConsoleView; 

    private final List<GameModelObserver> observers = new CopyOnWriteArrayList<>();

    /**
     * Constructs a new BoardGame instance.
     * Initializes all core game components including the board, players,
     * game controller, visual controller, board view, and game console view.
     * It also sets up necessary observer relationships between components.
     *
     * @param board The game board model. Must not be null.
     * @param players The list of players participating in the game.
     * @param config The game configuration settings.
     * @param onGameWon A Consumer callback to be executed when a player wins the game.
     */
    public BoardGame(Board board, List<Player> players, GameConfig config, Consumer<Player> onGameWon) {
        this.board = Objects.requireNonNull(board);
        this.players = new ArrayList<>(players);

        this.gameController = new GameController(board, this.players);
        this.boardView = new BoardView(board);
        this.gameConsoleView = new GameConsoleView(); 

        //give start action and roll dice action to gamecontroller (got from gemini)
        this.gameViewController = new GameViewController(
                () -> gameController.handleRollDice(),
                () -> gameController.start(),
                boardView,
                onGameWon
        );

        this.gameController.addObserver(gameViewController);
        this.gameController.addObserver(new LoggerObserver());
        this.gameController.addObserver(this.gameConsoleView); 
        //link gameController with GameViewController
        this.gameController.setVisualController(this.gameViewController);
    }

    /**
     * Starts the game logic and updates the board view.
     * This typically involves initializing player positions and the first turn.
     */
    public void start() {
        gameController.start();
        boardView.updateEntireBoard();
    }

    /**
     * Initiates a dice roll for the current player via the game controller.
     */
    public void rollDice() {
        gameController.handleRollDice();
    }

    //observer managment
    /**
     * Adds an observer to this BoardGame instance's list of listeners.
     * These observers will be notified of game events that this BoardGame instance itself handles.
     *
     * @param observer The GameModelObserver to add.
     */
    public void addObserver(GameModelObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from this BoardGame instance's list of listeners.
     *
     * @param observer The GameModelObserver to remove.
     */
    public void removeObserver(GameModelObserver observer) {
        observers.remove(observer);
    }


    /**
     * Called when a dice roll event occurs.
     * This implementation forwards the event to all observers registered with this BoardGame instance.
     *
     * @param value The value rolled on the dice.
     */
    @Override
    public void onDiceRolled(int value) {
        observers.forEach(o -> o.onDiceRolled(value));
    }

    /**
     * Called when a player moved event occurs.
     * This implementation forwards the event to all observers registered with this BoardGame instance.
     *
     * @param player The player who moved.
     * @param newPosition The new tile number (1-based) the player moved to.
     */
    @Override
    public void onPlayerMoved(Player player, int newPosition) {
        observers.forEach(o -> o.onPlayerMoved(player, newPosition));
    }

    /**
     * Called when the turn changes to a new player.
     * This implementation forwards the event to all observers registered with this BoardGame instance.
     *
     * @param newPlayer The player whose turn it is now.
     */
    @Override
    public void onTurnChanged(Player newPlayer) {
        observers.forEach(o -> o.onTurnChanged(newPlayer));
    }

    /**
     * Called when a player wins the game.
     * This implementation forwards the event to all observers registered with this BoardGame instance.
     *
     * @param winner The player who won the game.
     */
    @Override
    public void onGameWon(Player winner) {
        observers.forEach(o -> o.onGameWon(winner));
    }

    //getters for UI
    /**
     * Gets the game board model.
     *
     * @return The Board instance.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the visual representation of the game board.
     *
     * @return The BoardView instance.
     */
    public BoardView getBoardView() {
        return boardView;
    }

    /**
     * Gets the game logic controller.
     *
     * @return The GameController instance.
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * Gets the game's visual/UI controller.
     *
     * @return The GameViewController instance.
     */
    public GameViewController getGameViewController() {
        return gameViewController;
    }

    /**
     * Gets the game console view component.
     *
     * @return The GameConsoleView instance.
     */
    public GameConsoleView getGameConsoleView() {
        return gameConsoleView;
    }

    /**
     * Gets an unmodifiable list of players in the game.
     *
     * @return A List of Player objects.
     */
    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    /**
     * Checks if the game has finished.
     *
     * @return True if the game is finished, false otherwise.
     */
    public boolean isFinished() {
        return gameController.isFinished();
    }

    /**
     * Gets the name of the winning player.
     *
     * @return The name of the winner, or null if the game is not finished or there is no winner.
     */
    public String getWinnerName() {
        Player winner = gameController.getWinner();
        return (winner != null) ? winner.getName() : null;
    }
}