package edu.ntnu.idatt2003.idatt2003boardgame.view.layers.SnakesNLadders;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class SnakeVisual extends Group {
    
    public SnakeVisual(double length) {

        Circle skull = new Circle();
        skull.setCenterX(25);
        skull.setCenterY(length - 5);
        skull.setRadius(15);
        skull.setFill(Color.ORANGE);

        Rectangle body = new Rectangle();
        body.setY(0);
        body.setX(15);
        body.setWidth(20);
        body.setHeight(length - 15);
        body.setFill(Color.ORANGE);

        Rectangle toungue = new Rectangle();
        toungue.setY(length + 5);
        toungue.setX(22.5);
        toungue.setWidth(5);
        toungue.setHeight(10);
        toungue.setFill(Color.RED); 
        
        this.getChildren().addAll(toungue, skull, body);

    }


    public Group getLadder() { return this; }
    
    
}
