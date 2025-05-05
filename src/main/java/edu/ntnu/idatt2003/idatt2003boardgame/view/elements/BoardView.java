package edu.ntnu.idatt2003.idatt2003boardgame.view.elements;

import java.util.ArrayList;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Board;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Tile;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class BoardView extends GridPane {
    private final Board board;
    private final ArrayList<Tile> tileLogic;
    private final ArrayList<TileView> tileViews;

    public BoardView(Board board) {
        this.board = board;
        this.tileLogic = board.getTiles();
        this.tileViews = new ArrayList<>();

        this.setPrefSize(536, 482); // same as board
        this.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        initializeBoard();
    }

    private void initializeBoard() {
        this.setHgap(4); // horizontal gap between tiles
        this.setVgap(4); // vertical gap between tiles
        this.setStyle("-fx-background-color: lightblue;"); // background visible in gaps

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


    public void updateEntireBoard() {
        for (TileView tv : tileViews) {
            tv.updateVisual();
        }
        
    }

    public BoardView getBoardVisual() {
        return this;
    }

    public void displayEffects() {
        
    }


}
