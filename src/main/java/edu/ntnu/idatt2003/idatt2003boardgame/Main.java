package  edu.ntnu.idatt2003.idatt2003boardgame;

import  edu.ntnu.idatt2003.idatt2003boardgame.view.scenes.StartScreenView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main entry point for the IDATT2003 Board Game JavaFX application.
 * This class initializes and launches the user interface, starting with the
 * {@link StartScreenView}.
 * 
 * @author Bjørn Adam Vangen
 */
public class Main extends Application {
    /**
     * The main entry point for all JavaFX applications.
     * This method is called after the JavaFX application has been initialized.
     * It sets up and displays the initial screen of the board game application.
     *
     * @param primaryStage The primary stage for this application, onto which
     * the application scene can be set.
     */
    @Override
    public void start(Stage primaryStage){
        StartScreenView ui = new StartScreenView(primaryStage);
        ui.init();
        ui.start(); // Note: StartScreenView.start() calls show(), which calls init() again.
                  // Consider simplifying to just ui.show() if init() fully prepares the scene.
    }

    /**
     * The main method, which serves as the entry point for the Java application.
     * It launches the JavaFX application.
     *
     * @param args Command line arguments passed to the application. Not used by this application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}