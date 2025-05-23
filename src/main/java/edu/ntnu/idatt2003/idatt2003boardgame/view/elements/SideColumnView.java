package edu.ntnu.idatt2003.idatt2003boardgame.view.elements;

import java.util.List;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.DiceAnimation;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority; // Import Priority
import javafx.scene.layout.VBox;

/**
 * A JavaFX {@link VBox} component that serves as a side column in the game UI.
 * It typically displays game-related information such as dice animations,
 * player information (via {@link PlayerRowsView}), and a game console (via {@link GameConsoleView}).
 * 
 * @author Hector Mendana Morales
 * @author Bjørn Adam Vangen (styling and game console)
 */
public class SideColumnView extends VBox {

    /**
     * Creates the SideColumnView.
     *
     * @param players        The list of players.
     * @param diceAnimation  The dice animation component.
     * @param gameConsole    The game console component.
     */
    public SideColumnView(List<Player> players, DiceAnimation diceAnimation, GameConsoleView gameConsole) {
        this.getStyleClass().add("side-column");

        BorderPane diceWrapper = new BorderPane();
        diceWrapper.setCenter(diceAnimation);
        diceWrapper.setMaxWidth(Double.MAX_VALUE);

        PlayerRowsView playerRows = new PlayerRowsView(players);

        //console view grows to take up remaining space
        VBox.setVgrow(gameConsole, Priority.ALWAYS);

        this.getChildren().add(diceWrapper);
        this.getChildren().add(playerRows.getPlayerRows());
        this.getChildren().add(gameConsole.getViewNode());
    }

    /**
     * Returns this {@code SideColumnView} instance.
     * This method effectively returns the VBox itself, which acts as the main column container.
     *
     * @return this {@code SideColumnView} object.
     */
    public VBox getColumn() {
        return this;
    }
}