package edu.ntnu.idatt2003.idatt2003boardgame.service;

import java.util.List;

import edu.ntnu.idatt2003.idatt2003boardgame.BoardGame;
import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameController;
import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameViewController;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Board;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.BoardView;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.SideColumnView;
import javafx.scene.layout.VBox;

public class GameInitializationService {

    private final BoardGame boardGame;
    private final SideColumnView sideColumnView;

    public GameInitializationService(String presetName, List<Player> players) {

        this.boardGame = BoardGameFactory.createSnakesAndLadders(presetName, players);

        this.sideColumnView = new SideColumnView(players,boardGame.getGameViewController().getDiceAnimation());
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
}
