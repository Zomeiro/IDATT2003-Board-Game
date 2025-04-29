package edu.ntnu.idatt2003.idatt2003boardgame.view.elements;

import java.util.List;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SideColumnVisual extends VBox {

    public SideColumnVisual(List<Player> players, DiceAnimation diceAnimation) {
        
        this.setPrefWidth(500);
        this.setSpacing(250);
        this.setBackground(new Background(new BackgroundFill(Color.AQUA, null, null)));

        BorderPane diceWrapper = new BorderPane();
        diceWrapper.setCenter(diceAnimation);
        diceWrapper.setMaxWidth(Double.MAX_VALUE);
        diceWrapper.setPrefWidth(500);

        this.getChildren().add(diceWrapper);
        this.getChildren().add(new PlayerRowsVisual(players).getPlayerRows());

        this.setAlignment(Pos.CENTER); 
    }

    public VBox getColumn() {
        return this;
    }
    
}
