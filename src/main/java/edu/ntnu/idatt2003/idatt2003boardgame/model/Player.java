package edu.ntnu.idatt2003.idatt2003boardgame.model;

/**
 * Represents a player in the board game.
 * Each player has an icon, a name, and a current position on the board.
 * 
 * @author Hector Mendana Morales
 */
public class Player {
    private String icon;
    final String name; // Name is final, it's set at creation and doesn't change.
    int position;

    /**
     * Constructs a new Player with a specified icon and name.
     * The player's initial position is set to 1.
     *
     * @param icon A string file name for the player's icon.
     * @param name The name of the player.
     */
    public Player(String icon, String name) {
        this.icon = icon;
        this.name = name;
        this.position = 1; // Players start at position 1
    }

    /**
     * Gets the current position of the player on the board.
     * Position is typically 1-based.
     *
     * @return The current 1-based tile number where the player is located.
     */
    public int getPosition() { return position; }

    /**
     * Sets the player's current position on the board.
     *
     * @param position The new 1-based tile number for the player.
     */
    public void setPosition(int position) { this.position = position; }

    /**
     * Gets the string identifier or path for the player's icon.
     *
     * @return The player's icon string.
     */
    public String getIcon() { return icon; }

    /**
     * Sets the string identifier or path for the player's icon.
     *
     * @param icon The new icon string to set.
     */
    public void setIcon(String icon) { this.icon = icon; }

    /**
     * Gets the name of the player.
     *
     * @return The player's name.
     */
    public String getName() { return name; }

    /**
     * Moves the player to a specified tile.
     * This method updates the player's position based on the tile's number.
     *
     * @param tile The {@link Tile} object the player is moving to.
     */
    public void moveToTile(Tile tile) {
        position = tile.getNumber() + 1;
    }


}