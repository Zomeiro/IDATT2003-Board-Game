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
        this.initialize();
    }


    public void initialize() {
        tiles.addAll(generateMainLoop());
        tiles.addAll(generateCenterPath(Direction.UP));
        tiles.addAll(generateCenterPath(Direction.RIGHT));
        tiles.addAll(generateCenterPath(Direction.DOWN));
        tiles.addAll(generateCenterPath(Direction.LEFT));
        tiles.addAll(generateHomeTiles(Direction.UP_RIGHT));
        tiles.addAll(generateHomeTiles(Direction.DOWN_RIGHT));
        tiles.addAll(generateHomeTiles(Direction.DOWN_LEFT));
        tiles.addAll(generateHomeTiles(Direction.UP_LEFT));
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
        int tileIndex = 0;
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

    // Hvis du ønsker å ha alle i én liste:
    return mainTiles;
    }

    public ArrayList<Tile> generateCenterPath(Direction direction) {
        ArrayList<Tile> centerPath = new ArrayList<>();
    
        int center    = boardSize / 2;    // 7 for 15×15
        int centerLen = center - 1;       // 6 ruter (fra 1→6 eller 13→8)
    
        // Finn start‐posisjon ett hakk innenfor kanten, avhengig av retning
        int startRow = center, startCol = center;
        switch(direction) {
            case DOWN:
                startRow = 1;      // ett fra toppraden (0)
                startCol = center; // midtkolonne
                break;
            case UP:
                startRow = boardSize - 2; // ett fra bunnen (14)
                startCol = center;
                break;
            case RIGHT:
                startRow = center;
                startCol = 1;       // ett fra venstre kolonne (0)
                break;
            case LEFT:
                startRow = center;
                startCol = boardSize - 2; // ett fra høyre kolonne (14)
                break;
            default:
                throw new IllegalArgumentException(
                    "generateCenterPath only supports cardinal Directions"
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
    
        // Finn offset = hvor langt inn start‐området (6×6) strekker seg fra kanten.
        int center   = boardSize / 2;     // 7 for 15×15
        int longStep = boardSize/2 - 2;   // 5 for 15×15
        int offset   = center - longStep; // 7 - 5 = 2
    
        // Beregn topp‐venstre posisjon for 2×2‐blokken, avhengig av hjørne
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
    
        // Hent start‐index for tile‐numre
        int tileIndex = tiles.size();
    
        // Lag 2×2 blokken
        for (int dr = 0; dr < 2; dr++) {
            for (int dc = 0; dc < 2; dc++) {
                int r = baseRow + dr;
                int c = baseCol + dc;
                // Valider at vi ikke går utenfor brettet
                if (r < 0 || r >= boardSize || c < 0 || c >= boardSize) {
                    throw new IllegalStateException(
                        String.format("HomeTile utenfor brettet: (%d,%d)", r, c)
                    );
                }
                Tile t = new Tile(tileIndex++, new int[]{r, c});
                homeTiles.add(t);
            }
        }
    
        return homeTiles;
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
