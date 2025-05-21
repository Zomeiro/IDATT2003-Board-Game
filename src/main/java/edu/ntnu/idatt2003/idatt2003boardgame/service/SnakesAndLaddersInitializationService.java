package edu.ntnu.idatt2003.idatt2003boardgame.service;

import java.util.ArrayList;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.view.scenes.Ingame;
import edu.ntnu.idatt2003.idatt2003boardgame.view.scenes.WinnerView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SnakesAndLaddersInitializationService extends GameInitController {

    public SnakesAndLaddersInitializationService(Stage primaryStage) {
        super(primaryStage);
    }

    @Override
    public void startGame() {
        ArrayList<Player> playerList = this.getCurrentPlayers();

        if (playerList != null && playerList.size() > 1) {
            var service = new GameInitializationService("SnL1", playerList, winner -> {
                Scene winnerScene = new WinnerView().createScene(winner, primaryStage);
                primaryStage.setScene(winnerScene);
            });

            new Ingame(service).createGameScene(this.primaryStage);
        } else {
            System.out.println("Please select at least 2 players!");
        }
    }
}
