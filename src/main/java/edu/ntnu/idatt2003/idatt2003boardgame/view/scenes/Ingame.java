package edu.ntnu.idatt2003.idatt2003boardgame.view.scenes;

import java.util.ArrayList;

import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameViewController;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Board;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Tile;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.Effect;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.LadderEffect;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.SnakeEffect;
import edu.ntnu.idatt2003.idatt2003boardgame.service.GameInitializationService;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.BoardView;
import edu.ntnu.idatt2003.idatt2003boardgame.view.layers.SnakesNLadders.LadderLayer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Sets up and displays the main game scene, including the board,
 * side column, and game controls.
 */
public class Ingame {

    private final Board board;
    private final BoardView boardView;
    private final GameViewController gameViewController;
    private final VBox sideColumn;

    public Ingame(GameInitializationService gameInitializationService) {
        this.board = gameInitializationService.getBoard();
        this.boardView = gameInitializationService.getBoardVisual();
        this.gameViewController = gameInitializationService.getVisualController();
        this.sideColumn = gameInitializationService.getSideColumn();
    }

    public void createGameScene(Stage primaryStage) {
        //center pane
        StackPane centerPane = new StackPane();
        centerPane.getChildren().add(boardView);

        //snakes and ladder data for drawing
        ArrayList<Tile> tilesWithLadders = new ArrayList<>();
        ArrayList<Tile> tilesWithSnakes = new ArrayList<>();
        for (Tile tile : board.getTiles()) {
            Effect effect = tile.getEffect();
            if (effect instanceof LadderEffect) {
                tilesWithLadders.add(tile);
            } else if (effect instanceof SnakeEffect) {
                tilesWithSnakes.add(tile);
            }
        }

        //adding snake and ladder layers
        LadderLayer effectLayer = new LadderLayer(boardView, tilesWithLadders, tilesWithSnakes);
        centerPane.getChildren().add(effectLayer);

        BorderPane root = new BorderPane();
        BorderPane.setAlignment(gameViewController.getDiceButton(), Pos.CENTER); // Center the button
        root.setBottom(gameViewController.getDiceButton());
        root.setLeft(sideColumn);
        root.setCenter(centerPane);

        //scene and primarystage setup
        Scene scene = new Scene(root, 800, 700); // Increased size for better layout
        primaryStage.setTitle("Snakes & Ladders - In Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        
        gameViewController.initializeAndStartGame();
        boardView.updateEntireBoard();
    }
}