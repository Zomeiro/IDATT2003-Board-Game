package edu.ntnu.idatt2003.idatt2003boardgame.service;

import edu.ntnu.idatt2003.idatt2003boardgame.BoardGame;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.view.scenes.Ingame;
import edu.ntnu.idatt2003.idatt2003boardgame.view.scenes.WinnerView;
import javafx.scene.Scene;
import javafx.scene.control.Alert; // Import Alert
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Service responsible for initializing and starting a Snakes and Ladders game session.
 * It extends {@link GameInitController} to use its player and board selection capabilities,
 * then creates and launches the game using the {@link BoardGameFactory} and
 * {@link GameInitializationService}.
 * 
 * @author Bjørn Adam Vangen
 */
public class SnakesAndLaddersInitializationService extends GameInitController {

    /**
     * Constructs a new SnakesAndLaddersInitializationService.
     *
     * @param primaryStage The primary stage of the JavaFX application, used for displaying scenes.
     */
    public SnakesAndLaddersInitializationService(Stage primaryStage) {
        super(primaryStage);
    }

    /**
     * Overrides the base method to start a Snakes and Ladders game.
     * This method retrieves selected players and board configuration (from absolute path,
     * preset name, or random generation settings). It then validates the inputs,
     * creates a {@link BoardGame} instance using {@link BoardGameFactory},
     * sets up the game using {@link GameInitializationService}, and finally
     * displays the {@link Ingame} scene. Error alerts are shown for invalid configurations
     * or loading issues.
     */
    @Override
    public void startGame() {
        ArrayList<Player> playerList = this.getCurrentPlayers();

        if (playerList == null || playerList.size() < 2) {
            showAlert("Player Error", "Please select at least 2 unique players!");
            return;
        }

        BoardGame boardGame = null;
        Consumer<Player> onGameWon = winner -> {
            new WinnerView().showWinnerPopup(winner, primaryStage);
        };

        try {
            if (this.selectedFilePath != null) {
                //abs path
                boardGame = BoardGameFactory.createSnakesAndLaddersByAbsolutePath(
                        this.selectedFilePath, playerList, onGameWon);
            } else if (this.selectedBoardOption != null) {
                //preset / random
                if (this.selectedBoardOption.startsWith("Random")) {
                    BoardGameFactory.Difficulty difficulty;
                    switch (this.selectedBoardOption) {
                        case "Random Easy":
                            difficulty = BoardGameFactory.Difficulty.EASY;
                            break;
                        case "Random Hard":
                            difficulty = BoardGameFactory.Difficulty.HARD;
                            break;
                        case "Random Medium":
                        default:
                            difficulty = BoardGameFactory.Difficulty.MEDIUM;
                            break;
                    }
                    boardGame = BoardGameFactory.createRandomSnakesAndLadders(
                            difficulty, playerList, onGameWon);
                } else {
                    //assume preset name
                    boardGame = BoardGameFactory.createSnakesAndLadders(
                            this.selectedBoardOption, playerList, onGameWon);
                }
            } else {
                showAlert("Board Error", "Please select a board or load one from file!");
                return;
            }

            if (boardGame != null) {
                GameInitializationService service = new GameInitializationService(boardGame, playerList);
                new Ingame(service).createGameScene(this.primaryStage);
            } else {
                 // shouldn't happen if logic above is sound
                 showAlert("Error", "Could not create the board game instance.");
            }

        } catch (RuntimeException e) {
            //find errors during board loading/creation
            System.err.println("Failed to start game: " + e.getMessage());
            e.printStackTrace();
            showAlert("Load Error", "Failed to load or create board: \n" + e.getMessage());
        }
    }

    /**
     * Helper method to show JavaFX Alerts.
     * @param title The title of the alert window.
     * @param message The message to display.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}