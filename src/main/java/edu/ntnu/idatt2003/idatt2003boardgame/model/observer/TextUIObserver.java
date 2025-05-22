package edu.ntnu.idatt2003.idatt2003boardgame.model.observer;

import edu.ntnu.idatt2003.idatt2003boardgame.model.LudoPiece;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;


public class TextUIObserver implements GameModelObserver {

  @Override
  public void onDiceRolled(int value) {
    System.out.println("Dice rolled: " + value);
  }

  @Override
  public void onPlayerMoved(Player player, int newPosition) {
    System.out.println(player.getName() + " moved to square: " + newPosition);
  }

  @Override
  public void onPieceMoved(LudoPiece piece, int newPosition) {
    System.out.println(piece.getOwner().getName() + " moved piece to square: " + newPosition);
  }

  @Override
  public void onTurnChanged(Player newPlayer) {
    System.out.println("It is now " + newPlayer.getName() + "'s turn!");
  }

  @Override
  public void onGameWon(Player winner) {
    System.out.println("Congratulations " + winner.getName() + ", you won the game!");
  }
}