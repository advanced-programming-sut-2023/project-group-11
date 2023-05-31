package view;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class ViewUtils {

    public static void fieldError(Label label, String error) {
        label.setText(error);
        label.setTextFill(Color.RED);
    }

    public static void clearLabels(Label... labels) {
        for (Label label : labels) {
            label.setText("");
        }
    }
    public static void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.setText("");
        }
    }


    public static boolean setEmptyError(TextField field, Label error){
        if(field.getText().isEmpty()){
            error.setText("must be filled!");
            return false;
        }
        else return error.getText().isEmpty() || error.getText().equals("Strong!");
    }

    public static void alert(Alert.AlertType TYPE, String header, String content) {
        Alert alert = new Alert(TYPE);
        alert.setTitle(TYPE.name());
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
