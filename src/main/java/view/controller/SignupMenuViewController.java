package view.controller;

import controller.LoginMenuController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.MainMenu;
import view.SignupMenu;
import view.enums.messages.LoginMenuMessages;

import javax.swing.undo.UndoableEdit;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignupMenuViewController implements Initializable {
    public PasswordField loginPasswordField;
    public TextField loginUsernameField;
    public Label passwordError;
    public Label usernameError;
    public CheckBox stayLoggedInCheck;

    private LoginMenuMessages message;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void login() throws Exception {
        boolean stayLoggedIn = stayLoggedInCheck.isSelected();

        message = LoginMenuController.checkLogin(loginUsernameField.getText(), loginPasswordField.getText(), stayLoggedIn);

        switch (message) {
            case EMPTY_USERNAME_FIELD -> ViewUtils.fieldError(usernameError, "Required fields must be filled in!");
            case EMPTY_PASSWORD_FIELD -> ViewUtils.fieldError(passwordError, "Required fields must be filled in!");
            case USERNAME_NOT_EXIST -> ViewUtils.fieldError(usernameError, "Username doesn't exist!");
            case INCORRECT_PASSWORD -> ViewUtils.fieldError(passwordError, "Password is incorrect!");
            case LOCKED_ACCOUNT ->
                    ViewUtils.fieldError(usernameError,
                            "Your account is locked for " +
                                  LoginMenuController.getLeftLockedTime(loginUsernameField.getText()) / 1000.0 +
                                  " seconds more!");
            case SUCCESS -> {
                LoginMenuController.loginUser(loginUsernameField.getText());
                new MainMenu().start(SignupMenu.stage);
            }
        }
    }

    public void forgotPassword() throws Exception {
        Stage stage = new Stage();
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(SignupMenu.class.getResource("/FXML/ForgotPassword.fxml").toExternalForm()));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);

        stage.show();
    }
}
