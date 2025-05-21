package edu.ntnu.idatt2003.idatt2003boardgame.view.scenes;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WinnerView {

  public Scene createScene(Player winner, Stage stage) {
    Label title = new Label("🎉 " + winner.getName() + " vant!");
    Button close = new Button("Avslutt");
    close.setOnAction(e -> stage.close());

    VBox root = new VBox(20, title, close);
    root.setAlignment(Pos.CENTER);
    return new Scene(root, 300, 200);
  }
}
