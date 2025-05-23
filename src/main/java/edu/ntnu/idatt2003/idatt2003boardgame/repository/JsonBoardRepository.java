package edu.ntnu.idatt2003.idatt2003boardgame.repository;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Board;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.BackToStartEffect;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.Effect;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.LadderEffect;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.SkipTurnEffect;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.SnakeEffect;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.PlaceholderEffect;

/**
 * Repository for loading Snakes and Ladders boards from JSON files.
 * Assumes each board is defined in its own JSON file within the
 * 'src/main/resources/edu/ntnu/idatt2003/idatt2003boardgame/boards/' directory.
 */
public class JsonBoardRepository {

    /**
     * The directory containing the board JSON files.
     */
    private static final String boardDirectory = "src/main/resources/edu/ntnu/idatt2003/idatt2003boardgame/boards/";

    /**
     * Loads a board from a JSON file given its name.
     * The name should correspond to a JSON file (without the .json extension)
     * in the predefined boards' directory.
     *
     * @param name The name of the board to load.
     * @return The loaded Board object.
     * @throws RuntimeException if the board file is not found or cannot be read/parsed.
     */
    public static Board loadBoardByName(String name) {
        Path filePath = Paths.get(boardDirectory, name + ".json");
        return loadBoardFromFile(filePath);
    }

    /**
     * Loads a board from a JSON file given its absolute path.
     *
     * @param absolutePath The absolute path to the JSON file as a String.
     * @return The loaded Board object.
     * @throws RuntimeException if the file is not found or cannot be read/parsed.
     */
    public static Board loadBoardByAbsolutePath(String absolutePath) {
        Path filePath = Paths.get(absolutePath); // Convert String to Path
        return loadBoardFromFile(filePath);     // Call the internal loader
    }

    /**
     * Core logic to load a board from a given file path.
     *
     * @param filePath The Path object pointing to the JSON file.
     * @return The loaded Board object.
     * @throws RuntimeException if the file cannot be read or parsed.
     */
    private static Board loadBoardFromFile(Path filePath) {
        try {
            String jsonText = Files.readString(filePath, StandardCharsets.UTF_8);
            JSONObject boardJson = new JSONObject(jsonText);

            Board board = new Board();
            JSONArray tilesWithEffects = boardJson.getJSONArray("tiles");

            IntStream.range(0, tilesWithEffects.length())
                    .forEach(i -> modifyEffectTileFromJSON(tilesWithEffects.getJSONObject(i), board));

            return board;

        } catch (IOException e) {
            throw new RuntimeException("Could not read or find board file: " + filePath.toString() + " - " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Could not parse board file: " + filePath.toString() + " - " + e.getMessage(), e);
        }
    }


    /**
     * Modifies a tile on the board based on a JSONObject definition.
     *
     * @param tileWithEffect The JSONObject representing a tile with an effect.
     * @param board          The Board object to modify.
     */
    public static void modifyEffectTileFromJSON(JSONObject tileWithEffect, Board board) {
        int tileNumber = tileWithEffect.getInt("tile");
        String effectType = tileWithEffect.getString("effect");

        Effect effect;
        int target;

        switch (effectType) {
            case "Ladder":
                target = tileWithEffect.getInt("target");
                if (target > 0 && target <= board.getTiles().size()) {
                    board.getTiles().get(target - 1).setEffect(new PlaceholderEffect());
                }
                effect = new LadderEffect(tileNumber, target);
                break;

            case "Snake":
                target = tileWithEffect.getInt("target");
                if (target > 0 && target <= board.getTiles().size()) {
                    board.getTiles().get(target - 1).setEffect(new PlaceholderEffect());
                }
                effect = new SnakeEffect(tileNumber, target);
                break;

            case "LoseTurn":
                effect = new SkipTurnEffect();
                break;

            case "Back":
                effect = new BackToStartEffect();
                break;

            case "Placeholder":
                effect = new PlaceholderEffect();
                break;

            default:
                throw new AssertionError("Unknown effect type: " + effectType);
        }

        if (tileNumber > 0 && tileNumber <= board.getTiles().size()) {
            board.getTiles().get(tileNumber - 1).setEffect(effect);
        } else {
            System.err.println("Warning: Tile number " + tileNumber + " out of bounds. Ignoring effect.");
        }
    }

    /**
     * Lists the names of all available Snakes and Ladders boards
     * found in the boards' directory.
     *
     * @return A List of board names.
     * @throws RuntimeException if the boards' directory cannot be read.
     */
    public static List<String> listSnLBoards() {
        File folder = new File(boardDirectory);
        if (!folder.exists() || !folder.isDirectory()) {
             System.err.println("Boards directory not found or is not a directory: " + folder.getAbsolutePath() + ". Returning empty list.");
             return List.of(); // Return an empty list if not found
        }

        try (Stream<Path> paths = Files.walk(Paths.get(boardDirectory), 1)) { // Depth 1 to avoid subdirs
            return paths
                .filter(path -> !path.equals(Paths.get(boardDirectory))) // Exclude the directory itself
                .filter(Files::isRegularFile)
                .map(Path::getFileName)
                .map(Path::toString)
                .filter(name -> name.toLowerCase().endsWith(".json"))
                .map(name -> name.substring(0, name.length() - 5)) // Remove .json extension
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Could not list boards in directory: " + boardDirectory + " - " + e.getMessage(), e);
        }
    }
}