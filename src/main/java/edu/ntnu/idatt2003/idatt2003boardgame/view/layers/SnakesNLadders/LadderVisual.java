package edu.ntnu.idatt2003.idatt2003boardgame.view.layers.SnakesNLadders;

import java.util.ArrayList;
import java.util.stream.IntStream;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LadderVisual extends Group {
    
    public LadderVisual(double length) {

        Rectangle leftPillar = new Rectangle();
        leftPillar.setY(0);
        leftPillar.setX(0);
        leftPillar.setWidth(10);
        leftPillar.setHeight(length);
        leftPillar.setFill(Color.RED);

        Rectangle rightPillar = new Rectangle();
        rightPillar.setY(0);
        rightPillar.setX(40);
        rightPillar.setWidth(10);
        rightPillar.setHeight(length);
        rightPillar.setFill(Color.GREEN);


        ArrayList<Rectangle> steps = new ArrayList<>();

        int stepAmount = (int) length/25;

        IntStream.rangeClosed(0, stepAmount - 2)
            .forEach(i -> {
                Rectangle step = new Rectangle();
                step.setX(10);
                step.setY(25 + i*25);
                step.setWidth(30);
                step.setHeight(5);
                step.setFill(Color.RED);
                
                steps.add(step);

            });
        
        this.getChildren().addAll(steps);
        this.getChildren().addAll(rightPillar, leftPillar);

    }



    public Group getLadder() { return this; }
    
    
}
