package edu.ntnu.idatt2003.idatt2003boardgame.service;
import java.util.ArrayList;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.view.scenes.Ingame;
import javafx.stage.Stage;

public class SnakesAndLaddersInitializationService extends GameInitController {

    public SnakesAndLaddersInitializationService(Stage primaryStage) {
        super(primaryStage);
    }

    @Override
    public void startGame(){
        ArrayList<Player> playerList = this.getCurrentPlayers();
        if (playerList != null && playerList.size() > 1) {
            
            new Ingame(new GameInitializationService("SnL", 0, playerList)).createGameScene(this.primaryStage);
            return;
        }
        System.out.println("Please select at least 2 players!");
    }

}
