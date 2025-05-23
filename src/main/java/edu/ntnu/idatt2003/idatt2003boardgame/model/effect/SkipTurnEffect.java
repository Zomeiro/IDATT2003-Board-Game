package edu.ntnu.idatt2003.idatt2003boardgame.model.effect;

import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameController;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import javafx.scene.paint.Color;

/**
 * Effect that causes a player to skip their next turn when triggered.
 * 
 * @author Hector Mendana Morales
 */
public class SkipTurnEffect implements Effect {

    /**
     * Executes the skip turn effect by marking the specified player
     * in the game controller to skip their upcoming turn.
     *
     * @param player The player who will skip their next turn.
     * @param gameController The game controller responsible for managing turn logic.
     */
    @Override
    public void execute(Player player, GameController gameController) {
        gameController.markPlayerToSkip(player);
    }

    /**
     * Returns the color associated with this effect, which is red.
     * This can be used for visual representation in the UI to indicate
     * a tile that causes a player to lose a turn.
     *
     * @return The Color red.
     */
    @Override
    public Color getColor() {
        return Color.RED;
    }
}