package edu.ntnu.idatt2003.idatt2003boardgame.controller;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import edu.ntnu.idatt2003.idatt2003boardgame.model.observer.GameModelObserver;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.BoardView;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.ButtonView;
import edu.ntnu.idatt2003.idatt2003boardgame.view.elements.DiceAnimation;
import java.util.function.Consumer;
import javafx.scene.layout.HBox;


public class GameViewController implements GameModelObserver {
    private final DiceAnimation diceVisual = new DiceAnimation();
    private final ButtonView diceButton;
    private final BoardView boardView;
    private final Consumer<Player> onGameWon;


    public GameViewController(Runnable onDiceRoll, BoardView boardView, Consumer<Player> onGameWon) {
        this.boardView = boardView;
        this.diceButton = new ButtonView(onDiceRoll);
        this.onGameWon = onGameWon;

    }

    public HBox getDiceButton() {
        return diceButton;
    }

    public DiceAnimation getDiceAnimation() {
        return diceVisual;
    }

    @Override
    public void onDiceRolled(int value) {
        diceVisual.displayRoll(value);
    }

    @Override
    public void onPlayerMoved(Player player, int newPosition) {
        boardView.updateEntireBoard();
    }

    @Override
    public void onTurnChanged(Player newPlayer) {
        //add something here to highlight current player if time
    }

    @Override
    public void onGameWon(Player winner) {
        System.out.println("Winner: " + winner.getName());
        onGameWon.accept(winner); //tip from chatGPT
    }
}
