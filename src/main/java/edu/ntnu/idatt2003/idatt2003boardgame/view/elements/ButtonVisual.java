package edu.ntnu.idatt2003.idatt2003boardgame.view.elements;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;


public class ButtonVisual extends HBox {

    public ButtonVisual(Runnable onDiceRoll) {

        Button rollDiceButton = new Button("Roll dice");
        rollDiceButton.setOnAction(e -> {
            onDiceRoll.run();
            
        });
        
        this.getChildren().add(rollDiceButton);
        this.setSpacing(10);
        
    }

}

