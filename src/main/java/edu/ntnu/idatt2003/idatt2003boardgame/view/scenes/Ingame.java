package edu.ntnu.idatt2003.idatt2003boardgame.view.scenes;

import java.util.ArrayList;

import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameController;
import edu.ntnu.idatt2003.idatt2003boardgame.controller.VisualController;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Board;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Tile;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.LadderEffect;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.SnakeEffect;
import edu.ntnu.idatt2003.idatt2003boardgame.service.GameInitializationService;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.BoardView;
import edu.ntnu.idatt2003.idatt2003boardgame.view.layers.SnakesNLadders.LadderLayer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Ingame {

    private final Board board;
    private final BoardView boardView;
    private final VisualController visualController;
    private final VBox sideColumn;
    GameController gameController;

    public Ingame(GameInitializationService gameInitializationService) {
        this.board = gameInitializationService.getBoard();
        this.boardView = gameInitializationService.getBoardVisual();
        this.gameController = gameInitializationService.getGameController();
        this.visualController = gameInitializationService.getVisualController();
        this.sideColumn = gameInitializationService.getSideColumn();
        

    }

    public void createGameScene(Stage primaryStage) {
        StackPane centerPane = new StackPane();
        centerPane.getChildren().add(boardView);
    
        ArrayList<Tile> tilesWithLadders = new ArrayList<>();
        for (Tile tile : board.getTiles()) {
            if (tile.getEffect() instanceof LadderEffect) {
                tilesWithLadders.add(tile);
            }
    
            if (tile.getEffect() != null) {
                System.out.println("EFFECT IN: " + tile.getNumber());
            }
        }
    
        ArrayList<Tile> tilesWithSnakes = new ArrayList<>();
        for (Tile tile : board.getTiles()) {
            if (tile.getEffect() instanceof SnakeEffect) {
                tilesWithSnakes.add(tile);
            }
        }
    
        BorderPane root = new BorderPane();
        root.setBottom(visualController.getDiceButton()); 
        root.setLeft(sideColumn);
    
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Board Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    
        LadderLayer ladders = new LadderLayer(boardView, tilesWithLadders, tilesWithSnakes);
        centerPane.getChildren().add(ladders);
        root.setCenter(centerPane);

        gameController.start();
    
        boardView.updateEntireBoard();
    }

}
