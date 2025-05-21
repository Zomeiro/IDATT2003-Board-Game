package edu.ntnu.idatt2003.idatt2003boardgame.model;

public class LudoPiece {
  private final Player owner;
  private int position; //-1 = i in thingy, 0 = start square, 1++ = steps

  public LudoPiece(Player owner) {
    this.owner = owner;
    this.position = -1;
  }

  public Player getOwner() {
    return owner;
  }

  public int getPosition() {
    return position;
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
}
