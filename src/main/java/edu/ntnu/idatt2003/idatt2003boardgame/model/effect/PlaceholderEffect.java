package edu.ntnu.idatt2003.idatt2003boardgame.model.effect;

import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameController;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import javafx.scene.paint.Color;

/**
 * Represents a placeholder effect that performs no specific action when executed.
 * 
 * @author Hector Mendana Morales
 */
public class PlaceholderEffect implements Effect {

    /**
     * Executes the placeholder effect. This implementation performs no action.
     *
     * @param player The player on whom the effect would be executed (unused).
     * @param gameController The game controller (unused).
     */
    @Override
    public void execute(Player player, GameController gameController) {
        // This effect intentionally does nothing.
    }

    /**
     * Returns the color associated with this effect, which is gray.
     * This can be used for visual representation in the UI to indicate
     * a tile with a non-active or placeholder effect.
     *
     * @return The Color gray.
     */
    @Override
    public Color getColor() {
        return Color.GRAY;
    }
    
}