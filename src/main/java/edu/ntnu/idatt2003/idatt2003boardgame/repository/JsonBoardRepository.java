package edu.ntnu.idatt2003.idatt2003boardgame.repository;

import edu.ntnu.idatt2003.idatt2003boardgame.controller.GameController;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Board;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.BackToStartEffect;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.Effect;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.LadderEffect;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.SkipTurnEffect;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.SnakeEffect;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.PlaceholderEffect;

public class JsonBoardRepository {
    private static final File FILE = new File("src/main/resources/edu/ntnu/idatt2003/idatt2003boardgame/boards.json");

    public static Board constructSnLBoardFromJSON(int choice, GameController gameController) {
        Board board = new Board();

        try {
            String jsonText = new String(Files.readAllBytes(FILE.toPath()), StandardCharsets.UTF_8);

            JSONObject SnLBoard = new JSONObject(jsonText)
                .getJSONArray("games")
                .getJSONObject(0)
                .getJSONArray("SnL")
                .getJSONObject(choice);

            JSONArray tilesWithEffects = SnLBoard.getJSONArray("tiles");

            IntStream.range(0, tilesWithEffects.length())
                .forEach(i -> modifyEffectTileFromJSON(tilesWithEffects.getJSONObject(i), board, gameController));

        } catch (IOException e) {
            System.err.println("Failed to read boards.json file: " + e.getMessage());
            System.err.println("Looking for file at: " + FILE.getAbsolutePath());
            e.printStackTrace();
            throw new RuntimeException("Could not load the board: " + e.getMessage());
        }

        return board;
    }

    public static void modifyEffectTileFromJSON(JSONObject tileWithEffect, Board board, GameController gameController) {
        int tileNumber = tileWithEffect.getInt("tile");
        String effectType = tileWithEffect.getString("effect");

        Effect effect;
        int target;

        switch (effectType) {
            case "Ladder":
                target = tileWithEffect.getInt("target");
                board.getTiles().get(target - 1).setEffect(new PlaceholderEffect());
                effect = new LadderEffect(tileNumber, target);
                break;

            case "Snake":
                target = tileWithEffect.getInt("target");
                board.getTiles().get(target - 1).setEffect(new PlaceholderEffect());
                effect = new SnakeEffect(tileNumber, target);
                break;

            case "LoseTurn":
                effect = new SkipTurnEffect();
                break;

            case "Back":
                effect = new BackToStartEffect();
                break;

            default:
                throw new AssertionError("Unknown effect type: " + effectType);
        }

        board.getTiles().get(tileNumber - 1).setEffect(effect);
    }
}