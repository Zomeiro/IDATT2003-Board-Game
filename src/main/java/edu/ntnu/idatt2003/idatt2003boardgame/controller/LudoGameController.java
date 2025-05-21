package edu.ntnu.idatt2003.idatt2003boardgame.controller;

import edu.ntnu.idatt2003.idatt2003boardgame.model.*;
import edu.ntnu.idatt2003.idatt2003boardgame.model.dice.Dice;
import edu.ntnu.idatt2003.idatt2003boardgame.util.LoopingIterator;
import edu.ntnu.idatt2003.idatt2003boardgame.model.observer.GameModel;
import edu.ntnu.idatt2003.idatt2003boardgame.model.observer.GameModelObserver;

import java.util.*;

public class LudoGameController {

  private final GameModel model = new GameModel();
  private final Dice dice = new Dice(1);

  private final Board board;
  private final List<Player> players;
  private final Map<Player, Integer> playerRollAttempts = new HashMap<>();
  private final LoopingIterator<Player> playerIterator;

  private Player currentPlayer;
  private boolean gameFinished = false;

  public LudoGameController(Board board, List<Player> players) {
    this.board = board;
    this.players = players;
    this.playerIterator = new LoopingIterator<>(players);
    this.currentPlayer = playerIterator.next();
  }

  public void start() {
    model.fireTurnChanged(currentPlayer);
  }

  public void handleRoll() {
    if (gameFinished) return;

    int roll = dice.roll();
    model.fireDiceRolled(roll);

    boolean moved = tryMovePlayer(currentPlayer, roll);

    if (checkVictory(currentPlayer)) {
      model.fireGameWon(currentPlayer);
      gameFinished = true;
      return;
    }

    if (roll == 6) {
      model.fireTurnChanged(currentPlayer);
    } else {
      currentPlayer = playerIterator.next();
      model.fireTurnChanged(currentPlayer);
    }
  }

  private boolean tryMovePlayer(Player player, int roll) {
    //placeholder: implement spawn-on-6, movement, and knockout logic
    return true;
  }

  private boolean checkVictory(Player player) {
    //placeholder: see if all of player's pieces are in goal
    return false;
  }

  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  public void addObserver(GameModelObserver observer) {
    model.addListener(observer);
  }

  public void removeObserver(GameModelObserver observer) {
    model.removeListener(observer);
  }

  public GameModel getModel() {
    return model;
  }

  public boolean isFinished() {
    return gameFinished;
  }
}
