package edu.ntnu.idatt2003.idatt2003boardgame.model.effect;

import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameController;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import javafx.scene.paint.Color;

public class PlaceholderEffect implements Effect {

    @Override
    public void execute(Player player, GameController gameController) {
        
    }

    @Override
    public Color getColor() {
        return Color.GRAY;
    }
    
}
