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

/**
 * A JavaFX {@link VBox} component that displays a list of players,
 * with each player represented as a horizontal row containing their icon and name.
 * 
 * @author Hector Mendana Morales
 * @author Bjørn Adam Vangen (styling)
 */
public class PlayerRowsView extends VBox {
    private List<HBox> playerRows = new ArrayList<>();

    /**
     * Constructs a new PlayerRowsView.
     * It initializes the VBox with spacing and center alignment, and then
     * creates a visual row for each player in the provided list.
     *
     * @param players A list of {@link Player} objects to be displayed.
     */
    public PlayerRowsView(List<Player> players) {
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        players.stream().forEach(i -> createPlayerRow(i));
    }

    /**
     * Creates a visual row (an {@link HBox}) for a single player,
     * containing their icon and name, and adds it to this VBox.
     * Applies style classes "player-row", "player-icon", and "player-name-label"
     * to the respective elements for CSS styling.
     *
     * @param player The {@link Player} for whom to create the display row.
     */
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

    /**
     * Returns this {@code PlayerRowsView} instance.
     * This method effectively returns the VBox itself, which contains all the player rows.
     *
     * @return this {@code PlayerRowsView} object.
     */
    public PlayerRowsView getPlayerRows() {
        return this;
    }


}