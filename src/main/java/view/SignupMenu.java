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
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.enums.messages.LoginMenuMessages;
import view.enums.messages.SignupMenuMessages;

import java.net.URL;
import java.util.Random;

public class SignupMenu extends Application {
    @FXML
    private TextField signupUsername;
    @FXML
    private Label signupUsernameError;
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
    @FXML
    private PasswordField signupPassword;
    @FXML
    private TextField passwordShown;
    @FXML
    private TextField confirmationShown;
    @FXML
    private Label signupPasswordError;
    @FXML
    private CheckBox passwordShow;
    @FXML
    private PasswordField signupConfirmation;
    @FXML
    private Label signupConfirmationError;
    @FXML
    private TextField email;
    @FXML
    private Label emailError;
    @FXML
    private CheckBox chooseSloganBox;
    @FXML
    private Label sloganLabel;
    @FXML
    private TextField sloganTextField;
    @FXML
    private CheckBox randomSloganBox;
    @FXML
    private TextField nicknameTextField;
    @FXML
    private Label nicknameError;
    private LoginMenuMessages message;
    private SignupMenuMessages signupMessage;
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
        updateSignupLabel();
        updatePasswordLabel();
        updateConfirmationLabel();
        updateEmailLabel();
        updateNicknameLabel();
    }

    private void updateNicknameLabel() {
        nicknameTextField.textProperty().addListener((observable, oldText, newText)->{
           nicknameError.setText("");
        });
    }

    private void updateEmailLabel() {
        email.textProperty().addListener((observable, oldText, newText)->{
            if(newText.isEmpty()){
                emailError.setText("");
            }else {
                switch (SignupMenuController.checkEmail(newText)) {
                    case EMAIL_EXIST -> emailError.setText("email exists!");
                    case INVALID_EMAIL_FORMAT -> emailError.setText("invalid format!");
                    default -> emailError.setText("");
                }
            }
        });
    }

    private void updateConfirmationLabel() {
        signupConfirmation.textProperty().addListener((observable, oldText, newText)-> {
            if(newText.isEmpty() || newText.equals(signupPassword.getText()))
                signupConfirmationError.setText("");
            else
                signupConfirmationError.setText("doesn't match!");
        });
    }

    private void updatePasswordLabel() {
        signupPassword.textProperty().addListener((observable, oldText, newText)-> {
            int weakness = SignupMenuController.findHowWeakPasswordIs(newText);
            if(newText.isEmpty()){
                signupPasswordError.setText("");
            }
            else if(weakness == 0){
                signupPasswordError.setStyle("-fx-text-fill: green;");
                signupPasswordError.setText("Strong!");
            }
            else if(weakness <= 2){
                signupPasswordError.setStyle("-fx-text-fill: orange;");
                signupPasswordError.setText("Weak!");
            }else{
                signupPasswordError.setStyle("-fx-text-fill: red;");
                signupPasswordError.setText("Very weak!");
            }
        });
    }

    public void changeVisibility(){
        if(passwordShow.isSelected()){
            passwordShown.setText(signupPassword.getText());
            confirmationShown.setText(signupConfirmation.getText());
            passwordShown.setVisible(true);
            confirmationShown.setVisible(true);
            signupPassword.setVisible(false);
            signupConfirmation.setVisible(false);
            return;
        }
        signupPassword.setText(passwordShown.getText());
        signupConfirmation.setText(confirmationShown.getText());
        signupPassword.setVisible(true);
        signupConfirmation.setVisible(true);
        confirmationShown.setVisible(false);
    }

    private void updateSignupLabel() {
        signupUsername.textProperty().addListener((observable, oldText, newText)->{
            switch (SignupMenuController.checkUsername(newText)) {
                case USERNAME_EXIST -> signupUsernameError.setText("username exists");
                case INVALID_USERNAME_FORMAT -> signupUsernameError.setText("invalid format");
                default -> signupUsernameError.setText("");
            }
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
//                WritableImage captcha = Utils.generateCaptcha(new Random().nextInt(10,1000));
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

    public void generateRandomPassword() {
        signupConfirmation.setText("");
        signupPassword.setText(SignupMenuController.generateRandomPassword());
        passwordShown.setText(signupPassword.getText());
        passwordShow.setSelected(true);
        changeVisibility();
    }

    public void chooseSlogan() {
        if(chooseSloganBox.isSelected()){
            sloganLabel.setVisible(true);
            sloganTextField.setVisible(true);
            randomSloganBox.setVisible(true);
        }else{
            sloganLabel.setVisible(false);
            sloganTextField.setVisible(false);
            randomSloganBox.setVisible(false);
        }
    }

    public void randomSlogan() {
        if(randomSloganBox.isSelected()){
            sloganTextField.setText(SignupMenuController.generateRandomSlogan());
        }else{
            sloganTextField.setText("");
        }
    }

    public void signup() {
        if(ViewUtils.setEmptyError(signupUsername,signupUsernameError)&
        ViewUtils.setEmptyError(signupPassword,signupPasswordError)&
        ViewUtils.setEmptyError(signupConfirmation,signupConfirmationError)&
        ViewUtils.setEmptyError(nicknameTextField,nicknameError)&
        ViewUtils.setEmptyError(email,emailError)){

        }

    }
}
