package edu.ntnu.idatt2003.idatt2003boardgame.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import edu.ntnu.idatt2003.idatt2003boardgame.util.Direction;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Tile;

public class LudoBoard {
    private final int boardSize;
    private ArrayList<Tile> tiles;
    private List<Tile> mainLoop;
    private List<Tile> centerUp, centerRight, centerDown, centerLeft;
    private List<Tile> homeUpLeft, homeUpRight, homeDownRight, homeDownLeft;

    public LudoBoard() {
        this.tiles = new ArrayList<>();
        this.boardSize = 15;
        this.initialize();
    }


    public void initialize() {
        tiles.addAll(generateMainLoop());
        tiles.addAll(generateCenterPath(Direction.UP));
        tiles.addAll(generateCenterPath(Direction.RIGHT));
        tiles.addAll(generateCenterPath(Direction.DOWN));
        tiles.addAll(generateCenterPath(Direction.LEFT));
        tiles.addAll(generateHomeTiles(Direction.UP_LEFT));
        tiles.addAll(generateHomeTiles(Direction.UP_RIGHT));
        tiles.addAll(generateHomeTiles(Direction.DOWN_RIGHT));
        tiles.addAll(generateHomeTiles(Direction.DOWN_LEFT));
        
    }
    

    public ArrayList<Tile> generateMainLoop() {
        ArrayList<Tile> mainTiles = new ArrayList<>();
        int longStep = 5;
        int shortStep = 2; //needs to be a multiple of 2

        Direction[] dirs = new Direction[] {
            Direction.UP, Direction.RIGHT, Direction.DOWN, //North bow
            Direction.DOWN_RIGHT, //Move to East Bow start
            Direction.RIGHT, Direction.DOWN, Direction.LEFT, //East Bow
            Direction.DOWN_LEFT, //Move to South Bow start
            Direction.DOWN, Direction.LEFT, Direction.UP, //South Bow
            Direction.UP_LEFT, //Move to West Bow start
            Direction.LEFT, Direction.UP, Direction.RIGHT, //West Bow
            Direction.UP_RIGHT //Move to North Bow start

        };
        
        int[] steps = new int[] {
            longStep, shortStep, longStep,1, //repeat code, I know. Might change later
            longStep, shortStep, longStep,1,
            longStep, shortStep, longStep,1,
            longStep, shortStep, longStep,1 
        };
        int tileIndex = tiles.size();
        int row = 5, col = 6;

        for (int seg = 0; seg < dirs.length; seg++) {
            Direction d = dirs[seg];
            for (int i = 0; i < steps[seg]; i++) {
                row += d.getY();   
                col += d.getX();

                int[] coordCopy = new int[]{ row, col };

                Tile tile = new Tile(tileIndex++, coordCopy);
                mainTiles.add(tile);
            }
        }

    return mainTiles;
    }

    public ArrayList<Tile> generateCenterPath(Direction direction) {
        ArrayList<Tile> centerPath = new ArrayList<>();
    
        int center = boardSize / 2;
        int centerLen = center - 1;       
    
        //finding start position on away from edge 
        int startRow = center, startCol = center;
        switch(direction) {
            case DOWN:
                startRow = 1;  
                startCol = center; 
                break;
            case UP:
                startRow = boardSize - 2;
                startCol = center;
                break;
            case RIGHT:
                startRow = center;
                startCol = 1; 
                break;
            case LEFT:
                startRow = center;
                startCol = boardSize - 2;
                break;
            default:
                throw new IllegalArgumentException(
                    "generateCenterPath only supports directions UP, RIGHT, DOWN and LEFT"
                );
        }
    
        // Nummer for første center‐tile
        int tileIndexStart = tiles.size();
    
        // Lag centerLen nye ruter innover mot midten
        for (int i = 0; i < centerLen; i++) {
            int r = startRow + direction.getY() * i;
            int c = startCol + direction.getX() * i;
    
            if (r < 0 || r >= boardSize || c < 0 || c >= boardSize) {
                throw new IllegalStateException(
                    "CenterPath utenfor brettet: (" + r + "," + c + ")"
                );
            }
    
            Tile t = new Tile(tileIndexStart + i, new int[]{r, c});
            centerPath.add(t);
        }
    
        return centerPath;
    }

