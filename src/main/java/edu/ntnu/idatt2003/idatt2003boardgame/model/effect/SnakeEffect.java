package edu.ntnu.idatt2003.idatt2003boardgame.model.effect;

import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameController;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import javafx.scene.paint.Color;

public class SnakeEffect implements Effect {

    private final int baseTileIndex;
    private final int targetTileIndex;

    public SnakeEffect(int baseTileIndex, int targetTileIndex) {
        this.baseTileIndex = baseTileIndex;
        this.targetTileIndex = targetTileIndex;

    }

    @Override
    public void execute(Player player, GameController gameController) {
        gameController.movePlayer(player, targetTileIndex);
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    public int getBaseTileIndex() {
        return baseTileIndex;
    }

    public int getTargetTileIndex() {
        return targetTileIndex;
    }
    
    
}
