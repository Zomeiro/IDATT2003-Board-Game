package edu.ntnu.idatt2003.idatt2003boardgame.model;

import java.util.ArrayList;

import edu.ntnu.idatt2003.idatt2003boardgame.model.dice.Dice;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.Effect;

public class Rules {
    private final ArrayList<Effect> effects;
    private final int diceSize;
    private final Dice dice;

    Rules(ArrayList<Effect> effects, int diceSize) {
        this.effects = effects;
        this.diceSize = diceSize;
        this.dice = new Dice(diceSize);
        
    }

    public Dice getDice() {
        return dice;
    }

    public ArrayList<Effect> getEffects() {
        return effects;
    }

    

    
   
}
