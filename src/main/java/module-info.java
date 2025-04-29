module edu.ntnu.idatt2003.idatt2003boardgame {
  requires javafx.controls;
  requires javafx.fxml;

  opens edu.ntnu.idatt2003.idatt2003boardgame to javafx.fxml;
  exports edu.ntnu.idatt2003.idatt2003boardgame;
}