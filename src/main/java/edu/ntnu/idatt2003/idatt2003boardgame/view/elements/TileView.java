package edu.ntnu.idatt2003.idatt2003boardgame.view.elements;

import java.util.List;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Tile;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A visual representation of a Tile in the board game. This component
 * is a StackPane that layers classic visual elements, such as:
 * - A background color
 * - The tile number
 *
 * In cases where there are players in the tile, the following elements also
 * apply:
 * - A player icon (if one player is present)
 * - An overlay and player counter (if multiple players are present)
 *
 * The visual is updated using the updateVisual() method based on the tile state.
 *
 * @author Hector Mendana Morales
 */
public class TileView extends StackPane {

    private final Tile tile;
    private final Rectangle background;
    private final ImageView iconView;
    private final Pane cover;
    private final Label playersInTile;
    private final Label viewNumber;

    /**
     * Constructs a new TileView based on the provided Tile.
     *
     * @param tile the Tile object this visual represents
     */
    public TileView(Tile tile) {
        this.tile = tile;
        this.background = new Rectangle(50, 50);
        background.setFill(Color.rgb(212, 187, 237));

        this.iconView = new ImageView();
        iconView.setFitWidth(40);
        iconView.setFitHeight(40);

        viewNumber = new Label(Integer.toString(tile.getNumber()));

        this.cover = new Pane();
        cover.setPrefSize(50, 50);
        cover.setBackground(Background.EMPTY);

        this.playersInTile = new Label();

        this.getChildren().addAll(background, viewNumber, iconView, cover, playersInTile);

    }

    /**
     * Updates the visual elements of this tile based on its current state:
     *
     * - If the tile has an effect, the background becomes red.
     * - If one player is on the tile, their icon is shown.
     * - If multiple players are on the tile, a white overlay and a count are shown.
     * - If no players are present, the tile number is shown.
     */
    public void updateVisual() {
        if (tile.getEffect() != null) {
            this.background.setFill(tile.getEffect().getColor());
            viewNumber.setTextFill(Color.WHITE);
        }

        List<Player> players = tile.getPlayers();

        if (!players.isEmpty()) {
            background.setFill(Color.rgb(212, 187, 237));

            if (players.size() == 1) {
                String iconPath = players.get(0).getIcon();
                Image playerIcon = new Image(getClass().getResourceAsStream(iconPath));
                iconView.setImage(playerIcon);
                playersInTile.setText("");
                cover.setBackground(Background.EMPTY);
            } else {
                cover.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                playersInTile.setText(Integer.toString(players.size()));

            }

        } else if (iconView.getImage() != null || playersInTile.getText().equals("")) {
            iconView.setImage(null);
            playersInTile.setText("");
            cover.setBackground(Background.EMPTY);

        }

    }

    /**
     * Returns the Tile model associated with this visual.
     *
     * @return the Tile this visual represents
     */
    public Tile getTile() {
        return tile;
    }

}