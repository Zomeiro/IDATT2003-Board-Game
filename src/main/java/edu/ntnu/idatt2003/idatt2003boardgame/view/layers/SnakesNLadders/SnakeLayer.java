package edu.ntnu.idatt2003.idatt2003boardgame.view.layers.SnakesNLadders;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

//Most of this code is written by Gemini
/**
 * A JavaFX Group that represents the visual appearance of a single snake.
 * It constructs the snake using a series of overlapping circles to create a
 * segmented, wavy body, along with a distinct head and eyes. The snake is
 * drawn with specified colors to give it a "cute" or friendly appearance.
 * 
 * @author Hector Mendana Morales (original snake code)
 * @author Bjørn Adam Vangen (visual enhancements)
 * Most of the convoluted code was written by Gemini to save time.
 */
public class SnakeLayer extends Group {

    /**
     * Constructs a visual representation of a snake with a specified length.
     * The snake's body is made of several circular segments that create a
     * wiggling effect. The head is a larger circle with two eyes, positioned
     * at the end of the body.
     *
     * @param length The overall length of the snake, which influences the number
     * of body segments and the position of the head.
     */
    public SnakeLayer(double length) {

        Color lightGreen = Color.web("#8BC34A");
        Color darkGreen = Color.web("#4CAF50");
        Color eyeColor = Color.BLACK;

        //snake properties
        double headRadius = 15;
        double bodyRadius = 11;
        double snakeWidthCenter = 25; // Center X
        double wiggleAmount = 6;
        double bodyOverlap = 8;


        //calculate body segments amount
        double bodyDrawLength = length - headRadius;
        int segments = (int) (bodyDrawLength / (bodyRadius * 2 - bodyOverlap));
        if (segments < 1) segments = 1;

        //draw body segments
        for (int i = 0; i < segments; i++) {
            double y = i * (bodyDrawLength / segments);
            double x = snakeWidthCenter + Math.sin(y / 25.0) * wiggleAmount;

            Circle bodyPart = new Circle(x, y, bodyRadius);
            bodyPart.setFill((i % 2 == 0) ? lightGreen : darkGreen);

            this.getChildren().add(bodyPart);
        }

        Circle head = new Circle();
        head.setCenterX(snakeWidthCenter);
        head.setCenterY(length - headRadius);
        head.setRadius(headRadius);
        head.setFill(lightGreen);
        head.setStroke(darkGreen);
        head.setStrokeWidth(2);


        double eyeOffsetX = 6;
        double eyeOffsetY = 4;
        double eyeRadius = 3;

        Circle leftEye = new Circle(head.getCenterX() - eyeOffsetX, head.getCenterY() - eyeOffsetY, eyeRadius, eyeColor);
        Circle rightEye = new Circle(head.getCenterX() + eyeOffsetX, head.getCenterY() - eyeOffsetY, eyeRadius, eyeColor);

        this.getChildren().addAll(head, leftEye, rightEye);
    }

    /**
     * Returns this SnakeLayer group.
     * @return This Group object.
     */
    public Group getSnake() {
        return this;
    }
}