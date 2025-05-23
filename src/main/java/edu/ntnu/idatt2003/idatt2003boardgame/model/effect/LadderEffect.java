package edu.ntnu.idatt2003.idatt2003boardgame.model.effect;


import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameController;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import javafx.scene.paint.Color;

/**
 * Represents a ladder effect in the board game.
 * When a player lands on a tile with this effect, they are moved
 * from the ladder's base tile to a specified target tile further up the board.
 * 
 * @author Hector Mendana Morales
 */
public class LadderEffect implements Effect {

    private final int baseTileIndex;
    private final int targetTileIndex;

    /**
     * Constructs a new LadderEffect.
     *
     * @param baseTileIndex The 1-based index of the tile where the ladder starts.
     * @param targetTileIndex The 1-based index of the tile where the ladder ends and the player is moved to.
     */
    public LadderEffect(int baseTileIndex, int targetTileIndex) {
        this.baseTileIndex = baseTileIndex;
        this.targetTileIndex = targetTileIndex;

    }

    /**
     * Executes the ladder effect, moving the specified player to the ladder's target tile.
     *
     * @param player The player on whom the effect is executed.
     * @param gameController The game controller managing the game state.
     */
    @Override
    public void execute(Player player, GameController gameController) {
        gameController.movePlayer(player, targetTileIndex);
    }

    /**
     * Returns the color associated with this effect, which is blue.
     * This can be used for visual representation of ladders in the UI.
     *
     * @return The Color blue.
     */
    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    /**
     * Gets the 1-based index of the tile where this ladder starts.
     *
     * @return The base tile index of the ladder.
     */
    public int getBaseTileIndex() {
        return baseTileIndex;
    }

    /**
     * Gets the 1-based index of the tile where this ladder leads to.
     *
     * @return The target tile index of the ladder.
     */
    public int getTargetTileIndex() {
        return targetTileIndex;
    }


    
}