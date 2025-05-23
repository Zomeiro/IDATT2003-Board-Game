package edu.ntnu.idatt2003.idatt2003boardgame.view.elements;

import java.util.ArrayList;
import java.util.List;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayerRowsView extends VBox {
    private List<HBox> playerRows = new ArrayList<>();

    public PlayerRowsView(List<Player> players) {
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        players.stream().forEach(i -> createPlayerRow(i));
    }

    public void createPlayerRow(Player player) {
        HBox row = new HBox();
        row.getStyleClass().add("player-row"); 
        ImageView playerIcon = new ImageView();
        try {
            playerIcon.setImage(new Image(getClass().getResourceAsStream(player.getIcon())));
        } catch (Exception e) {
            System.err.println("Could not load player icon: " + player.getIcon());
        }
        playerIcon.getStyleClass().add("player-icon"); 

        playerIcon.setFitWidth(50);
        playerIcon.setFitHeight(50);
        Label name = new Label(player.getName());
        name.getStyleClass().add("player-name-label"); 

    
        name.setPrefWidth(100);


        row.getChildren().addAll(playerIcon, name);

        this.getChildren().addAll(row);

    }

    public PlayerRowsView getPlayerRows() {
        return this;
    }


}