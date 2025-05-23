package edu.ntnu.idatt2003.idatt2003boardgame.view.layers.SnakesNLadders;

import java.util.Iterator;
import java.util.List;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Tile;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.LadderEffect;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.SnakeEffect;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.BoardView;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.TileView;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.transform.Rotate;

/**
 * A JavaFX Pane responsible for rendering visual representations of ladders and snakes
 * on top of a BoardView. It calculates the position, length, and rotation
 * for each ladder and snake based on their start and end tiles on the board.
 * 
 * @author Hector Mendana Morales
 */
public class LadderLayer extends Pane {

    private final BoardView boardView;

    /**
     * Constructs a new LadderLayer.
     * This layer will draw all specified ladders and snakes onto itself,
     * positioned relative to the provided BoardView.
     *
     * @param boardView The BoardView on which the tiles are laid out, used for coordinate calculations.
     * @param tilesWithLadders A list of Tile objects that have LadderEffects.
     * @param tilesWithSnakes A list of Tile objects that have SnakeEffects.
     */
    public LadderLayer(BoardView boardView, List<Tile> tilesWithLadders, List<Tile> tilesWithSnakes) {
        this.boardView = boardView;

        this.setPrefSize(536, 482);
        this.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        for (Tile tile : tilesWithLadders) {
            renderLadder((LadderEffect) tile.getEffect());
        }

        for (Tile tile : tilesWithSnakes) {
            renderSnake((SnakeEffect) tile.getEffect());
        }

    }

    private void renderLadder(LadderEffect ladder) {

        LadderVisual ladderVisual;

        Integer baseX = null;
        Integer baseY = null;

        Integer targetX = null;
        Integer targetY = null;

        Iterator<Node> tileIterator = boardView.getChildren().iterator();

        final int TILE_SIZE = 50;
        final int GAP = 4;
        final int spacing = TILE_SIZE + GAP;

        while (((baseX == null) || (targetX == null)) && (tileIterator.hasNext())) {
            Node tileNode = tileIterator.next();

            if (tileNode instanceof TileView tileView) {
                if (tileView.getTile().getNumber() == ladder.getBaseTileIndex()) {

                    baseX = BoardView.getColumnIndex(tileView);
                    baseY = BoardView.getRowIndex(tileView);

                } else if (tileView.getTile().getNumber() == ladder.getTargetTileIndex()) {

                    targetX = BoardView.getColumnIndex(tileView);
                    targetY = BoardView.getRowIndex(tileView);

                }
            }
        }

        int dx = targetX - baseX;
        int dy = targetY - baseY;

        double hypotenuse = Math.sqrt((dx * dx) + (dy * dy));

        ladderVisual = new LadderVisual(hypotenuse * spacing - TILE_SIZE / 2);
        ladderVisual.setLayoutX(baseX * spacing);
        ladderVisual.setLayoutY(baseY * spacing + TILE_SIZE / 2.0);

        double angle = Math.toDegrees(Math.atan2(dx, dy));

        System.out.println(angle);

        ladderVisual.getTransforms().add(new Rotate(-angle, 25, 0));

        this.getChildren().add(ladderVisual);

    }


    private void renderSnake(SnakeEffect snake) {

        SnakeLayer snakeLayer;

        Integer baseX = null;
        Integer baseY = null;

        Integer targetX = null;
        Integer targetY = null;

        Iterator<Node> tileIterator = boardView.getChildren().iterator();

        final int TILE_SIZE = 50;
        final int GAP = 4;
        final int spacing = TILE_SIZE + GAP;

        while (((baseX == null) || (targetX == null)) && (tileIterator.hasNext())) {
            Node tileNode = tileIterator.next();

            if (tileNode instanceof TileView tileView) {
                if (tileView.getTile().getNumber() == snake.getBaseTileIndex()) {

                    baseX = BoardView.getColumnIndex(tileView);
                    baseY = BoardView.getRowIndex(tileView);

                } else if (tileView.getTile().getNumber() == snake.getTargetTileIndex()) {

                    targetX = BoardView.getColumnIndex(tileView);
                    targetY = BoardView.getRowIndex(tileView);

                }
            }
        }

        int dx = targetX - baseX;
        int dy = targetY - baseY;

        double hypotenuse = Math.sqrt((dx * dx) + (dy * dy));

        snakeLayer = new SnakeLayer(hypotenuse * spacing - TILE_SIZE / 2);
        snakeLayer.setLayoutX(baseX * spacing);
        snakeLayer.setLayoutY(baseY * spacing + TILE_SIZE / 2.0);

        double angle = Math.toDegrees(Math.atan2(dx, dy));

        snakeLayer.getTransforms().add(new Rotate(-angle, 25, 0));

        this.getChildren().add(snakeLayer);

    }

}