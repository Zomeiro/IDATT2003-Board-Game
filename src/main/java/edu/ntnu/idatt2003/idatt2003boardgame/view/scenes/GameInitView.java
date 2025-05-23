package edu.ntnu.idatt2003.idatt2003boardgame.view.scenes;

import edu.ntnu.idatt2003.idatt2003boardgame.service.GameInitController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the user interface for game initialization and configuration.
 * This view allows users to set up player profiles, select icons, choose a game board
 * (from presets, random generation, or a file), and start the game.
 * It interacts with a GameInitController to handle user actions and data.
 * 
 * @author Bjørn Adam Vangen
 */
public class GameInitView {
    private Stage primaryStage;
    private List<Button> iconBtns = new ArrayList<>();
    private List<HBox> playerFieldWrappers = new ArrayList<>();
    private List<Button> saveButtons = new ArrayList<>();
    private List<ComboBox<String>> playerDropdowns = new ArrayList<>();
    private VBox playerWrapper;

    private ComboBox<String> boardComboBox = new ComboBox<>();
    private Button loadFileButton = new Button("Load from File...");
    private Label selectedFileLabel = new Label("Board: Using preset/random");

    private Button addPlayerBtn = new Button("Add Player");
    private Button startGameBtn = new Button("Start game!");

    private GameInitController controller;

    /**
     * Constructs a new GameInitView.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */
    public GameInitView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Sets the controller that will handle actions and logic for this view.
     *
     * @param controller The GameInitController instance.
     */
    public void setController(GameInitController controller) {
        this.controller = controller;
    }

    private void applyStyles(Scene scene) {
        String cssPath = "/edu/ntnu/idatt2003/idatt2003boardgame/css/styles.css";
        try {
            scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
        } catch (Exception e) {
            System.err.println("CSS not found: " + cssPath);
        }
    }

    /**
     * Initializes the UI components and layout for the game initialization screen.
     * This includes setting up board selection options, player addition buttons,
     * and the start game button. It also applies CSS styling to the scene.
     */
    public void init() {
        primaryStage.setTitle("Snakes & Ladders Config");

        VBox menuWrapper = new VBox();
        menuWrapper.getStyleClass().add("game-init-wrapper");

        //board Selection
        HBox boardSelectionBox = new HBox();
        boardSelectionBox.getStyleClass().add("board-selection-box");
        boardComboBox.setPromptText("Select Board Preset/Random");
        boardSelectionBox.getChildren().addAll(boardComboBox, loadFileButton);

        VBox boardWrapper = new VBox(5);
        boardWrapper.setAlignment(Pos.CENTER);
        boardWrapper.getChildren().addAll(boardSelectionBox, selectedFileLabel);
        menuWrapper.getChildren().add(boardWrapper);

        //add player button
        HBox addPlayerBtnWrapper = new HBox();
        addPlayerBtnWrapper.getChildren().add(addPlayerBtn);
        addPlayerBtnWrapper.setAlignment(Pos.CENTER);
        menuWrapper.getChildren().add(addPlayerBtnWrapper);

        //player fields
        playerWrapper = new VBox();
        playerWrapper.setAlignment(Pos.CENTER);
        menuWrapper.getChildren().add(playerWrapper);

        //start game button
        startGameBtn.getStyleClass().add("start-button");
        HBox startButtonWrapper = new HBox();
        startButtonWrapper.setAlignment(Pos.CENTER);
        startButtonWrapper.getChildren().add(startGameBtn);
        menuWrapper.getChildren().add(startButtonWrapper);

        //event handlers
        addPlayerBtn.setOnAction(e -> controller.addPlayer());
        startGameBtn.setOnAction(e -> controller.startGame());

        Scene menu = new Scene(menuWrapper);
        applyStyles(menu); // Apply CSS
        primaryStage.setScene(menu);
    }

    /**
     * Adds a new player configuration field to the view.
     * Each field includes an icon button, a dropdown/editable ComboBox for player name,
     * and a save button.
     *
     * @param playerNames An array of existing player names to populate the dropdown.
     * @param index The index for the new player field (used for tracking and event handling).
     * @param initialIconFileName The filename of the initial icon to display for this player.
     * @param iconBtnHandler The event handler for the icon button.
     * @param dropdownHandler The event handler for the player name ComboBox.
     * @param saveBtnHandler The event handler for the save button.
     * @param initialIconImage The initial Image object for the icon button.
     */
    public void addPlayerField(
        String[] playerNames, int index,
        String initialIconFileName,
        EventHandler<ActionEvent> iconBtnHandler,
        EventHandler<ActionEvent> dropdownHandler,
        EventHandler<ActionEvent> saveBtnHandler,
        Image initialIconImage) {

        ComboBox<String> playerDropdown = new ComboBox<>();
        playerDropdown.setPromptText("Player " + (index + 1));
        playerDropdown.setEditable(true);

        if (playerNames != null) {
            for (String playername : playerNames) {
                if (playername != null && !playername.isEmpty()) {
                    playerDropdown.getItems().add(playername);
                }
            }
        }
        playerDropdowns.add(playerDropdown);

        Button iconBtn = new Button();
        iconBtn.getStyleClass().add("icon-button");
        iconBtns.add(iconBtn);

        ImageView buttonIMG = new ImageView(initialIconImage);
        buttonIMG.setFitHeight(40);
        buttonIMG.setFitWidth(40);
        iconBtn.setGraphic(buttonIMG);

        Button saveButton = new Button("Save new");
        saveButtons.add(saveButton);

        HBox playerFieldWrapper = new HBox();
        playerFieldWrapper.getStyleClass().add("player-field-wrapper");
        playerFieldWrapper.getChildren().addAll(iconBtn, playerDropdown, saveButton);
        playerFieldWrappers.add(playerFieldWrapper);

        playerWrapper.getChildren().add(playerFieldWrapper);

        iconBtn.setOnAction(iconBtnHandler);
        playerDropdown.setOnAction(dropdownHandler);
        saveButton.setOnAction(saveBtnHandler);
    }

