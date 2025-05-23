package edu.ntnu.idatt2003.idatt2003boardgame.view.elements;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.model.observer.GameModelObserver;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * A JavaFX VBox containing a TextArea that acts as a game console,
 * observing and displaying game events.
 */
public class GameConsoleView extends VBox implements GameModelObserver {

    private final TextArea consoleArea;

    /**
     * Creates a new GameConsoleView.
     */
    public GameConsoleView() {
        this.consoleArea = new TextArea();
        this.consoleArea.setEditable(false);
        this.consoleArea.setWrapText(true);
        this.consoleArea.getStyleClass().add("game-console-area");

        Label title = new Label("Game Log");
        title.getStyleClass().add("game-console-title");

        VBox.setVgrow(consoleArea, Priority.ALWAYS);

        this.getChildren().addAll(title, consoleArea);
        this.getStyleClass().add("game-console");
        this.setSpacing(5);
    }

    private void appendText(String message) {
        Platform.runLater(() -> {
            consoleArea.appendText(message + "\n");
            consoleArea.setScrollTop(Double.MAX_VALUE);
        });
    }

    @Override
    public void onDiceRolled(int value) {
        appendText("Dice rolled: " + value);
    }

    @Override
    public void onPlayerMoved(Player player, int newPosition) {
        appendText(player.getName() + " moved to square: " + newPosition);
    }

    @Override
    public void onTurnChanged(Player newPlayer) {
        appendText("It is now " + newPlayer.getName() + "'s turn!");
    }

    @Override
    public void onGameWon(Player winner) {
        appendText("Congratulations " + winner.getName() + ", you won the game!");
    }

    public VBox getViewNode() {
        return this;
    }
}