package edu.ntnu.idatt2003.idatt2003boardgame.model.observer;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;

/**
 * An implementation of {@link GameModelObserver} that logs game events to the standard output (console).
 * This observer prints messages for dice rolls, player movements, turn changes, and game win events.
 * 
 * @author Bjørn Adam Vangen
 */
public class LoggerObserver implements GameModelObserver {

  /**
   * Called when a dice roll occurs in the game.
   * This implementation logs the dice roll value to the console.
   *
   * @param value The value rolled on the dice.
   */
  @Override
  public void onDiceRolled(int value) {
    System.out.println("Dice roll: " + value);
  }

  /**
   * Called when a player moves to a new position on the board.
   * This implementation logs the player's name and their new position to the console.
   *
   * @param player The player who moved.
   * @param newPosition The new tile number (typically 1-based) the player has moved to.
   */
  @Override
  public void onPlayerMoved(Player player, int newPosition) {
    System.out.println(player.getName() + " moved to square " + newPosition);
  }

  /**
   * Called when the game turn changes to a new player.
   * This implementation logs the name of the player whose turn it is now to the console.
   *
   * @param newPlayer The player whose turn it is now.
   */
  @Override
  public void onTurnChanged(Player newPlayer) {
    System.out.println("New turn: " + newPlayer.getName());
  }

  /**
   * Called when a player wins the game.
   * This implementation logs the name of the winning player to the console.
   *
   * @param winner The player who has won the game.
   */
  @Override
  public void onGameWon(Player winner) {
    System.out.println(winner.getName() + "has won the game!");
  }
}