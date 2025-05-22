package edu.ntnu.idatt2003.idatt2003boardgame.model;

import java.util.ArrayList;
import java.util.List;


public class LudoPlayer extends Player {

    private final LudoColor colour;
    private final List<LudoPiece> pieces = new ArrayList<>(4);

    public LudoPlayer(String name, String icon, LudoColor color) {
        super(name, icon);
        this.colour = color;
        for (int i = 0; i < 4; i++) {
            pieces.add(new LudoPiece(this,color, i));
        }
    }

    public LudoColor getColour() { 
        return colour; 
    }
    public List<LudoPiece> getPieces() { 
        return pieces; 
    }

    public boolean hasWon() {
        return pieces.stream().allMatch(LudoPiece::isFinished);
    }
}
