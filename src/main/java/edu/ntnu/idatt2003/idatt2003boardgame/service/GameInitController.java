package edu.ntnu.idatt2003.idatt2003boardgame.service;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.repository.PlayerRepository;
import edu.ntnu.idatt2003.idatt2003boardgame.view.scenes.GameInitView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.control.ComboBox;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Controls the game initialization process, managing player setup,
 * board selection, and interaction with the {@link GameInitView}.
 * It handles loading player data, selecting icons, and preparing
 * the necessary configurations before starting a game.
 * 
 * @author Bjørn Adam Vangen
 */
public class GameInitController {

    private final String ICON_RELATIVE_PATH = "/edu/ntnu/idatt2003/idatt2003boardgame/PlayerIcons/";
    private List<String> iconFileNames = new ArrayList<>();
    private PlayerRepository playerDataAccess = new PlayerRepository();
    protected Stage primaryStage;
    protected GameInitView view;
    private ArrayList<Integer> playerIconIndexes = new ArrayList<>();

    protected String selectedBoardOption = null;
    protected String selectedFilePath = null;

    /**
     * Represents the types of games that can be selected or initialized.
     */
    public enum GameType {
        /** Snakes and Ladders game type. */
        SnL,
        /** Ludo game type. */
        LUDO,
    }
    private GameType selectedGameType = GameType.SnL;

