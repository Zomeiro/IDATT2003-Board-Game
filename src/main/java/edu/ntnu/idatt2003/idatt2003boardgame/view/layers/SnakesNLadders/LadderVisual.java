package edu.ntnu.idatt2003.idatt2003boardgame.view.layers.SnakesNLadders;

import java.util.ArrayList;
import java.util.stream.IntStream;

import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class LadderVisual extends Group {
    //parts of this code written by Gemini
    public LadderVisual(double length) {
        //drop shadow
        DropShadow shadow = new DropShadow();
        shadow.setRadius(4.0);
        shadow.setOffsetX(2.0);
        shadow.setOffsetY(2.0);
        shadow.setColor(Color.web("#000000", 0.5)); // Black shadow with 50% opacity

        //gradient for rounded look
        LinearGradient pillarGradient = new LinearGradient(
                0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#5D4037")),    
                new Stop(0.5, Color.web("#795548")),  
                new Stop(1, Color.web("#5D4037"))     
        );

        //pillar properties
        double pillarWidth = 10;
        double ladderWidth = 40; //x-pos right pillar
        double cornerRadius = 3;

        //left pillar
        Rectangle leftPillar = new Rectangle();
        leftPillar.setY(0);
        leftPillar.setX(0);
        leftPillar.setWidth(pillarWidth);
        leftPillar.setHeight(length);
        leftPillar.setFill(pillarGradient);
        leftPillar.setArcWidth(cornerRadius * 2); 
        leftPillar.setArcHeight(cornerRadius * 2);
        leftPillar.setEffect(shadow);

        //right pillar
        Rectangle rightPillar = new Rectangle();
        rightPillar.setY(0);
        rightPillar.setX(ladderWidth);
        rightPillar.setWidth(pillarWidth);
        rightPillar.setHeight(length);
        rightPillar.setFill(pillarGradient);
        rightPillar.setArcWidth(cornerRadius * 2);
        rightPillar.setArcHeight(cornerRadius * 2);
        rightPillar.setEffect(shadow);

        //steps
        ArrayList<Rectangle> steps = new ArrayList<>();
        int stepAmount = (int) (length / 25); 
        double stepHeight = 6;
        double stepYOffset = 20; 
        double stepSpacing = (length - stepYOffset * 2) / (stepAmount > 1 ? stepAmount -1 : 1); 
        Color stepColor = Color.web("#6D4C41"); 

        IntStream.range(0, stepAmount)
                .forEach(i -> {
                    Rectangle step = new Rectangle();
                    step.setX(pillarWidth - (cornerRadius / 2)); 
                    step.setY(stepYOffset + i * stepSpacing - (stepHeight / 2)); 
                    step.setWidth(ladderWidth - pillarWidth + cornerRadius); 
                    step.setHeight(stepHeight);
                    step.setFill(stepColor);
                    step.setArcWidth(cornerRadius); 
                    step.setArcHeight(cornerRadius);
                    step.setEffect(shadow); 

                    steps.add(step);
                });

        
        this.getChildren().addAll(steps);
        this.getChildren().addAll(rightPillar, leftPillar);
    }

    public Group getLadder() {
        return this;
    }
}