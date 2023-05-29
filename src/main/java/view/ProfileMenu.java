package view;

import controller.ProfileMenuController;
import controller.SignupMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Stronghold;
import model.User;
import view.enums.messages.ProfileMenuMessages;

import java.net.URL;

public class ProfileMenu extends Application {
    public static Stage stage;
    @FXML
    private Label username;
    @FXML
    private Label nickname;
    @FXML
    private Label email;
    @FXML
    private Label slogan;
    @FXML
    private ImageView avatar;
    @FXML
    private Label newUsernameError;
    @FXML
    private TextField newUsername;
    @FXML
    private PasswordField oldPassword;
    @FXML
    private Label newPasswordError;
    @FXML
    private PasswordField newPassword;
    @FXML
    private TextField newSlogan;
    @FXML
    private TextField newNickname;
    @FXML
    private Label newEmailError;
    @FXML
    private TextField newEmail;
    private User currentUser;
    private ProfileMenuMessages message;

    @FXML
    public void initialize() {
        currentUser = Stronghold.getCurrentUser();
        updateDefaultLabels();
        updateNewUsernameLabel();
        updatePasswordLabel();
    }

    private void updateDefaultLabels() {
        username.setText("Username: " + currentUser.getUsername());
        nickname.setText("Nickname: " + currentUser.getNickname());
        email.setText("Email: " + currentUser.getEmail());
        if (currentUser.getSlogan() != null) slogan.setText("Slogan : " + currentUser.getSlogan());
        else slogan.setText("Slogan: " + "empty!");
//        avatar.setImage(new Image(getClass().getResource(currentUser.getAvatarPath()).toExternalForm()));
    }

    @Override
    public void start(Stage stage) throws Exception {
        ProfileMenu.stage = stage;
        BorderPane borderPane = FXMLLoader.load(
                new URL(SignupMenu.class.getResource("/FXML/ProfileMenu.fxml").toExternalForm()));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);

        stage.show();
    }

    public void updateNewUsernameLabel() {
        newUsername.textProperty().addListener(((observableValue, oldText, newText) -> {
            message = ProfileMenuController.checkChangeUsername(newText);
            switch (message) {
                case INVALID_USERNAME -> newUsernameError.setText("Invalid format");
                case USERNAME_EXIST -> newUsernameError.setText("Username exists");
            }
        }));
    }

    private void updatePasswordLabel() {
        newPassword.textProperty().addListener((observable, oldText, newText) -> {//TODO: duplicate
            int weakness = SignupMenuController.findHowWeakPasswordIs(newText);
            if (newText.isEmpty()) newPasswordError.setText("");
            else if (weakness == 0) {
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

    public void changeUsername() {
        ProfileMenuController.changeUsername(currentUser, newUsername.getText());
        username.setText("Username: " + newUsername.getText());
        ViewUtils.alert(Alert.AlertType.INFORMATION,"Change Username",
                "You have successfully changed your password");
    }
}
