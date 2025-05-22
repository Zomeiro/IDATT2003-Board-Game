package edu.ntnu.idatt2003.idatt2003boardgame.service;

import edu.ntnu.idatt2003.idatt2003boardgame.BoardGame;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Board;
import edu.ntnu.idatt2003.idatt2003boardgame.model.GameConfig;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.*;
import edu.ntnu.idatt2003.idatt2003boardgame.repository.JsonBoardRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A factory class for creating BoardGame instances, particularly for
 * Snakes and Ladders. It provides methods to list available board presets,
 * load boards from JSON files (by name or absolute path), and generate
 * random boards.
 */
public final class BoardGameFactory {

    /**
     * Enum representing different difficulty levels for random board generation.
     */
    //suggested by Gemini
    public enum Difficulty {
        EASY(6, 4, 2, 1),
        MEDIUM(5, 5, 3, 2),
        HARD(4, 7, 4, 3);

        final int ladders;
        final int snakes;
        final int skipTurns;
        final int backToStarts;

        Difficulty(int ladders, int snakes, int skipTurns, int backToStarts) {
            this.ladders = ladders;
            this.snakes = snakes;
            this.skipTurns = skipTurns;
            this.backToStarts = backToStarts;
        }
    }

    private BoardGameFactory() { }

    public static List<String> listSnLPresets() {
        return Collections.unmodifiableList(JsonBoardRepository.listSnLBoards());
    }

    public static BoardGame createSnakesAndLadders(String preset, List<Player> players, Consumer<Player> onGameWon) {
        Board board = JsonBoardRepository.loadBoardByName(preset);
        GameConfig cfg = GameConfig.defaultConfig(players.size());
        return new BoardGame(board, players, cfg, onGameWon);
    }

    public static BoardGame createSnakesAndLaddersByAbsolutePath(String absolutePath, List<Player> players, Consumer<Player> onGameWon) {
        Board board = JsonBoardRepository.loadBoardByAbsolutePath(absolutePath);
        GameConfig cfg = GameConfig.defaultConfig(players.size());
        return new BoardGame(board, players, cfg, onGameWon);
    }

    public static BoardGame createRandomSnakesAndLadders(Difficulty difficulty, List<Player> players, Consumer<Player> onGameWon) {
        Board board = buildRandomSnL(difficulty);
        GameConfig cfg = GameConfig.defaultConfig(players.size());
        return new BoardGame(board, players, cfg, onGameWon);
    }

    /**
     * Builds a random Snakes and Ladders board based on the specified difficulty.
     * It now checks the actual board size to avoid IndexOutOfBounds errors.
     *
     * @param difficulty The difficulty level.
     * @return A new, randomly generated Board object.
     */
    private static Board buildRandomSnL(Difficulty difficulty) {
        Board board = new Board(); // Creates the board (size determined by Board class)
        Random random = new Random();

        int boardSize = board.getTiles().size();
        // Ensure we don't try to place effects on tile 1 or the last tile
        int maxTileForEffect = boardSize - 1;

        // Check if board is too small for effects
        if (maxTileForEffect < 2) {
            System.err.println("Warning: Board size (" + boardSize + ") is too small to add random effects. Returning empty board.");
            return board;
        }

        //from Gemini 
        List<Integer> availableTiles = IntStream.rangeClosed(2, maxTileForEffect)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(availableTiles, random);

        int currentIndex = 0;

        //ladders
        for (int i = 0; i < difficulty.ladders; i++) {
            if (currentIndex + 1 >= availableTiles.size()) break; // Stop if not enough tiles

            int tile1 = availableTiles.get(currentIndex++);
            int tile2 = availableTiles.get(currentIndex++);

            int start = Math.min(tile1, tile2);
            int end = Math.max(tile1, tile2);

            // Ensure start and end are within bounds (should be, but as a safeguard)
            if (start > 0 && end <= boardSize) {
                board.getTiles().get(start - 1).setEffect(new LadderEffect(start, end));
                board.getTiles().get(end - 1).setEffect(new PlaceholderEffect()); // Mark end tile
            }
        }

        // snakes
        for (int i = 0; i < difficulty.snakes; i++) {
            if (currentIndex + 1 >= availableTiles.size()) break;

            int tile1 = availableTiles.get(currentIndex++);
            int tile2 = availableTiles.get(currentIndex++);

            int start = Math.max(tile1, tile2);
            int end = Math.min(tile1, tile2);

            if (start > 0 && end <= boardSize && end > 0) { // Ensure end isn't 0
                board.getTiles().get(start - 1).setEffect(new SnakeEffect(start, end));
                board.getTiles().get(end - 1).setEffect(new PlaceholderEffect()); // Mark end tile
            }
        }

        //skip turn
        for (int i = 0; i < difficulty.skipTurns; i++) {
            if (currentIndex >= availableTiles.size()) break;
            int tile = availableTiles.get(currentIndex++);
             if (tile > 0 && tile <= boardSize) {
                board.getTiles().get(tile - 1).setEffect(new SkipTurnEffect());
             }
        }

        //back to start
        for (int i = 0; i < difficulty.backToStarts; i++) {
            if (currentIndex >= availableTiles.size()) break;
            int tile = availableTiles.get(currentIndex++);
             if (tile > 0 && tile <= boardSize) {
                board.getTiles().get(tile - 1).setEffect(new BackToStartEffect());
            }
        }

        return board;
    }
}