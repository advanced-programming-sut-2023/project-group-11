package view.controller;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ViewUtils {

    public static void fieldError(Label label, String error) {
        label.setText(error);
        label.setTextFill(Color.RED);
    }
}
