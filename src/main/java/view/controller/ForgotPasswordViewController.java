package view.controller;

import controller.LoginMenuController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import view.ForgotPassword;
import view.enums.messages.LoginMenuMessages;

import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPasswordViewController implements Initializable {
    @FXML
    private Label recoveryQuestion;
    @FXML
    private TextField recoveryAnswer;
    @FXML
    private Label recoveryAnswerError;

    private LoginMenuMessages message;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recoveryQuestion.setText(recoveryQuestion.getText() +
                LoginMenuController.showRecoveryQuestion(SignupMenuViewController.getUsername()));
    }

    public void checkForgotPasswordAction(MouseEvent mouseEvent) {
        Button submitButton = (Button) mouseEvent.getSource();
        if (submitButton.getText().equals("Check")) {
            message = LoginMenuController.checkRecoveryAnswer(SignupMenuViewController.getUsername(), recoveryAnswer.getText());

            switch (message) {
                case EMPTY_RECOVERY_ANSWER_FIELD -> ViewUtils.fieldError(recoveryAnswerError, "Required fields must be filled in!");
                case WRONG_RECOVERY_ANSWER -> ViewUtils.fieldError(recoveryAnswerError, "Wrong recovery answer!");
                case SUCCESS -> enterNewPassword(submitButton);
            }
        } else {
            message = LoginMenuController.checkNewPassword(SignupMenuViewController.getUsername(), recoveryAnswer.getText());

            switch (message) {
                case EMPTY_PASSWORD_FIELD ->
                    ViewUtils.fieldError(recoveryAnswerError, "Required fields must be filled in!");
                case WEAK_PASSWORD ->
                    ViewUtils.fieldError(recoveryAnswerError, "Weak password!");
                case SUCCESS -> {
                    ViewUtils.alert(Alert.AlertType.INFORMATION, "Password Changed: ",
                            "You have successfully changed your password!");
                    ForgotPassword.getStage().close();
                }
            }
        }
    }

    private void enterNewPassword(Button submitButton) {
        recoveryQuestion.setText("Enter your new password!");
        recoveryAnswer.setPromptText("New password");
        recoveryAnswer.setText("");
        recoveryAnswerError.setText("");
        submitButton.setText("Confirm");
    }
}
