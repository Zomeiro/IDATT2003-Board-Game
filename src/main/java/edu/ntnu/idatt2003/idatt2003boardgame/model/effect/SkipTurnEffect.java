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

    @Override
    public void execute(Player player, GameController gameController) {
        gameController.markPlayerToSkip(player);
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }
}
