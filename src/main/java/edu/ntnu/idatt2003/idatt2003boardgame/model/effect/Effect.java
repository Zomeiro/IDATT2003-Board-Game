package edu.ntnu.idatt2003.idatt2003boardgame.model.effect;

import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameController;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import javafx.scene.paint.Color;

/**
 * Represents an effect that can be triggered within the board game
 * 
 * @author Hector Mendana Morales
 */
public interface Effect {

    /**
     * Executes the specific action of this effect.
     * This method is called when a player triggers the effect,
     * for example, by landing on a tile associated with this effect.
     *
     * @param player The player on whom the effect is to be executed.
     * @param gameController The game controller, providing access to game logic
     * and state modification methods (e.g., moving players,
     * advancing turns).
     */
    public void execute(Player player,  GameController gameController);

    /**
     * Gets the color associated with this effect.
     * This can be used for UI purposes, such as visually indicating
     * tiles with specific effects.
     *
     * @return The Color representing this effect.
     */
    Color getColor();
}