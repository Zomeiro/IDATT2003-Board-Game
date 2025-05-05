package edu.ntnu.idatt2003.idatt2003boardgame.service;

import java.util.List;

import edu.ntnu.idatt2003.idatt2003boardgame.repository.JsonBoardRepository;
import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameController;
import edu.ntnu.idatt2003.idatt2003boardgame.controller.VisualController;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Board;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.BoardView;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.SideColumnView;
import javafx.scene.layout.VBox;

public class GameInitializationService {

    private final Board board;
    private final BoardView boardView;
    private GameController gameController;
    private final SideColumnView sideColumnView;
    private final VisualController visualController;

    public GameInitializationService(String game, int boardChoice, List<Player> players) {
        board = JsonBoardRepository.constructSnLBoardFromJSON(boardChoice, gameController); // OK if gameController is unused here
        boardView = new BoardView(board);

        gameController = new GameController(board, players); // step 1
        visualController = new VisualController(gameController, boardView);    // step 2
        gameController.setVisualController(visualController);                    // step 3

        sideColumnView = new SideColumnView(players, visualController.getDiceAnimation());

    }

    public Board getBoard() {
        return board;
    }

    public BoardView getBoardVisual() {
        return boardView;
    }

    public GameController getGameController() {
        return gameController;
    }

    public VisualController getVisualController() {
        return visualController;
    }

    public VBox getSideColumn() {
        return sideColumnView.getColumn();

    }

}
