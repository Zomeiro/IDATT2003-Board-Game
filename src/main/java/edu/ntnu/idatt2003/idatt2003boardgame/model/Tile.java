package edu.ntnu.idatt2003.idatt2003boardgame.model;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import java.util.ArrayList;
import java.util.List;

import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.Effect;

/**
 * Represents a single tile on the game board.
 * Each tile has a unique number, can hold a list of players currently on it,
 * may have an associated effect, and can store its coordinates (e.g., for UI rendering).
 * 
 * @author Hector Mendana Morales
 * @author Bjørn Adam Vangen
 */
public class Tile {

    private List<Player> players;
    private Effect effect;
    private final int number;
    private int[] coordinates;

    /**
     * Constructs a new Tile with a specified number.
     * The tile initially has no effect, no set coordinates, and no players.
     *
     * @param number The unique number identifying this tile (typically 1-based).
     */
    public Tile(int number) {
        this.number = number;
        this.effect = null;
        this.coordinates = null;
        this.players = new ArrayList<>();
    }

    /**
     * Constructs a new Tile with a specified number and coordinates.
     * The tile initially has no effect and no players.
     *
     * @param number The unique number identifying this tile (typically 1-based).
     * @param coordinates An array of integers representing the tile's coordinates,
     * e.g., [x, y] for 2D positioning.
     * 
     * Did not end up using this constructor due to limited time for other games, but it is here for future use.
     */
    public Tile(int number,int[] coordinates) {
        this.number = number;
        this.effect = null;
        this.coordinates = coordinates;
        this.players = new ArrayList<>();
    }

    /**
     * Gets the number of this tile.
     *
     * @return The unique number identifying this tile.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Gets the coordinates of this tile.
     *
     * @return An array of integers representing the tile's coordinates.
     * @throws NullPointerException if the coordinates have not been set for this tile.
     */
    public int[] getCoordinates() {
        if (coordinates != null) {
            return coordinates;
        } else {
            throw new NullPointerException("Coordinates are not set for this tile.");
        }
    }

    /**
     * Gets the effect associated with this tile, if any.
     *
     * @return The {@link Effect} associated with this tile, or {@code null} if there is no effect.
     */
    public Effect getEffect() {
        return effect;
    }

    /**
     * Gets the list of players currently occupying this tile.
     *
     * @return A {@link List} of {@link Player} objects on this tile.
     * Returns an empty list if no players are on this tile.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Adds a player to this tile.
     *
     * @param recievedPlayer The {@link Player} to add to this tile.
     */
    public void addPlayer(Player recievedPlayer) {
        players.add(recievedPlayer);

    }

    /**
     * Removes the first player from the list of players on this tile.
     * This method removes the player at index 0. If the list is empty,
     * it will result in an {@link IndexOutOfBoundsException}.
     */
    public void popPlayer() {
        players.remove(0);
    }

    /**
     * Sets or replaces the effect associated with this tile.
     *
     * @param effect The {@link Effect} to associate with this tile. Can be {@code null} to remove an existing effect.
     */
    public void setEffect(Effect effect) {
        this.effect = effect;

    }

    /**
     * Sets the coordinates for this tile.
     *
     * @param coordinates An array of integers representing the tile's coordinates, e.g., [x, y].
     */
    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }
}