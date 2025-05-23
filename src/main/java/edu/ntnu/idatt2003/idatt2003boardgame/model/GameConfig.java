package edu.ntnu.idatt2003.idatt2003boardgame.model;

import java.util.ArrayList;

/**
 * Represents the configuration for a game session.
 * Note: The configuration parameters such as player count, rules, and dice size
 * are currently implemented as static fields, meaning they are shared across all
 * instances of GameConfig. This effectively makes it a global configuration rather
 * than an instance-specific one.
 * 
 * @author Hector Mendana Morales
 * @author Bjørn Adam Vangen (defaultConfig method)
 */
public class GameConfig {

    private static int playerCount;
    private static Rules rules;
    private static int diceSize;

    /**
     * Constructs a new GameConfig with the specified player count, rules, and dice size.
     * Due to the static nature of the fields, these values will affect the global game configuration.
     *
     * @param playerCount The number of players for the game.
     * @param rules The set of rules to be used for the game.
     * @param diceSize The number of dice to be used in the game.
     */
    public GameConfig(int playerCount, Rules rules, int diceSize) {
        this.playerCount = playerCount;
        this.rules = rules;
        this.diceSize = diceSize;

    }

    /**
     * Constructs a new GameConfig with the specified player count and rules,
     * defaulting to 1 die.
     * Due to the static nature of the fields, these values will affect the global game configuration.
     *
     * @param playerCount The number of players for the game.
     * @param rules The set of rules to be used for the game.
     */
    public GameConfig(int playerCount, Rules rules) {
        this.playerCount = playerCount;
        this.rules = rules;
        this.diceSize = 1;

    }

    /**
     * Creates a default game configuration for a given number of players.
     * It uses standard rules (assuming {@link Rules#standard(int)} with 1 die).
     *
     * @param playerCount The number of players for which to create the default configuration.
     * @return A new {@code GameConfig} instance with default settings.
     */
    public static GameConfig defaultConfig(int playerCount) {
        Rules defaultRules = Rules.standard(1);
        return new GameConfig(playerCount, defaultRules);
    }

    /**
     * Gets the currently configured game rules.
     * Reflects the static {@code rules} field.
     *
     * @return The current game {@link Rules}.
     */
    public Rules getRules() {
        return rules;
    }

    /**
     * Gets the currently configured number of players.
     * Reflects the static {@code playerCount} field.
     *
     * @return The current number of players.
     */
    public int getPlayers() {
        return playerCount;
    }

    /**
     * Gets the currently configured number of dice.
     * Reflects the static {@code diceSize} field.
     *
     * @return The current number of dice.
     */
    public int getDiceSize() {
        return diceSize;
    }

    /**
     * Sets the game rules for the global configuration.
     * Modifies the static {@code rules} field.
     *
     * @param gameRules The {@link Rules} to set.
     */
    public void setRules(Rules gameRules) {
        rules = gameRules;
    }

    /**
     * Sets the number of players for the global configuration.
     * Modifies the static {@code playerCount} field.
     *
     * @param gamePlayers The number of players to set.
     */
    public void setPlayers(int gamePlayers) {
        playerCount = gamePlayers;
    }

    /**
     * Sets the number of dice for the global configuration.
     * Modifies the static {@code diceSize} field.
     *
     * @param gameDiceSize The number of dice to set.
     */
    public void setDiceSize(int gameDiceSize) {
        diceSize = gameDiceSize;
    }

}