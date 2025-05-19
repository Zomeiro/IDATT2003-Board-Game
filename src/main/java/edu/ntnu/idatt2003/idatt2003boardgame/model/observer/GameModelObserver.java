package edu.ntnu.idatt2003.idatt2003boardgame.model.observer;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;

public interface GameModelObserver {
  void onDiceRolled(int value);
  void onPlayerMoved(Player player, int newPosition);
  void onTurnChanged(Player newPlayer);
  void onGameWon(Player winner);
}
