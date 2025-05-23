package edu.ntnu.idatt2003.idatt2003boardgame.service;

import edu.ntnu.idatt2003.idatt2003boardgame.BoardGame;
import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameController;
import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameViewController;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Board;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.BoardView;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.SideColumnView;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.GameConsoleView;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Service to hold and provide access to the core components
 * required for the Ingame scene, based on an initialized BoardGame.
 * 
 * @author Bjørn Adam Vangen
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
        //ensures boardgame and components are initialized
        if (this.boardGame == null || this.boardGame.getGameViewController() == null || this.boardGame.getGameConsoleView() == null) {
            throw new IllegalArgumentException("BoardGame, its ViewController, and its ConsoleView must be initialized.");
        }
        this.sideColumnView = new SideColumnView(
                players,
                boardGame.getGameViewController().getDiceAnimation(),
                boardGame.getGameConsoleView()
        );
    }

    /**
     * Gets the game board associated with the initialized game.
     *
     * @return The {@link Board} instance.
     */
    public Board getBoard() {
        return boardGame.getBoard();
    }

    /**
     * Gets the visual representation of the game board.
     *
     * @return The {@link BoardView} instance.
     */
    public BoardView getBoardVisual() {
        return boardGame.getBoardView();
    }

    /**
     * Gets the game logic controller.
     *
     * @return The {@link GameController} instance.
     */
    public GameController getGameController() {
        return boardGame.getGameController();
    }

    /**
     * Gets the game's visual/UI controller.
     *
     * @return The {@link GameViewController} instance.
     */
    public GameViewController getVisualController() {
        return boardGame.getGameViewController();
    }

    /**
     * Gets the side column UI component, which typically contains player information,
     * dice animations, and the game console.
     *
     * @return The {@link VBox} representing the side column.
     */
    public VBox getSideColumn() {
        return sideColumnView.getColumn();
    }

    /**
     * Gets the fully initialized BoardGame instance.
     *
     * @return The {@link BoardGame} instance.
     */
    public BoardGame getBoardGame() {
        return boardGame;
    }
}