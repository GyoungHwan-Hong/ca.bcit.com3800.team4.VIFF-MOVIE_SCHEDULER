package viffpdf;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class NumField extends TextField {
  public NumField() {
    addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
          public void handle(KeyEvent t) {
            char[] ar = t.getCharacter().toCharArray();
            char ch = ar[(t.getCharacter().toCharArray()).length - 1];
            if (ch < '0' || ch > '9')
              t.consume(); 
          }
        });
  }
}
