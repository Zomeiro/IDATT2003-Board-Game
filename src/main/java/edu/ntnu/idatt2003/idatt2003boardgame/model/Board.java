package edu.ntnu.idatt2003.idatt2003boardgame.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;

import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.Effect;

public class Board {
    public final Random randomGenerator;
    private final ArrayList<Tile> tiles;
    private final HashMap<Tile, Effect> effectMap;
    private final int boardWidth;
    private final int boardHeight;
    private final int tileCount;

    public Board() {
        this.randomGenerator = new Random();
        this.tiles = new ArrayList<>();
        this.effectMap = new HashMap<>();
        this.boardWidth = 10;
        this.boardHeight = 9;
        this.tileCount = boardWidth * boardHeight;

        IntStream.rangeClosed(1, tileCount)
            .forEach(i -> tiles.add(new Tile(i)));

    }

    public Board(int boardWidth, int boardHeight) {
        this.randomGenerator = new Random();
        this.tiles = new ArrayList<>();
        this.effectMap = new HashMap<>();

        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.tileCount = boardWidth * boardHeight;

        IntStream.rangeClosed(1, tileCount)
            .forEach(i -> tiles.add(new Tile(i)));

    }
    
    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public Tile getTileInIndex(int index) {
        return tiles.get(index);
    }

    public HashMap<Tile, Effect> getEffectMap() {
        return effectMap;
    }

    public int getTileCount() {
        return tileCount;
    }
    public int getBoardWidth() {
        return boardWidth;
    }
    public int getBoardHeight() {
        return boardHeight;
    }


}
