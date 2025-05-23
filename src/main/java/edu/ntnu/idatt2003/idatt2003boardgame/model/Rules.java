package edu.ntnu.idatt2003.idatt2003boardgame.model;

import java.util.ArrayList;

import edu.ntnu.idatt2003.idatt2003boardgame.model.dice.Dice;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.Effect;

/**
 * Represents the set of rules for a game, including the effects present
 * and the configuration of the dice to be used.
 * 
 * @author Hector Mendana Morales
 */
public class Rules {
    private final ArrayList<Effect> effects;
    private final int diceSize;
    private final Dice dice;

    /**
     * Constructs a new Rules object with a specified list of effects and dice size.
     * A {@link Dice} object is created based on the provided dice size.
     * This constructor has package-private visibility.
     *
     * @param effects An {@link ArrayList} of {@link Effect} objects applicable under these rules.
     * @param diceSize The number of dice to be used in the game (e.g., 1 for a single die, 2 for two dice).
     */
    Rules(ArrayList<Effect> effects, int diceSize) {
        this.effects = effects;
        this.diceSize = diceSize;
        this.dice = new Dice(diceSize);

    }

    /**
     * Creates a standard set of rules with no predefined effects and a specified dice size.
     * This factory method is a convenient way to get a basic Rules object.
     *
     * @param diceSize The number of dice to be used in the game under these standard rules.
     * @return A new {@code Rules} instance with an empty list of effects and the specified dice size.
     */
    public static Rules standard(int diceSize) {
        return new Rules(new ArrayList<>(), diceSize);
    }

    /**
     * Gets the {@link Dice} object configured for these rules.
     * The Dice object encapsulates the number of dice to be rolled.
     *
     * @return The {@code Dice} instance associated with these rules.
     */
    public Dice getDice() {
        return dice;
    }

    /**
     * Gets the list of effects that are part of these rules.
     * This list may be empty if no specific effects are defined.
     *
     * @return An {@link ArrayList} of {@link Effect} objects.
     */
    public ArrayList<Effect> getEffects() {
        return effects;
    }
}