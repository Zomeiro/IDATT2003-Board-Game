package edu.ntnu.idatt2003.idatt2003boardgame.model;

public class LudoPiece {
    private final Player owner;
    private final LudoColor color;
    private int position; // -1 = in yard, 0-3 in home, 4-60 = on board, 57+ = finished
    private final int id;

    public LudoPiece(Player owner, LudoColor color, int id) {
        this.owner = owner;
        this.color = color;
        this.position = 0; //start 
        this.id = id;
    }

    public Player getOwner() {
        return owner;
    }
    
    public LudoColor getColor() {
        return color;
    }

    public int getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public void setPosition(int pos) {
        this.position = pos;
    }

    public boolean isInYard() {
        return position == -1;
    }

    public boolean isFinished() {
        return position >= 57;
    }
    
    public boolean isOnBoard() {
        return position >= 0 && position < 57;
    }
}