    /**
     * Constructs a GameInitController.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */
    public GameInitController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.view = new GameInitView(this.primaryStage);
        view.setController(this);
        init();
    }

    private void init() {
        iconFileNames = playerDataAccess.getIconNames();
        view.init();
        setupBoardSelection();
        addPlayer();
        addPlayer();
    }

    private void setupBoardSelection() {
        List<String> boardOptions = new ArrayList<>(BoardGameFactory.listSnLPresets());
        boardOptions.add("Random Easy");
        boardOptions.add("Random Medium");
        boardOptions.add("Random Hard");
        view.setBoardOptions(boardOptions);

        if (!boardOptions.isEmpty()) {
            selectedBoardOption = boardOptions.get(0);
            view.setBoardComboBoxValue(selectedBoardOption);
            view.setSelectedFileLabel("Board: " + selectedBoardOption);
        } else {
             view.setSelectedFileLabel("Board: No presets found. Load a file.");
        }

        //handlers
        view.getBoardComboBox().setOnAction(e -> handleBoardSelection());
        view.getLoadFileButton().setOnAction(e -> handleLoadFile());
    }

    private void handleBoardSelection() {
        String selection = view.getBoardComboBox().getValue();
        if (selection != null) {
            selectedBoardOption = selection;
            selectedFilePath = null;
            view.setSelectedFileLabel("Board: " + selection);
        }
    }

    private void handleLoadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Board JSON File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            selectedFilePath = selectedFile.getAbsolutePath();
            selectedBoardOption = null;
            view.setSelectedFileLabel("Board: " + selectedFile.getName());
            view.clearBoardComboBoxSelection();
        }
    }

    /**
     * Adds a new player configuration field to the game initialization view.
     * If the maximum number of players (currently 4) is reached, no new field is added.
     * Populates the new field with default player names and an initial icon.
     */
    public void addPlayer() {
        int index = playerIconIndexes.size();
        if (index >= 4) { //limit to 4 players
            System.out.println("Maximum players reached.");
            return;
        }
        playerIconIndexes.add(0);

        String[] playerNames = playerDataAccess.getPlayerNames();
        Image initialIconImage = playerDataAccess.getImageFromFileName(iconFileNames.get(0));

        //create handlers specific to player index
        EventHandler<ActionEvent> iconBtnHandler = e -> handleIconButtonClick(index);
        EventHandler<ActionEvent> dropdownHandler = e -> handleDropdownSelection(index);
        EventHandler<ActionEvent> saveBtnHandler = e -> handleSaveButton(index);

        view.addPlayerField(playerNames, index, iconFileNames.get(0),
                iconBtnHandler, dropdownHandler, saveBtnHandler, initialIconImage);
    }

    private void handleIconButtonClick(int playerIndex) {
        int currentIndex = playerIconIndexes.get(playerIndex);
        currentIndex = (currentIndex + 1) % iconFileNames.size(); // Cycle through icons

        playerIconIndexes.set(playerIndex, currentIndex);
        Image iconImage = playerDataAccess.getImageFromFileName(iconFileNames.get(currentIndex));
        view.updateIconButton(playerIndex, iconImage);
    }

    private void handleDropdownSelection(int playerIndex) {
        String selectedPlayer = view.getPlayerName(playerIndex);
        String[] playerNames = playerDataAccess.getPlayerNames();

        if (selectedPlayer != null && Arrays.stream(playerNames).anyMatch(selectedPlayer::equals)) {
            try {
                String relativeIconPath = playerDataAccess.getPlayerIconByPlayerName(selectedPlayer);
                String fileName = new File(relativeIconPath).getName();
                int iconIndex = iconFileNames.indexOf(fileName);

                if (iconIndex != -1) {
                    playerIconIndexes.set(playerIndex, iconIndex);
                    Image iconImage = playerDataAccess.getImageFromFileName(fileName);
                    view.updateIconButton(playerIndex, iconImage);
                } else {
                     System.out.println("Icon '" + fileName + "' not found in loaded images list.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void handleSaveButton(int playerIndex) {
        String playerName = view.getPlayerName(playerIndex);
        if (playerName == null || playerName.trim().isEmpty()) {
            System.out.println("Player name cannot be empty.");
            return;
        }

        int selectedIconIndex = playerIconIndexes.get(playerIndex);
        String iconFileName = iconFileNames.get(selectedIconIndex);
        String relativeIconPath = ICON_RELATIVE_PATH + iconFileName;

        try {
            String[] playerNames = playerDataAccess.getPlayerNames();
            if (Arrays.stream(playerNames).anyMatch(playerName::equals)) {
                playerDataAccess.changeIcon(playerName, relativeIconPath);
                view.updateSaveButtonText(playerIndex, "Updated");
            } else {
                playerDataAccess.registerNewPlayer(playerName, relativeIconPath);
                view.updateSaveButtonText(playerIndex, "Saved");
                view.addPlayerToDropdown(playerIndex, playerName);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            view.updateSaveButtonText(playerIndex, "Failed");
        }
    }

    //method should be overridden in subclasses
    /**
     * Placeholder method for starting the game.
     * This base implementation indicates an error and is intended to be overridden
     * by subclasses that define specific game starting procedures.
     */
    public void startGame() {
        System.err.println("Base GameInitController.startGame() called - Should be overridden!");
        view.setStartGameButtonText("Error - Please Restart");
    }

    /**
     * Retrieves the list of currently configured players based on the selections
     * made in the game initialization view.
     *
     * @return An {@link ArrayList} of {@link Player} objects. Returns {@code null} if no
     * valid players are selected or if an error occurs during player creation.
     * Duplicate player names are skipped.
     */
    public ArrayList<Player> getCurrentPlayers() {
        ArrayList<Player> playerList = new ArrayList<>();
        List<ComboBox<String>> playerDropdowns = view.getPlayerDropdowns();

        for (int i = 0; i < playerDropdowns.size(); i++) {
            String playerName = playerDropdowns.get(i).getValue();

            if (playerName != null && !playerName.trim().isEmpty()) {
                try {
                    String relativeIconPath = playerDataAccess.getPlayerIconByPlayerName(playerName);
                    String iconFileName = new File(relativeIconPath).getName();
                    String fullPath = ICON_RELATIVE_PATH + iconFileName;

                    Player player = new Player(fullPath, playerName);

                    //no duplicate players
                    if (!playerList.stream().anyMatch(p -> p.getName().equals(playerName))) {
                         playerList.add(player);
                    } else {
                         System.out.println("Player '" + playerName + "' already added. Skipping duplicate.");
                    }

                } catch (IllegalArgumentException e) {
                    System.out.println("Could not find/add player '" + playerName + "': " + e.getMessage());
                }
            }
        }

        if (playerList.isEmpty()) {
            System.out.println("No valid players selected");
            return null;
        }
        return playerList;
    }

    /**
     * Sets the type of game selected for initialization.
     *
     * @param type The {@link GameType} to set.
     */
    public void setSelectedGameType(GameType type) {
        this.selectedGameType = type;
    }

    /**
     * Shows the game initialization view on the primary stage.
     */
    public void start() {
        view.show();
    }
}