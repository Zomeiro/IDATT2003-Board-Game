package edu.ntnu.idatt2003.idatt2003boardgame.model.dice;
import java.util.ArrayList;

/**
 * Represents a collection of dice that can be rolled together.
 * The total sum of the values from all dice in the collection is returned upon rolling.
 * 
 * @author Bjørn Adam Vangen
 */
public class Dice {
    private final ArrayList<Die> dice = new ArrayList<>();

    /**
     * Constructs a new Dice object containing a specified number of individual Die instances.
     *
     * @param numberOfDice The number of dice to include in this collection.
     * Must be a non-negative integer. If zero, no dice are created.
     */
    public Dice(int numberOfDice) {
        for (int i = 0; i < numberOfDice; i++) {
            dice.add(new Die());
        }
    }

    /**
     * Rolls all the dice in this collection and returns the sum of their face values.
     * Each die in the collection is rolled individually.
     *
     * @return The total sum of the values rolled on all dice.
     * Returns 0 if there are no dice in the collection.
     */
    public int roll() {
        int sum = 0;
        for (Die die : dice) {
            die.roll();
            sum += die.getValue();
        }
        return sum;
    }
}