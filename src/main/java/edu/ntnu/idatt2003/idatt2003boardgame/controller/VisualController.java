package edu.ntnu.idatt2003.idatt2003boardgame.controller;

import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.BoardView;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.ButtonView;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.DiceAnimation;
import javafx.scene.layout.HBox;

public class VisualController {
    private final GameController gameController;
    private final DiceAnimation diceVisual = new DiceAnimation();
    private final ButtonView diceButton;
    private final BoardView boardView;


    public VisualController(GameController gameController, BoardView boardView) {
        this.gameController = gameController;
        this.boardView = boardView;
        this.diceButton = new ButtonView(() -> gameController.handleRollDice());
    }

    public void updateEntireBoard() {
        boardView.updateEntireBoard();
    }

    public void displayRoll(int diceRoll) {
        diceVisual.displayRoll(diceRoll);
    }

    public HBox getDiceButton() {
        return diceButton;
    }

    public DiceAnimation getDiceAnimation() {
        return diceVisual;
    }
}
