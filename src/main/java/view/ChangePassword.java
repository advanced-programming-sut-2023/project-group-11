package view;

import controller.ProfileMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.enums.messages.ProfileMenuMessages;

import java.net.URISyntaxException;
import java.net.URL;

public class ChangePassword extends Application {
    @FXML
    private ImageView captchaImageView;
    @FXML
    private PasswordField oldPassword;
    @FXML
    private Label newPasswordError;
    @FXML
    private PasswordField newPassword;
    @FXML
    private Label captchaError;
    @FXML
    private TextField captchaField;
    private static String captchaNumber;

    private static Stage stage;

    @FXML
    public void initialize() throws URISyntaxException {
        updatePasswordLabel();
        reloadCaptcha();
    }

    public void checkChangePassword() throws URISyntaxException {
        ProfileMenuMessages message;
        if (checkCaptcha()) {
            message = ProfileMenuController.checkChangePassword(oldPassword.getText(), newPassword.getText());

            switch (message) {
                case INCORRECT_PASSWORD -> ViewUtils.alert(Alert.AlertType.ERROR, "Change Password Failed",
                        "Incorrect old password!");
                case SAME_PASSWORD -> ViewUtils.alert(Alert.AlertType.ERROR, "Change Password Failed",
                        "Old password and new password are same");
                case SUCCESS -> {
                    ViewUtils.alert(Alert.AlertType.INFORMATION, "Change Password Successful",
                            "You have successfully changed your password");
                    stage.close();
                }
            }
            reloadCaptcha();
        }
    }

    private boolean checkCaptcha() throws URISyntaxException {
        if (!oldPassword.getText().isEmpty() && !newPassword.getText().isEmpty()) {
            if (!captchaField.getText().equals(captchaNumber)) {
                captchaError.setText("wrong captcha");
                reloadCaptcha();
                return false;
            }
            return true;
        } else newPasswordError.setText("must be filled!");
        return false;
    }

    private void updatePasswordLabel() {
        ViewUtils.livePasswordError(newPassword, newPasswordError);
    }

    public void reloadCaptcha() throws URISyntaxException {
        captchaNumber = ViewUtils.reloadCaptcha(captchaImageView);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(getClass().getResource("/FXML/ChangePassword.fxml").toExternalForm()));

        ChangePassword.stage = stage;
        stage.setScene(new Scene(anchorPane));
        stage.show();
    }
}
