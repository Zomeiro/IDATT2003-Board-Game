package edu.ntnu.idatt2003.idatt2003boardgame.model.observer;

import edu.ntnu.idatt2003.idatt2003boardgame.model.LudoPiece;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class GameModel {

  private final List<GameModelObserver> listeners = new CopyOnWriteArrayList<>();

  public void addListener(GameModelObserver listener) {
    listeners.add(listener);
  }

  public void removeListener(GameModelObserver listener) {
    listeners.remove(listener);
  }

  public void fireDiceRolled(int value) {
    for (GameModelObserver l : listeners) {
      l.onDiceRolled(value);
    }
  }

  public void firePlayerMoved(Player player, int newPosition) {
    for (GameModelObserver l : listeners) {
      l.onPlayerMoved(player, newPosition);
    }
  }

  public void firePieceMoved(LudoPiece piece, int newPosition) {
    for (GameModelObserver l : listeners) {
      l.onPieceMoved(piece, newPosition);
    }
  }

  public void fireTurnChanged(Player newPlayer) {
    for (GameModelObserver l : listeners) {
      l.onTurnChanged(newPlayer);
    }
  }

  public void fireGameWon(Player winner) {
    for (GameModelObserver l : listeners) {
      l.onGameWon(winner);
    }
  }
}
