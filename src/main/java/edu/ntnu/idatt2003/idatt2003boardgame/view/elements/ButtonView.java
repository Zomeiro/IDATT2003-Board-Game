package edu.ntnu.idatt2003.idatt2003boardgame.view.elements;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * A custom JavaFX {@link HBox} component that encapsulates a "Roll dice" button.
 * It provides a simple way to create and manage the dice rolling button and its action.
 * 
 * @author Hector Mendana Morales
 */
public class ButtonView extends HBox {

    /**
     * Constructs a new ButtonView.
     * It creates a "Roll dice" button and sets its action to the provided {@link Runnable}.
     *
     * @param onDiceRoll The {@code Runnable} action to be executed when the "Roll dice" button is clicked.
     */
    public ButtonView(Runnable onDiceRoll) {

        Button rollDiceButton = new Button("Roll dice");
        rollDiceButton.setOnAction(e -> {
            onDiceRoll.run();
        });
        this.getChildren().add(rollDiceButton);
    }
}