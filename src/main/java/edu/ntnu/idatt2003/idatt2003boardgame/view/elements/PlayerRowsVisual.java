package edu.ntnu.idatt2003.idatt2003boardgame.view.elements;

import java.util.ArrayList;
import java.util.List;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PlayerRowsVisual extends VBox {
    private List<HBox> playerRows = new ArrayList<>();

    public PlayerRowsVisual(List<Player> players) {
        players.stream().forEach(i -> createPlayerRow(i));
    }

    public void createPlayerRow(Player player) {
        HBox row = new HBox();
        row.setBackground(Background.fill(Color.WHITE));

        ImageView playerIcon = new ImageView(new Image(getClass().getResourceAsStream(player.getIcon())));

        playerIcon.setFitWidth(50);
        playerIcon.setFitHeight(50);
        Label name = new Label(player.getName());

        name.setAlignment(Pos.CENTER);
        name.setFont(Font.font("System", FontWeight.BOLD, 18));
        name.setPrefWidth(100);

        row.setAlignment(Pos.CENTER);

        row.setSpacing(75);

        row.getChildren().addAll(playerIcon, name);

        this.getChildren().addAll(row);

    }

    public PlayerRowsVisual getPlayerRows() {
        return this;
    }  
 
    
}
