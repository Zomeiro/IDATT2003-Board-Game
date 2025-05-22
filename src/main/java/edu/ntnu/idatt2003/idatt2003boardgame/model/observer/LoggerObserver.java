package edu.ntnu.idatt2003.idatt2003boardgame.model.observer;

import edu.ntnu.idatt2003.idatt2003boardgame.model.LudoPiece;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;


public class LoggerObserver implements GameModelObserver {

  @Override
  public void onDiceRolled(int value) {
    System.out.println("Dice roll: " + value);
  }

  @Override
  public void onPlayerMoved(Player player, int newPosition) {
    System.out.println(player.getName() + " moved to square " + newPosition);
  }

  @Override
  public void onPieceMoved(LudoPiece piece, int newPosition) {
    System.out.println(piece.getOwner().getName() + " moved piece " + piece.getId() + " to square " + newPosition);
  }

  @Override
  public void onTurnChanged(Player newPlayer) {
    System.out.println("New turn: " + newPlayer.getName());
  }

  @Override
  public void onGameWon(Player winner) {
    System.out.println(winner.getName() + "has won the game!");
  }
}
