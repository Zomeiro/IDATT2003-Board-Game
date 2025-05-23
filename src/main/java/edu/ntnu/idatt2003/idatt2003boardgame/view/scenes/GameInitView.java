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

    public void init() {
        primaryStage.setTitle("Snakes & Ladders Config");

        VBox menuWrapper = new VBox();
        menuWrapper.setAlignment(Pos.TOP_CENTER);
        menuWrapper.setSpacing(20); // Add some spacing
        menuWrapper.setPadding(new Insets(20)); // Add some padding

        String buttonStyle = "-fx-pref-height: 30; -fx-background-color: rgba(200,200,200);";

        //board Selection
        HBox boardSelectionBox = new HBox(10);
        boardSelectionBox.setAlignment(Pos.CENTER);
        boardComboBox.setPrefWidth(200);
        boardComboBox.setPromptText("Select Board Preset/Random");
        loadFileButton.setStyle(buttonStyle);
        boardSelectionBox.getChildren().addAll(boardComboBox, loadFileButton);

        VBox boardWrapper = new VBox(5);
        boardWrapper.setAlignment(Pos.CENTER);
        boardWrapper.getChildren().addAll(boardSelectionBox, selectedFileLabel);
        menuWrapper.getChildren().add(boardWrapper);

        //add player button
        addPlayerBtn.setStyle(buttonStyle);
        HBox addPlayerBtnWrapper = new HBox();
        addPlayerBtnWrapper.getChildren().add(addPlayerBtn);
        addPlayerBtnWrapper.setAlignment(Pos.CENTER);
        menuWrapper.getChildren().add(addPlayerBtnWrapper);

        //player fields
        playerWrapper = new VBox();
        playerWrapper.setSpacing(10);
        playerWrapper.setAlignment(Pos.CENTER);
        menuWrapper.getChildren().add(playerWrapper);

        //start game button
        startGameBtn.setStyle(buttonStyle + " -fx-font-size: 14px; -fx-font-weight: bold;");
        HBox startButtonWrapper = new HBox();
        startButtonWrapper.setAlignment(Pos.CENTER);
        startButtonWrapper.getChildren().add(startGameBtn);
        menuWrapper.getChildren().add(startButtonWrapper);

        //event handlers
        addPlayerBtn.setOnAction(e -> controller.addPlayer());
        startGameBtn.setOnAction(e -> controller.startGame());

        Scene menu = new Scene(menuWrapper, 600, 600);
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
        playerDropdown.setPrefHeight(40);
        playerDropdown.setPrefWidth(180);

        if (playerNames != null) {
            for (String playername : playerNames) {
                if (playername != null && !playername.isEmpty()) {
                    playerDropdown.getItems().add(playername);
                }
            }
        }
        playerDropdowns.add(playerDropdown);

        Button iconBtn = new Button();
        iconBtns.add(iconBtn);

        ImageView buttonIMG = new ImageView(initialIconImage);
        buttonIMG.setFitHeight(40);
        buttonIMG.setFitWidth(40);
        iconBtn.setGraphic(buttonIMG);

        Button saveButton = new Button("Save new");
        saveButtons.add(saveButton);

        HBox playerFieldWrapper = new HBox(10);
        playerFieldWrapper.getChildren().addAll(iconBtn, playerDropdown, saveButton);
        playerFieldWrapper.setAlignment(Pos.CENTER);
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