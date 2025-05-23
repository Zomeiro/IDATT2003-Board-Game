package edu.ntnu.idatt2003.idatt2003boardgame.util;

/**
 * Represents a direction in a 2D grid, typically used for movement or orientation.
 * Each direction has an associated x and y offset. Ended up not being used due to discontinued ludo development.
 * 
 * @author Bjørrn Adam Vangen
 */
public enum Direction {
    /** Represents the upward direction (x:0, y:-1). */
    UP(0, -1),
    /** Represents the rightward direction (x:1, y:0). */
    RIGHT(1, 0),
    /** Represents the downward direction (x:0, y:1). */
    DOWN(0, 1),
    /** Represents the leftward direction (x:-1, y:0). */
    LEFT(-1, 0),
    /** Represents the upward-right diagonal direction (x:1, y:-1). */
    UP_RIGHT(1, -1),
    /** Represents the upward-left diagonal direction (x:-1, y:-1). */
    UP_LEFT(-1, -1),
    /** Represents the downward-right diagonal direction (x:1, y:1). */
    DOWN_RIGHT(1, 1),
    /** Represents the downward-left diagonal direction (x:-1, y:1). */
    DOWN_LEFT(-1, 1);

    private int x;
    private int y;
    private int[] offset;

    /**
     * Private constructor to initialize a Direction enum constant with its x and y offsets.
     * Note: The 'offset' field is declared but not initialized in this constructor.
     *
     * @param x The x-component of the direction's offset.
     * @param y The y-component of the direction's offset.
     */
    private Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-component of this direction's offset.
     *
     * @return The x-offset.
     */
    public int getX() { return x; }

    /**
     * Gets the y-component of this direction's offset.
     *
     * @return The y-offset.
     */
    public int getY() { return y; }

    /**
     * Gets the offset of this direction as an array {@code [x, y]}.
     * <p>
     * Note: In the current implementation, the {@code offset} field is not initialized,
     * so this method will always return {@code null}.
     * It is intended to return an array like {@code [getX(), getY()]}.
     * </p>
     *
     * @return The offset array, or {@code null} as currently implemented.
     */
    public int[] getOffset() { return offset; }


    /**
     * Returns a string representation of the direction in the format "[x,y]".
     *
     * @return A string representing the x and y offsets of the direction.
     */
    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}