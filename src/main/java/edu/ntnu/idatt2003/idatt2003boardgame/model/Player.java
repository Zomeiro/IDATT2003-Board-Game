package edu.ntnu.idatt2003.idatt2003boardgame.model;

public class Player {
    private String icon;
    final String name;
    int position;

    public Player(String icon, String name) {
        this.icon = icon;
        this.name = name;
        this.position = 1;
    }

    public int getPosition() { return position; }

    public void setPosition(int position) { this.position = position; }

    public String getIcon() { return icon; }

    public void setIcon(String icon) { this.icon = icon; }

    public String getName() { return name; }

    public void moveToTile(Tile tile) {
        position = tile.getNumber() + 1;
    }


}