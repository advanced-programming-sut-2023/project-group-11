package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.enums.Message;
import webConnection.Client;

import java.io.IOException;
import java.net.URL;

public class ForgotPassword extends Application {
    @FXML
    private Label recoveryQuestionLabel;
    @FXML
    private TextField recoveryAnswer;
    @FXML
    private Label recoveryAnswerError;

    private static Stage stage;
    private Message message;

    @Override
    public void start(Stage stage) throws Exception {
        ForgotPassword.stage = stage;
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(ForgotPassword.class.getResource("/FXML/ForgotPassword.fxml").toExternalForm()));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    public void initialize() throws IOException {
        String recoveryQuestion = (String) Client.getConnection().getData("LoginMenuController",
                "showRecoveryQuestion", SignupMenu.getUsername()).get("value");
        recoveryQuestionLabel.setText(recoveryQuestionLabel.getText() + recoveryQuestion);
    }

    public static Stage getStage() {
        return stage;
    }
    public void checkForgotPasswordAction(MouseEvent mouseEvent) throws IOException {
        Button submitButton = (Button) mouseEvent.getSource();
        if (submitButton.getText().equals("Check")) {
            message = Client.getConnection().checkAction("LoginMenuController", "checkRecoveryAnswer",
                    SignupMenu.getUsername(), recoveryAnswer.getText());

            switch (message) {
                case EMPTY_RECOVERY_ANSWER_FIELD -> ViewUtils.fieldError(recoveryAnswerError, "Required fields must be filled in!");
                case WRONG_RECOVERY_ANSWER -> ViewUtils.fieldError(recoveryAnswerError, "Wrong recovery answer!");
                case SUCCESS -> enterNewPassword(submitButton);
            }
        } else {
            message = Client.getConnection().checkAction("LoginMenuController", "checkNewPassword",
                    SignupMenu.getUsername(), recoveryAnswer.getText());

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
        recoveryQuestionLabel.setText("Enter your new password!");
        recoveryAnswer.setPromptText("New password");
        recoveryAnswer.setText("");
        recoveryAnswerError.setText("");
        submitButton.setText("Confirm");
    }

}
