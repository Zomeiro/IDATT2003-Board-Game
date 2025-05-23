package edu.ntnu.idatt2003.idatt2003boardgame.view.scenes;

import edu.ntnu.idatt2003.idatt2003boardgame.service.SnakesAndLaddersInitializationService;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Represents the initial start screen of the board game application.
 * This view displays a title and options to start different games,
 * currently featuring a button to start a "Snakes & Ladders" game.
 * It sets up the primary stage with this initial scene.
 * 
 * @author Bjørn Adam Vangen
 */
public class StartScreenView {
    private final Stage primaryStage;

    /**
     * Constructs a new StartScreenView.
     *
     * @param primaryStage The primary stage of the JavaFX application,
     * on which this start screen will be displayed.
     */
    public StartScreenView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Helper method from Gemini to apply CSS styles to the scene. 
     * @param scene The Scene to apply styles to.
     */
    private void applyStyles(Scene scene) {
        String cssPath = "/edu/ntnu/idatt2003/idatt2003boardgame/css/styles.css";
        try {
            String cssUrl = getClass().getResource(cssPath).toExternalForm();
            scene.getStylesheets().add(cssUrl);
        } catch (Exception e) {
            System.err.println("CSS not found for StartScreenView: " + cssPath);
        }
    }

    /**
     * Initializes the user interface for the start screen.
     * This includes setting the stage title, creating the layout with a title label
     * and a button to start the "Snakes & Ladders" game, applying CSS styles,
     * and setting the scene on the primary stage with defined dimensions.
     */
    public void init() {

        primaryStage.setTitle("Board Game Central");

        VBox menuWrapper = new VBox();
        menuWrapper.getStyleClass().add("start-screen-wrapper");
        menuWrapper.setAlignment(Pos.CENTER);
        menuWrapper.setSpacing(30);

        Label titleLabel = new Label("Board game!");
        titleLabel.getStyleClass().add("label-header");

        Button snakesButton = new Button("Play Snakes & Ladders");
        snakesButton.getStyleClass().add("start-button");
        snakesButton.setPrefWidth(250);

        snakesButton.setOnAction(e -> {
            new SnakesAndLaddersInitializationService(primaryStage).start();
        });

        menuWrapper.getChildren().addAll(titleLabel, snakesButton);

        Scene menu = new Scene(menuWrapper);
        applyStyles(menu);
        primaryStage.setScene(menu);

        primaryStage.setHeight(500);
        primaryStage.setWidth(600);

        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(400);
    }

    /**
     * Initializes and shows the start screen.
     */
    public void show() {
        init();
        primaryStage.show();
    }

    /**
     * Kept for potential compatibility.
     */
    public void start() {
        show();
    }
}