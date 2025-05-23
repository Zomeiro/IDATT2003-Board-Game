package edu.ntnu.idatt2003.idatt2003boardgame.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;

import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.Effect;

/**
 * Represents the game board, consisting of a collection of tiles,
 * dimensions (width and height), and a map for associating effects with tiles.
 * The board initializes a grid of tiles upon creation.
 * 
 * @author Hector Mendana Morales
 * @author Bjørn Adam Vangen
 */
public class Board {
    /**
     * A public random number generator instance that can be used for board-related randomness.
     * Note: Exposing a public Random instance is generally not recommended practice;
     * consider encapsulating randomness needs within methods.
     */
    public final Random randomGenerator;
    private final ArrayList<Tile> tiles;
    private final HashMap<Tile, Effect> effectMap;
    private final int boardWidth;
    private final int boardHeight;
    private final int tileCount;

    /**
     * Constructs a default board with a width of 10 and a height of 9,
     * resulting in 90 tiles. Tiles are numbered sequentially starting from 1.
     */
    public Board() {
        this.randomGenerator = new Random();
        this.tiles = new ArrayList<>();
        this.effectMap = new HashMap<>();
        this.boardWidth = 10;
        this.boardHeight = 9;
        this.tileCount = boardWidth * boardHeight;

        IntStream.rangeClosed(1, tileCount)
            .forEach(i -> tiles.add(new Tile(i)));

    }

    /**
     * Constructs a board with specified dimensions.
     * Tiles are numbered sequentially starting from 1 up to (boardWidth * boardHeight).
     *
     * @param boardWidth The width of the board (number of tiles horizontally).
     * @param boardHeight The height of the board (number of tiles vertically).
     */
    public Board(int boardWidth, int boardHeight) {
        this.randomGenerator = new Random();
        this.tiles = new ArrayList<>();
        this.effectMap = new HashMap<>();

        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.tileCount = boardWidth * boardHeight;

        IntStream.rangeClosed(1, tileCount)
            .forEach(i -> tiles.add(new Tile(i)));

    }

    /**
     * Returns the list of all tiles on the board.
     * The tiles are typically ordered by their number.
     *
     * @return An {@link ArrayList} of {@link Tile} objects.
     */
    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    /**
     * Returns the tile at the specified 0-based index in the internal list of tiles.
     * Note: Tile numbers are typically 1-based, so to get tile number N,
     * you would usually request index N-1.
     *
     * @param index The 0-based index of the tile to retrieve.
     * @return The {@link Tile} at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index >= getTileCount()).
     */
    public Tile getTileInIndex(int index) {
        return tiles.get(index);
    }

    /**
     * Returns the map associating tiles with their respective effects.
     * Note: This map is initialized but not populated or used for applying effects
     * within the provided Board class methods. Its management would be external.
     *
     * @return A {@link HashMap} where keys are {@link Tile} objects and
     * values are {@link Effect} objects.
     */
    public HashMap<Tile, Effect> getEffectMap() {
        return effectMap;
    }

    /**
     * Returns the total number of tiles on the board.
     * This is equivalent to boardWidth * boardHeight.
     *
     * @return The total count of tiles.
     */
    public int getTileCount() {
        return tileCount;
    }

    /**
     * Returns the width of the board (number of tiles horizontally).
     *
     * @return The board width.
     */
    public int getBoardWidth() {
        return boardWidth;
    }

    /**
     * Returns the height of the board (number of tiles vertically).
     *
     * @return The board height.
     */
    public int getBoardHeight() {
        return boardHeight;
    }


}