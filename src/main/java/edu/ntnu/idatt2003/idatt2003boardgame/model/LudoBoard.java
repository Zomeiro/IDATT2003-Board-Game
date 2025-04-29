package edu.ntnu.idatt2003.idatt2003boardgame.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;
import edu.ntnu.idatt2003.idatt2003boardgame.util.Direction;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Tile;

public class LudoBoard {
    private final int boardSize;
    private ArrayList<Tile> tiles;

    public LudoBoard() {
        this.tiles = new ArrayList<>();
        this.boardSize = 15;
        System.out.println("Board size successfully set to " + boardSize);
        
        this.initialize();
    }

    public LudoBoard(int boardSize) {
        this.tiles = new ArrayList<>();
        if (boardSize % 2 == 0) {
            this.boardSize = boardSize +1;
            System.out.println("Board size must be odd. Setting to " + (boardSize + 1));
        } else {
            this.boardSize = boardSize;
            System.out.println("Board size successfully set to " + boardSize);
        }

        this.initialize();
    }

    public void initialize() {
        
    }

    public ArrayList<Tile> generateMainLoop() {
        ArrayList<Tile> mainTiles = new ArrayList<>();
        int longStep = 5;
        int shortStep = 2; //needs to be a multiple of 2

        Direction[] mainDirections = new Direction[] {
            Direction.UP, Direction.RIGHT, Direction.DOWN, //North bow
            Direction.DOWN_RIGHT, //Move to East Bow start
            Direction.RIGHT, Direction.DOWN, Direction.LEFT, //East Bow
            Direction.DOWN_LEFT, //Move to South Bow start
            Direction.DOWN, Direction.LEFT, Direction.UP, //South Bow
            Direction.UP_LEFT, //Move to West Bow start
            Direction.LEFT, Direction.UP, Direction.RIGHT, //West Bow
            Direction.UP_RIGHT //Move to North Bow start

        };
        
        int[] mainSteps = new int[] {
            longStep, shortStep, longStep,1, //repeat code, I know. Might change later
            longStep, shortStep, longStep,1,
            longStep, shortStep, longStep,1,
            longStep, shortStep, longStep,1 
        };
        int tileIndex = 0;
        int[] coordinates = new int[] {6,6}; //coords start at 0 and go to 14
        
        for (int i = 0; i<mainDirections.length;i++) {
            Direction direction = mainDirections[i];
            int steps = mainSteps[i];

            for(int j = 0; j<steps; j++) {
                coordinates[0] += direction.getX();
                coordinates[1] += direction.getY();
                
                Tile tile = new Tile(tileIndex, coordinates);    
                
                tileIndex++;
                
            }
        }

        return mainTiles;
    }

    public ArrayList<Tile> generateCenterPath(Direction direction) {
        ArrayList<Tile> centerPath = new ArrayList<>();
        //pass
        return centerPath;

    }
    
}
