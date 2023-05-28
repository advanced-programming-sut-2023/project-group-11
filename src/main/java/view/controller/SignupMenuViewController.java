package view.controller;

import controller.LoginMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.ForgotPassword;
import view.MainMenu;
import view.SignupMenu;
import view.enums.messages.LoginMenuMessages;

import javax.swing.undo.UndoableEdit;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignupMenuViewController implements Initializable {
    @FXML
    private PasswordField loginPasswordField;
    @FXML
    private TextField loginUsernameField;
    @FXML
    private Label passwordError;
    @FXML
    private Label usernameError;
    @FXML
    private CheckBox stayLoggedInCheck;

    private LoginMenuMessages message;
    private static String username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void login() throws Exception {
        boolean stayLoggedIn = stayLoggedInCheck.isSelected();
        ViewUtils.clearLabels(usernameError, passwordError);
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
        ViewUtils.clearLabels(usernameError, passwordError);
        username = loginUsernameField.getText();
        message = LoginMenuController.checkForgotPassword(username);

        switch (message) {
            case USERNAME_NOT_EXIST -> ViewUtils.fieldError(usernameError, "Username doesn't exist!");
            case SUCCESS -> {
                Stage stage = new Stage();
                new ForgotPassword().start(stage);
            }
        }
    }

    protected static String getUsername() {
        return username;
    }
}
