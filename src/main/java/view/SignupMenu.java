package view;

import controller.EntryMenuController;
import controller.LoginMenuController;
import controller.Utils;
import controller.SignupMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.enums.messages.LoginMenuMessages;

import java.net.URL;

public class SignupMenu extends Application {
    @FXML
    private TextField passwordShown;
    @FXML
    private TextField signupUsername;
    @FXML
    private Label signupUsernameLabel;
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

    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        SignupMenu.stage = stage;
        BorderPane borderPane = FXMLLoader.load(
                new URL(SignupMenu.class.getResource("/FXML/SignupMenu.fxml").toExternalForm()));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);

        stage.show();

        EntryMenuController.fillAllFieldsWithPreviousData();
        stage.setOnCloseRequest(windowEvent -> Utils.endStronghold());
        if (EntryMenuController.getStayLoggedIn() != null) new MainMenu().start(stage);
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @FXML
    public void initialize(){
        signupUsername.textProperty().addListener((observable, oldText, newText)->{
            signupUsernameLabel.setText(SignupMenuController.checkUsername(newText).getMessage());
        });
    }

    public static Stage getStage() {
        return stage;
    }

    protected static String getUsername() {
        return username;
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
}