    public ArrayList<Tile> generateHomeTiles(Direction direction) {
        ArrayList<Tile> homeTiles = new ArrayList<>();
    
        int center   = boardSize / 2;
        int longStep = boardSize/2 - 2;   
        int offset   = center - longStep;
    
        int baseRow, baseCol;
        switch(direction) {
            case UP_LEFT:
                baseRow = offset;
                baseCol = offset;
                break;
            case UP_RIGHT:
                baseRow = offset;
                baseCol = boardSize - offset - 2;
                break;
            case DOWN_RIGHT:
                baseRow = boardSize - offset - 2;
                baseCol = boardSize - offset - 2;
                break;
            case DOWN_LEFT:
                baseRow = boardSize - offset - 2;
                baseCol = offset;
                break;
            default:
                throw new IllegalArgumentException(
                    "generateHomeTiles only supports diagonal Directions"
                );
        }
    
        //get start index for home tiles
        int tileIndex = tiles.size();
    
        //make 4 home tiles
        for (int dr = 0; dr < 2; dr++) {
            for (int dc = 0; dc < 2; dc++) {
                int r = baseRow + dr;
                int c = baseCol + dc;
                //validate no out of bounds
                if (r < 0 || r >= boardSize || c < 0 || c >= boardSize) {
                    throw new IllegalStateException(
                        String.format("Home tile out of bounds: (%d,%d)", r, c)
                    );
                }
                Tile t = new Tile(tileIndex++, new int[]{r, c});
                homeTiles.add(t);
            }
        }
    
        return homeTiles;
    }
    

public HashMap<String, List<Integer>> getColorPaths() {
    HashMap<String, List<Integer>> colorPaths = new HashMap<>();

    //red path (starts at main loop index 7, ends at index 5 before entering center)
    List<Integer> redPath = new ArrayList<>();
    //home tiles first
    redPath.addAll(List.of(76, 77, 78, 79));
    //start tile and main loop (starting from index 7, going 51 steps to end at index 5)
    for (int i = 0; i < 51; i++) {
        redPath.add((46 + i) % 52);
    }
    //center path RIGHT (indices 58-63)
    redPath.addAll(List.of(58, 59, 60, 61, 62, 63));
    
    //blue path (starts at main loop index 20, ends at index 18 before entering center)  
    List<Integer> bluePath = new ArrayList<>();
    // Home tiles first
    bluePath.addAll(List.of(80, 81, 82, 83));
    // Start tile and main loop (starting from index 20, going 51 steps to end at index 18)
    for (int i = 0; i < 51; i++) {
        bluePath.add((7 + i) % 52);
    }
    // Center path DOWN (indices 64-69)
    bluePath.addAll(List.of(64, 65, 66, 67, 68, 69));
    
    //Yellow path (starts at main loop index 33, ends at index 31 before entering center)
    List<Integer> yellowPath = new ArrayList<>();
    //home tiles first
    yellowPath.addAll(List.of(84, 85, 86, 87));
    // start tile and main loop (starting from index 33, going 51 steps to end at index 31)
    for (int i = 0; i < 51; i++) {
        yellowPath.add((20 + i) % 52);
    }
    //center path LEFT (indices 70-75)
    yellowPath.addAll(List.of(70, 71, 72, 73, 74, 75));
    
    
    //green path (starts at main loop index 46, ends at index 44 before entering center)
    List<Integer> greenPath = new ArrayList<>();
    //home tiles
    greenPath.addAll(List.of(88, 89, 90, 91));
    //start tile and main loop (starting from index 46, going 51 steps to end at index 44)
    for (int i = 0; i < 51; i++) {
        greenPath.add((33 + i) % 52);
    }
    //center path UP (indices 52-57)
    greenPath.addAll(List.of(52, 53, 54, 55, 56, 57));
    
    colorPaths.put("RED", redPath);
    colorPaths.put("BLUE", bluePath);
    colorPaths.put("YELLOW", yellowPath);
    colorPaths.put("GREEN", greenPath);
    
    return colorPaths;
}
    
    // made this for debugging purposes. not needed elsewhere
    public void printAsciiBoard() { 
    for (int i = 0; i < boardSize; i++) {
        for (int j = 0; j < boardSize; j++) {
            boolean found = false;
            for (Tile tile : tiles) {
                if (tile.getCoordinates()[0] == i && tile.getCoordinates()[1] == j) {
                    System.out.print(String.format("%2d ", tile.getNumber()));
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.print(".  ");
            }
        }
        System.out.println();
    }
}

    
}
