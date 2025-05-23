package edu.ntnu.idatt2003.idatt2003boardgame.model.observer;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;

/**
 * Defines the interface for an observer of game model events.
 * Classes implementing this interface can register with a {@link GameModel}
 * to receive notifications about changes in the game state, such as dice rolls,
 * player movements, turn changes, and game completion.
 * 
 * @author Bjørn Adam Vangen
 */
public interface GameModelObserver {
  /**
   * Called when a dice roll occurs in the game.
   *
   * @param value The value rolled on the dice.
   */
  void onDiceRolled(int value);

  /**
   * Called when a player moves to a new position on the board.
   *
   * @param player The player who moved.
   * @param newPosition The new tile number (typically 1-based) the player has moved to.
   */
  void onPlayerMoved(Player player, int newPosition);

  /**
   * Called when the game turn changes to a new player.
   *
   * @param newPlayer The player whose turn it is now.
   */
  void onTurnChanged(Player newPlayer);

  /**
   * Called when a player wins the game.
   *
   * @param winner The player who has won the game.
   */
  void onGameWon(Player winner);
}