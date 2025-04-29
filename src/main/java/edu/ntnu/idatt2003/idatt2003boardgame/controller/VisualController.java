package edu.ntnu.idatt2003.idatt2003boardgame.controller;

import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.BoardVisual;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.ButtonVisual;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.DiceAnimation;
import javafx.scene.layout.HBox;

public class VisualController {
    private final GameController gameController;
    private final DiceAnimation diceVisual = new DiceAnimation();
    private final ButtonVisual diceButton;
    private final BoardVisual boardVisual;


    public VisualController(GameController gameController, BoardVisual boardVisual) {
        this.gameController = gameController;
        this.boardVisual = boardVisual;
        this.diceButton = new ButtonVisual(() -> gameController.handleRollDice());
    }

    public void updateEntireBoard() {
        boardVisual.updateEntireBoard();
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
