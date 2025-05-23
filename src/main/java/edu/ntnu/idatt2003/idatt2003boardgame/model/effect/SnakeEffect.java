package edu.ntnu.idatt2003.idatt2003boardgame.model.effect;

import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameController;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import javafx.scene.paint.Color;

/**
 * Represents a snake effect in the board game.
 * When a player lands on a tile with this effect (the snake's head),
 * they are moved from the snake's base tile (head) to a specified target tile (tail) further down the board.
 * 
 * @author Hector Mendana Morales
 */
public class SnakeEffect implements Effect {

    private final int baseTileIndex;
    private final int targetTileIndex;

    /**
     * Constructs a new SnakeEffect.
     *
     * @param baseTileIndex The 1-based index of the tile where the snake's head is located (start of the effect).
     * @param targetTileIndex The 1-based index of the tile where the snake's tail is located (where the player is moved to).
     */
    public SnakeEffect(int baseTileIndex, int targetTileIndex) {
        this.baseTileIndex = baseTileIndex;
        this.targetTileIndex = targetTileIndex;

    }

    /**
     * Executes the snake effect, moving the specified player to the snake's target tile (tail).
     *
     * @param player The player on whom the effect is executed.
     * @param gameController The game controller managing the game state.
     */
    @Override
    public void execute(Player player, GameController gameController) {
        gameController.movePlayer(player, targetTileIndex);
    }

    /**
     * Returns the color associated with this effect, which is green.
     * This can be used for visual representation of snakes in the UI.
     *
     * @return The Color green.
     */
    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    /**
     * Gets the 1-based index of the tile where this snake's head is (start of the effect).
     *
     * @return The base tile index of the snake.
     */
    public int getBaseTileIndex() {
        return baseTileIndex;
    }

    /**
     * Gets the 1-based index of the tile where this snake's tail is (where the player is moved to).
     *
     * @return The target tile index of the snake.
     */
    public int getTargetTileIndex() {
        return targetTileIndex;
    }


}