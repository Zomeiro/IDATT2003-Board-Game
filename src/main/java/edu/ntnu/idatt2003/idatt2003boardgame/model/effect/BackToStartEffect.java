package edu.ntnu.idatt2003.idatt2003boardgame.model.effect;

import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameController;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import javafx.scene.paint.Color;

/**
 * Effect that causes a player to skip their next turn when triggered.
 * 
 * @author Hector Mendana Morales
 */
public class BackToStartEffect implements Effect {

    /**
     * Executes the effect, moving the specified player back to the starting tile (tile 1).
     *
     * @param player The player on whom the effect is executed.
     * @param gameController The game controller managing the game state.
     */
    @Override
    public void execute(Player player, GameController gameController) {
        gameController.movePlayer(player, 1);
    }

    /**
     * Returns the color associated with this effect, which is black.
     * This can be used for visual representation in the UI.
     *
     * @return The Color black.
     */
    @Override
    public Color getColor() {
        return Color.BLACK;
    }
}