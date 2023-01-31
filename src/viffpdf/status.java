package viffpdf;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class Status {
  static void print(String text) {
    Text t = new Text(String.valueOf(text) + "\n");
    t.setFill((Paint)Color.BLACK);
    Main.logArea.getChildren().add(t);
  }
  
  static void printError(String text) {
    Text t = new Text(String.valueOf(text) + "\n");
    t.setFill((Paint)Color.RED);
    Main.logArea.getChildren().add(t);
  }
  
  static void printWarning(String text) {
    Text t = new Text(text);
    t.setFill((Paint)Color.BLUE);
    Main.logArea.getChildren().add(t);
  }
}
