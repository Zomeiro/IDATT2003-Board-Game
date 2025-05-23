package edu.ntnu.idatt2003.idatt2003boardgame.controller;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.model.observer.GameModelObserver;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.BoardView;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.ButtonView;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.DiceAnimation;
import java.util.function.Consumer;
import javafx.scene.layout.HBox;

/**
 * Manages the visual aspects and user interactions of the game,
 * observing the model (via GameController) and updating the view.
 * 
 * @author Bjørn Adam Vangen
 */
public class GameViewController implements GameModelObserver {
    private final DiceAnimation diceVisual = new DiceAnimation();
    private final ButtonView diceButton;
    private final BoardView boardView;
    private final Consumer<Player> onGameWonCallback;
    private final Runnable onStartGameAction; // Action to start the game logic

    /**
     * Constructs the GameViewController.
     *
     * @param onDiceRoll        Action to perform when the dice button is clicked.
     * @param onStartGame       Action to perform to start the game logic.
     * @param boardView         The visual representation of the board.
     * @param onGameWon         Callback to execute when a player wins.
     */
    public GameViewController(Runnable onDiceRoll, Runnable onStartGame, BoardView boardView, Consumer<Player> onGameWon) {
        this.boardView = boardView;
        this.diceButton = new ButtonView(onDiceRoll);
        this.onGameWonCallback = onGameWon;
        this.onStartGameAction = onStartGame; // Store the start action
    }

    /**
     * Triggers the game start action provided during construction.
     */
    public void initializeAndStartGame() {
        if (onStartGameAction != null) {
            onStartGameAction.run();
        } else {
            System.err.println("GameViewController: onStartGame action was not provided!");
        }
    }

    /**
     * Gets the HBox containing the dice button UI element.
     *
     * @return The HBox for the dice button.
     */
    public HBox getDiceButton() {
        return diceButton;
    }

    /**
     * Gets the DiceAnimation object responsible for visually displaying dice rolls.
     *
     * @return The DiceAnimation instance.
     */
    public DiceAnimation getDiceAnimation() {
        return diceVisual;
    }

    /**
     * Called when a dice roll event occurs in the game model.
     * This implementation updates the dice visual display.
     *
     * @param value The value rolled on the dice.
     */
    @Override
    public void onDiceRolled(int value) {
        diceVisual.displayRoll(value);
    }

    /**
     * Called when a player moved event occurs in the game model.
     * This implementation triggers an update of the entire board view.
     *
     * @param player The player who moved.
     * @param newPosition The new tile number (1-based) the player moved to.
     */
    @Override
    public void onPlayerMoved(Player player, int newPosition) {
        boardView.updateEntireBoard();
    }

    /**
     * Called when the turn changes to a new player in the game model.
     *
     * @param newPlayer The player whose turn it is now.
     */
    @Override
    public void onTurnChanged(Player newPlayer) {
        //add something here to highlight current player if time
    }

    /**
     * Called when a player wins the game.
     * This implementation prints the winner's name to the console and
     * invokes the onGameWonCallback.
     *
     * @param winner The player who won the game.
     */
    @Override
    public void onGameWon(Player winner) {
        System.out.println("Winner: " + winner.getName());
        if (onGameWonCallback != null) {
            onGameWonCallback.accept(winner);
        }
    }
}