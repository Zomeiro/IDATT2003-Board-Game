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

    public GameInitView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

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
    public ComboBox<String> getBoardComboBox() { return boardComboBox; }
    public Button getLoadFileButton() { return loadFileButton; }

    //setters
    public void setBoardOptions(List<String> options) {
        boardComboBox.getItems().setAll(options);
    }

    public void setSelectedFileLabel(String text) {
        selectedFileLabel.setText(text);
    }

    public void clearBoardComboBoxSelection() {
        boardComboBox.getSelectionModel().clearSelection();
        boardComboBox.setPromptText("Select Board Preset/Random");
    }

    public void setBoardComboBoxValue(String value) {
        boardComboBox.setValue(value);
    }

    public void updateIconButton(int playerIndex, Image iconImage) {
        if (playerIndex >= 0 && playerIndex < iconBtns.size()) {
            Button iconBtn = iconBtns.get(playerIndex);
            ImageView buttonIMG = new ImageView(iconImage);
            buttonIMG.setFitHeight(40);
            buttonIMG.setFitWidth(40);
            iconBtn.setGraphic(buttonIMG);
        }
    }

    public void updateSaveButtonText(int playerIndex, String text) {
        if (playerIndex >= 0 && playerIndex < saveButtons.size()) {
            saveButtons.get(playerIndex).setText(text);
        }
    }

    public String getPlayerName(int playerIndex) {
        if (playerIndex >= 0 && playerIndex < playerDropdowns.size()) {
            return playerDropdowns.get(playerIndex).getValue();
        }
        return null;
    }

    public void addPlayerToDropdown(int playerIndex, String playerName) {
        if (playerIndex >= 0 && playerIndex < playerDropdowns.size()) {
            playerDropdowns.get(playerIndex).getItems().add(playerName);
        }
    }

    public void setStartGameButtonText(String text) {
        startGameBtn.setText(text);
    }

    public List<ComboBox<String>> getPlayerDropdowns() {
        return playerDropdowns;
    }

    public void show() {
        primaryStage.show();
    }
}