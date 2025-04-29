package  edu.ntnu.idatt2003.idatt2003boardgame;

import  edu.ntnu.idatt2003.idatt2003boardgame.view.scenes.StartScreenView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage){
        StartScreenView ui = new StartScreenView(primaryStage);
        ui.init();
        ui.start();
    }
    public static void main(String[] args) {
        launch(args);
    }
}