package edu.ntnu.idatt2003.idatt2003boardgame.view.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * A JavaFX {@link GridPane} component that visually represents a single die face
 * and animates a dice roll. It uses a 3x3 grid of circles (pips) to display
 * standard die faces from 1 to 6.
 * 
 * @author Hector Mendana Morales
 */
public class DiceAnimation extends GridPane {

    private List<Circle> points;
    private final Random random = new Random();

    /**
     * Constructs a new DiceAnimation component.
     * Initializes a 3x3 grid of StackPanes, each containing a background
     * rectangle and a circle (pip) to represent a part of the die face.
     * The initial display shows all pips as black (inactive).
     */
    public DiceAnimation() {
        this.points = new ArrayList<>(9);

        this.setHgap(0);
        this.setVgap(0);
        this.setPadding(Insets.EMPTY);
        this.setAlignment(Pos.CENTER);
        this.setPrefWidth(225);
        this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        this.setStyle("-fx-background-color: white;");

        IntStream.rangeClosed(0, 8)
                .forEach(i -> {
                    StackPane die = new StackPane();
                    Rectangle background = new Rectangle(75, 75, Color.BLACK);
                    background.setStroke(null);

                    Circle circle = new Circle(20, Color.BLACK);
                    points.add(circle);

                    die.getChildren().addAll(background, circle);
                    this.add(die, i % 3, i / 3);
                });

    }

    /**
     * Displays a dice roll animation.
     * It rapidly shows several random "fake" rolls before settling on the final roll value.
     *
     * @param finalRoll The final value (1-6) that the die should display after the animation.
     */
    public void displayRoll(int finalRoll) {
    Timeline timeline = new Timeline();

    IntStream.rangeClosed(1,10).forEach(i -> {
        int fakeRoll = random.nextInt(6) + 1;

        KeyFrame keyFrame = new KeyFrame(Duration.millis(i * 50), e -> {
            displayFace(fakeRoll);
        });

        timeline.getKeyFrames().add(keyFrame);

    });

    KeyFrame finalKeyFrame = new KeyFrame(Duration.millis(500), e -> {
        displayFace(finalRoll);
    });

    timeline.getKeyFrames().add(finalKeyFrame);
    timeline.play();
}

    /**
     * Updates the visual display of the die to show the specified roll value.
     * It configures the visibility (color) of the pips to represent the die face.
     *
     * @param roll The die face value to display (1-6).
     * @throws IllegalArgumentException if the roll value is not between 1 and 6.
     */
    public void displayFace(int roll) {
        points.forEach(c -> c.setFill(Color.BLACK));

        switch (roll) {
            case 1 ->
                setPips(4);
            case 2 ->
                setPips(0, 8);
            case 3 ->
                setPips(0, 4, 8);
            case 4 ->
                setPips(0, 2, 6, 8);
            case 5 ->
                setPips(0, 2, 4, 6, 8);
            case 6 ->
                setPips(0, 2, 3, 5, 6, 8);
            default ->
                throw new IllegalArgumentException("Roll must be between 1 and 6");
        }
    }

    private void setPips(int... indices) {
        for (int i : indices) {
            points.get(i).setFill(Color.WHITE);
        }
    }

}