package edu.ntnu.idatt2003.idatt2003boardgame.view.scenes;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality; 
import javafx.stage.Stage;

public class WinnerView {

    /**
     * Shows a simple pop-up window indicating the winner.
     *
     * @param winner     The player who won.
     * @param ownerStage The main application stage, which will be the parent.
     */
    public void showWinnerPopup(Player winner, Stage ownerStage) {
        Stage popupStage = new Stage();

        Label title = new Label(winner.getName() + " won!");
        Button playAgainButton = new Button("Play Again"); 
        Button closeButton = new Button("Quit"); 

        
        playAgainButton.setOnAction(e -> {
            popupStage.close(); 
            new StartScreenView(ownerStage).show(); 
        });
        closeButton.setOnAction(e -> {
            popupStage.close(); 
            ownerStage.close(); 
        });

        VBox root = new VBox(20, title, playAgainButton, closeButton); 
        root.setAlignment(Pos.CENTER);
        root.setPadding(new javafx.geometry.Insets(20)); 

        Scene popupScene = new Scene(root, 300, 200);

        String cssPath = "/edu/ntnu/idatt2003/idatt2003boardgame/css/styles.css";
        try {
            popupScene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
        } catch (Exception e) { System.err.println("CSS not found, using default styles."); }

        
        popupStage.initOwner(ownerStage); 
        popupStage.initModality(Modality.APPLICATION_MODAL); 
        popupStage.setTitle("Game Over!");
        popupStage.setScene(popupScene);
        popupStage.setResizable(false);

        popupStage.showAndWait();
    }
}