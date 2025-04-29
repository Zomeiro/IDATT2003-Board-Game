package edu.ntnu.idatt2003.idatt2003boardgame.service;

import java.util.List;

import edu.ntnu.idatt2003.idatt2003boardgame.repository.BoardJSON;
import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameController;
import edu.ntnu.idatt2003.idatt2003boardgame.controller.VisualController;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Board;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.BoardVisual;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.SideColumnVisual;
import javafx.scene.layout.VBox;

public class GameSetup {

    private final Board board;
    private final BoardVisual boardVisual;
    private GameController gameController;
    private final SideColumnVisual sideColumnVisual;
    private final VisualController visualController;

    public GameSetup(String game, int boardChoice, List<Player> players) {
        board = BoardJSON.constructSnLBoardFromJSON(boardChoice, gameController); // OK if gameController is unused here
        boardVisual = new BoardVisual(board);

        gameController = new GameController(board, players); // step 1
        visualController = new VisualController(gameController, boardVisual);    // step 2
        gameController.setVisualController(visualController);                    // step 3

        sideColumnVisual = new SideColumnVisual(players, visualController.getDiceAnimation());

    }

    public Board getBoard() {
        return board;
    }

    public BoardVisual getBoardVisual() {
        return boardVisual;
    }

    public GameController getGameController() {
        return gameController;
    }

    public VisualController getVisualController() {
        return visualController;
    }

    public VBox getSideColumn() {
        return sideColumnVisual.getColumn();

    }

}
