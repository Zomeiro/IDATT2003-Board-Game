package edu.ntnu.idatt2003.idatt2003boardgame.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.function.Consumer;

import edu.ntnu.idatt2003.idatt2003boardgame.BoardGame;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Board;
import edu.ntnu.idatt2003.idatt2003boardgame.model.GameConfig;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.model.Rules;
import edu.ntnu.idatt2003.idatt2003boardgame.repository.JsonBoardRepository;


public final class BoardGameFactory {
  
  private static final int snl_presets = 3;

  private BoardGameFactory() { }
  
  public static List<String> listSnLPresets() {
    List<String> presets = new ArrayList<>();

    IntStream.range(0, snl_presets)
        .forEach(i -> presets.add("builtin-SnL-" + i));

    presets.addAll(JsonBoardRepository.listSnLBoards()); 

    return Collections.unmodifiableList(presets);
  }

  public static BoardGame createSnakesAndLadders(String preset, List<Player> players, Consumer<Player> onGameWon) {

    Board board;

    if (preset.startsWith("builtin-SnL-")) {
      int idx = Integer.parseInt(preset.substring("builtin-SnL-".length()));
      board = buildBuiltinSnL(idx);
    } else {
      board = JsonBoardRepository.loadBoardByName(preset);
    }

    GameConfig cfg = GameConfig.defaultConfig(players.size());
    return new BoardGame(board, players, cfg, onGameWon);  // ✅ med callback
  }



  private static Board buildBuiltinSnL(int idx) {
    if (idx < 0 || idx >= snl_presets)
      throw new IllegalArgumentException("Invalid built-in SnL-index: " + idx);

    return JsonBoardRepository.constructSnLBoardFromJSON(idx);
  }
}
