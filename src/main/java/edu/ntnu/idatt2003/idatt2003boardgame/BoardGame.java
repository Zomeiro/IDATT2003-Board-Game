package edu.ntnu.idatt2003.idatt2003boardgame;

import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameController;
import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameViewController;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Board;
import edu.ntnu.idatt2003.idatt2003boardgame.model.GameConfig;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.model.observer.GameModelObserver;
import edu.ntnu.idatt2003.idatt2003boardgame.model.observer.LoggerObserver;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.BoardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public final class BoardGame implements GameModelObserver {

  private final Board board;
  private final List<Player> players;
  private final GameController gameController;
  private final GameViewController gameViewController;
  private final BoardView boardView;

  private final List<GameModelObserver> observers = new CopyOnWriteArrayList<>();

  public BoardGame(Board board, List<Player> players, GameConfig config, Consumer<Player> onGameWon) {
    this.board = Objects.requireNonNull(board);
    this.players = new ArrayList<>(players);

    this.gameController = new GameController(board, this.players);
    this.boardView = new BoardView(board);

    this.gameViewController = new GameViewController(() -> gameController.handleRollDice(), boardView, onGameWon);

    this.gameController.addObserver(gameViewController);
    this.gameController.addObserver(new LoggerObserver());
    this.gameController.setVisualController(null);
  }

  public void start() {
    gameController.start();
    boardView.updateEntireBoard();
  }

  public void rollDice() {
    gameController.handleRollDice();
  }

//observer managment
  public void addObserver(GameModelObserver observer) {
    observers.add(observer);
  }

  public void removeObserver(GameModelObserver observer) {
    observers.remove(observer);
  }


  @Override
  public void onDiceRolled(int value) {
    observers.forEach(o -> o.onDiceRolled(value));
  }

  @Override
  public void onPlayerMoved(Player player, int newPosition) {
    observers.forEach(o -> o.onPlayerMoved(player, newPosition));
  }

  @Override
  public void onTurnChanged(Player newPlayer) {
    observers.forEach(o -> o.onTurnChanged(newPlayer));
  }

  @Override
  public void onGameWon(Player winner) {
    observers.forEach(o -> o.onGameWon(winner));
  }

//getters for UI
  public Board getBoard() {
    return board;
  }

  public BoardView getBoardView() {
    return boardView;
  }

  public GameController getGameController() {
    return gameController;
  }

  public GameViewController getGameViewController() {
    return gameViewController;
  }

  public List<Player> getPlayers() {
    return List.copyOf(players);
  }

  public boolean isFinished() {
    return gameController.isFinished();
  }

  public String getWinnerName() {
    Player winner = gameController.getWinner();
    return (winner != null) ? winner.getName() : null;
  }
}
