package edu.ntnu.idatt2003.idatt2003boardgame.service;

import edu.ntnu.idatt2003.idatt2003boardgame.BoardGame;
import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameController;
import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameViewController;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Board;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.BoardView;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.SideColumnView;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Service to hold and provide access to the core components
 * required for the Ingame scene, based on an initialized BoardGame.
 */
public class GameInitializationService {

    private final BoardGame boardGame;
    private final SideColumnView sideColumnView;

    /**
     * Creates a new GameInitializationService.
     *
     * @param boardGame The fully initialized BoardGame instance.
     * @param players   The list of players (needed for SideColumnView).
     */
    public GameInitializationService(BoardGame boardGame, List<Player> players) {
        this.boardGame = boardGame;
        if (this.boardGame == null || this.boardGame.getGameViewController() == null) {
            throw new IllegalArgumentException("BoardGame and its ViewController must be initialized.");
        }
        this.sideColumnView = new SideColumnView(players, boardGame.getGameViewController().getDiceAnimation());
    }

    public Board getBoard() {
        return boardGame.getBoard();
    }

    public BoardView getBoardVisual() {
        return boardGame.getBoardView();
    }

    public GameController getGameController() {
        return boardGame.getGameController();
    }

    public GameViewController getVisualController() {
        return boardGame.getGameViewController();
    }

    public VBox getSideColumn() {
        return sideColumnView.getColumn();
    }

    public BoardGame getBoardGame() {
        return boardGame;
    }
}