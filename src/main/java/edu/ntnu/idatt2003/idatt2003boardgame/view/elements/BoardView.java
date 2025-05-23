package edu.ntnu.idatt2003.idatt2003boardgame.view.elements;

import java.util.ArrayList;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Board;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Tile;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * Represents the visual display of a game board as a {@link GridPane}.
 * It initializes and arranges {@link TileView} instances based on the
 * provided {@link Board} model, laying them out in a serpentine pattern.
 * 
 * @author Hector Mendana Morales
 */
public class BoardView extends GridPane {
    private final Board board;
    private final ArrayList<Tile> tileLogic;
    private final ArrayList<TileView> tileViews;

    /**
     * Constructs a new BoardView associated with the given {@link Board}.
     * It sets up the grid pane's size and initializes the visual representation
     * of each tile on the board.
     *
     * @param board The game board model to be visualized.
     */
    public BoardView(Board board) {
        this.board = board;
        this.tileLogic = board.getTiles();
        this.tileViews = new ArrayList<>();

        this.getStyleClass().add("board-view");

        this.setPrefSize(536, 482); //same as board
        this.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        initializeBoard();
    }

    private void initializeBoard() {
        this.setHgap(4); //horizontal gap between tiles
        this.setVgap(4); //vertical gap between tiles

        Boolean movesRight = false;

        for (int i = 0; i < this.board.getTileCount(); i++) {

            Tile tile = tileLogic.get(i);
            TileView tileView = new TileView(tile);

            tileViews.add(tileView);

            if ((i % this.board.getBoardWidth()) == 0) { movesRight = !movesRight; }

            if (movesRight) {
                this.add(tileView, i % this.board.getBoardWidth(), i / this.board.getBoardWidth());

            } else {
                this.add(tileView, this.board.getBoardWidth() - ((i % this.board.getBoardWidth()) + 1), i / this.board.getBoardWidth());

            }
        }

    }


    /**
     * Updates the visual representation of all tiles on the board.
     * This method iterates through each {@link TileView} and calls its
     * {@code updateVisual()} method.
     */
    public void updateEntireBoard() {
        for (TileView tv : tileViews) {
            tv.updateVisual();
        }

    }

    /**
     * Returns this BoardView instance.
     *
     * @return This {@code BoardView} object.
     */
    public BoardView getBoardVisual() {
        return this;
    }

    /**
     * Intended to display visual representations of effects on the board.
     * Note: This method is currently not implemented.
     */
    public void displayEffects() {

    }


}