    //getters
    /**
     * Gets the ComboBox used for selecting or entering a board preset/random option.
     * @return The ComboBox for board selection.
     */
    public ComboBox<String> getBoardComboBox() { return boardComboBox; }
    /**
     * Gets the Button used for triggering the file chooser to load a board from a file.
     * @return The "Load from File..." Button.
     */
    public Button getLoadFileButton() { return loadFileButton; }

    //setters
    /**
     * Sets the items available in the board selection ComboBox.
     * @param options A list of strings representing the board options.
     */
    public void setBoardOptions(List<String> options) {
        boardComboBox.getItems().setAll(options);
    }

    /**
     * Sets the text of the label that displays information about the selected board
     * (e.g., preset name, random option, or loaded file name).
     * @param text The text to display on the label.
     */
    public void setSelectedFileLabel(String text) {
        selectedFileLabel.setText(text);
    }

    /**
     * Clears the current selection in the board ComboBox and resets its prompt text.
     */
    public void clearBoardComboBoxSelection() {
        boardComboBox.getSelectionModel().clearSelection();
        boardComboBox.setPromptText("Select Board Preset/Random");
    }

    /**
     * Sets the selected value of the board ComboBox.
     * @param value The string value to set as selected.
     */
    public void setBoardComboBoxValue(String value) {
        boardComboBox.setValue(value);
    }

    /**
     * Updates the icon displayed on a player's icon button.
     * @param playerIndex The index of the player field whose icon button to update.
     * @param iconImage The new Image to display on the button.
     */
    public void updateIconButton(int playerIndex, Image iconImage) {
        if (playerIndex >= 0 && playerIndex < iconBtns.size()) {
            Button iconBtn = iconBtns.get(playerIndex);
            ImageView buttonIMG = new ImageView(iconImage);
            buttonIMG.setFitHeight(40);
            buttonIMG.setFitWidth(40);
            iconBtn.setGraphic(buttonIMG);
        }
    }

    /**
     * Updates the text of a player's save button (e.g., "Save new", "Updated", "Failed").
     * @param playerIndex The index of the player field whose save button text to update.
     * @param text The new text for the save button.
     */
    public void updateSaveButtonText(int playerIndex, String text) {
        if (playerIndex >= 0 && playerIndex < saveButtons.size()) {
            saveButtons.get(playerIndex).setText(text);
        }
    }

    /**
     * Gets the player name entered or selected in a specific player field's ComboBox.
     * @param playerIndex The index of the player field.
     * @return The string value from the ComboBox, or null if the index is invalid.
     */
    public String getPlayerName(int playerIndex) {
        if (playerIndex >= 0 && playerIndex < playerDropdowns.size()) {
            return playerDropdowns.get(playerIndex).getValue();
        }
        return null;
    }

    /**
     * Adds a new player name to the items list of a specific player field's ComboBox.
     * This is typically used after a new player profile is saved.
     * @param playerIndex The index of the player field whose ComboBox to update.
     * @param playerName The player name to add to the ComboBox items.
     */
    public void addPlayerToDropdown(int playerIndex, String playerName) {
        if (playerIndex >= 0 && playerIndex < playerDropdowns.size()) {
            playerDropdowns.get(playerIndex).getItems().add(playerName);
        }
    }

    /**
     * Sets the text of the main "Start game!" button.
     * @param text The text to display on the start game button.
     */
    public void setStartGameButtonText(String text) {
        startGameBtn.setText(text);
    }

    /**
     * Gets the list of ComboBoxes used for player name input/selection.
     * @return A list of ComboBox instances.
     */
    public List<ComboBox<String>> getPlayerDropdowns() {
        return playerDropdowns;
    }

    /**
     * Makes the primary stage visible.
     * This method is typically called after the scene has been initialized and set.
     */
    public void show() {
        primaryStage.show();
    }
}