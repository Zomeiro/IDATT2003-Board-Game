package edu.ntnu.idatt2003.idatt2003boardgame.model.observer;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents the core model for game events in the Observer pattern.
 * It maintains a list of {@link GameModelObserver} listeners and provides methods
 * to notify these listeners about various game state changes such as dice rolls,
 * player movements, turn changes, and game win events.
 * This class uses a {@link CopyOnWriteArrayList} for managing listeners to ensure
 * thread-safe modifications and iterations.
 * 
 * @author Bjørn Adam Vangen
 */
public class GameModel {

  private final List<GameModelObserver> listeners = new CopyOnWriteArrayList<>();

  /**
   * Adds a {@link GameModelObserver} to the list of listeners.
   * The added listener will be notified of subsequent game events.
   *
   * @param listener The observer to add. Must not be null.
   */
  public void addListener(GameModelObserver listener) {
    listeners.add(listener);
  }

  /**
   * Removes a {@link GameModelObserver} from the list of listeners.
   * The removed listener will no longer receive notifications for game events.
   *
   * @param listener The observer to remove.
   */
  public void removeListener(GameModelObserver listener) {
    listeners.remove(listener);
  }

  /**
   * Notifies all registered listeners that a dice has been rolled.
   *
   * @param value The value resulting from the dice roll.
   */
  public void fireDiceRolled(int value) {
    for (GameModelObserver l : listeners) {
      l.onDiceRolled(value);
    }
  }

  /**
   * Notifies all registered listeners that a player has moved to a new position.
   *
   * @param player The {@link Player} who moved.
   * @param newPosition The new tile number (1-based) the player moved to.
   */
  public void firePlayerMoved(Player player, int newPosition) {
    for (GameModelObserver l : listeners) {
      l.onPlayerMoved(player, newPosition);
    }
  }

  /**
   * Notifies all registered listeners that the turn has changed to a new player.
   *
   * @param newPlayer The {@link Player} whose turn it is now.
   */
  public void fireTurnChanged(Player newPlayer) {
    for (GameModelObserver l : listeners) {
      l.onTurnChanged(newPlayer);
    }
  }

  /**
   * Notifies all registered listeners that a player has won the game.
   *
   * @param winner The {@link Player} who won the game.
   */
  public void fireGameWon(Player winner) {
    for (GameModelObserver l : listeners) {
      l.onGameWon(winner);
    }
  }
}