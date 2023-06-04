package view;

import controller.SignupMenuController;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Random;

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


    public static boolean setEmptyError(TextField field, Label error) {
        if (field.getText().isEmpty()) {
            error.setText("must be filled!");
            return false;
        } else return error.getText().isEmpty() || error.getText().equals("Strong!");
    }

    public static void alert(Alert.AlertType TYPE, String header, String content) {
        Alert alert = new Alert(TYPE);
        alert.setTitle(TYPE.name());
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static String reloadCaptcha(ImageView captchaImageView) throws URISyntaxException {
        File[] files = new File(SignupCompletion.class.getResource("/IMG/captcha").toURI()).listFiles();
        File captcha = files[new Random().nextInt(files.length)];
        String captchaNumber = captcha.getName().substring(0, 4);
        captchaImageView.setImage(new Image(captcha.getPath()));
        return captchaNumber;
    }

    static void livePasswordError(PasswordField newPassword, Label newPasswordError) {
        newPassword.textProperty().addListener((observable, oldText, newText) -> {
            int weakness = SignupMenuController.findHowWeakPasswordIs(newText);
            if (newText.isEmpty()) {
                newPasswordError.setText("");
            } else if (weakness == 0) {
                newPasswordError.setStyle("-fx-text-fill: green;");
                newPasswordError.setText("Strong!");
            } else if (weakness <= 2) {
                newPasswordError.setStyle("-fx-text-fill: orange;");
                newPasswordError.setText("Weak!");
            } else {
                newPasswordError.setStyle("-fx-text-fill: red;");
                newPasswordError.setText("Very weak!");
            }
        });
    }
}
