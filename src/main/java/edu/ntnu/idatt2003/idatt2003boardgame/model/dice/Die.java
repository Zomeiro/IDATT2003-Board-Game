package edu.ntnu.idatt2003.idatt2003boardgame.model.dice;

import java.util.Random;

/**
 * Represents a single die that can be rolled to produce a random integer value.
 * The die typically has faces numbered from 1 to 6, but can be rolled
 * with a specified maximum value.
 * 
 * @author Bjørn Adam Vangen
 */
public class Die {
  /**
   * A shared Random instance used for generating random numbers for all dice.
   */
  public static final Random RANDOM = new Random();
  private int lastRolledValue;

  /**
   * Rolls the die, generating a random integer between 1 and 6 (inclusive).
   * The result of the roll is stored internally and can be retrieved using {@link #getValue()}.
   */
  public void roll() {
    lastRolledValue = RANDOM.nextInt(1, 7);
    //return lastRolledValue;
  }

  /**
   * Rolls the die, generating a random integer between 1 and the specified maximum value (inclusive).
   * The result of the roll is stored internally and can be retrieved using {@link #getValue()}.
   *
   * @param max The maximum face value for this roll. Must be a positive integer.
   */
  public void roll(int max) {
    lastRolledValue = RANDOM.nextInt(1, max + 1);
    //return lastRolledValue;
  }

  /**
   * Gets the value of the last roll performed on this die.
   * If the die has not been rolled yet, the value will be the default for an int (0).
   *
   * @return The last rolled value.
   */
  public int getValue() {
    return lastRolledValue;
  }
}