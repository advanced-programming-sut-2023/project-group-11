package view;

import controller.ProfileMenuController;
import controller.SignupMenuController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Stronghold;
import model.User;
import view.enums.messages.ProfileMenuMessages;

import java.io.File;
import java.net.URISyntaxException;
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
    private TextField newEmail;
    @FXML
    private TextField newNickname;
    @FXML
    private Label newEmailError;
    private User currentUser;
    private ProfileMenuMessages message;

    @FXML
    public void initialize() {
        currentUser = Stronghold.getCurrentUser();
        slogan.setWrapText(true);
        updateDefaultLabels();
        updateNewUsernameLabel();
        updatePasswordLabel();
        updateEmailLabel();
    }

    private void updateDefaultLabels() {
        username.setText(currentUser.getUsername());
        nickname.setText(currentUser.getNickname());
        email.setText(currentUser.getEmail());
        if (currentUser.getSlogan() != null) slogan.setText(currentUser.getSlogan());
        else slogan.setText("empty!");
        updateAvatar();
    }

    public void updateNewUsernameLabel() {
        newUsername.textProperty().addListener(((observableValue, oldText, newText) -> {

            message = ProfileMenuController.checkChangeUsername(newText);
            if (newText.isEmpty()) newUsernameError.setText("");
            else switch (message) {
                case INVALID_USERNAME -> newUsernameError.setText("Invalid format");
                case USERNAME_EXIST -> newUsernameError.setText("Username exists");
                case SUCCESS -> newUsernameError.setText("");
            }
        }));
    }

    public void changeUsername() {
        message = ProfileMenuController.checkChangeUsername(newUsername.getText());

        switch (message) {
            case USERNAME_EXIST -> ViewUtils.alert(Alert.AlertType.ERROR, "Change Username Failed",
                    "Username Already exists!");
            case INVALID_USERNAME -> ViewUtils.alert(Alert.AlertType.ERROR, "Change Username Failed",
                    "Invalid username format!");
            case SUCCESS -> {
                ProfileMenuController.changeUsername(currentUser, newUsername.getText());
                username.setText(newUsername.getText());
                ViewUtils.alert(Alert.AlertType.INFORMATION, "Change Username Successful",
                        "You have successfully changed your username!");
            }
        }
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

    @FXML
    private void createAvatarChooser() {
        FileChooser avatarChooser = new FileChooser();

        configureFileChooser(avatarChooser);
        File selectedFile = avatarChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            ProfileMenuController.changeAvatar(currentUser, selectedFile);
            ViewUtils.alert(Alert.AlertType.INFORMATION, "Change Avatar Successful",
                    "You have successfully changed your avatar!");
            updateAvatar();
        }
    }

    private void configureFileChooser(FileChooser fileChooser) {
        try {
            fileChooser.setInitialDirectory(new File(getClass().getResource("/IMG/avatars").toURI()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "images", "*.jpg", "*.jpeg", "*.png"));
    }

    public void changeAvatar(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.COPY);
            File avatarFile = dragEvent.getDragboard().getFiles().get(0);
            if (isImage(avatarFile.getPath())) {
                ProfileMenuController.changeAvatar(currentUser, avatarFile);
                updateAvatar();
            }
        }
        dragEvent.consume();
        //TODO: needs debug (on drag dropped or over or ...)
    }

    private boolean isImage(String path) {
        return isValidExtension(getExtension(path));
    }

    private String getExtension(String path) {
        int dotIndex = path.lastIndexOf(".");
        return path.substring(dotIndex + 1);
    }

    private boolean isValidExtension(String extension) {
        return extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg");
    }

    private void updateAvatar() {
        avatar.setImage(currentUser.getAvatar());
    }

    public void generateRandomSlogan() {
        newSlogan.setText(SignupMenuController.generateRandomSlogan());
    }

    public void removeSlogan() {
        currentUser.setSlogan(null);
        slogan.setText("empty!");
    }

    public void changeSlogan() {
        ViewUtils.alert(Alert.AlertType.INFORMATION, "Change Slogan",
                "You have successfully changed your slogan!");
        currentUser.setSlogan(newSlogan.getText());
        slogan.setText(newSlogan.getText());
    }

    private void updateEmailLabel() {
        newEmail.textProperty().addListener((observableValue, oldText, newText) -> {
            message = ProfileMenuController.checkChangeEmail(newText);

            if (newText.isEmpty()) newEmailError.setText("");
            else switch (message) {
                case INVALID_EMAIL -> newEmailError.setText("Invalid format!");
                case EMAIL_EXIST -> newEmailError.setText("Email exists!");
                case SUCCESS -> newEmailError.setText("");
            }
        });
    }

    public void changeEmail() {
        message = ProfileMenuController.checkChangeEmail(newEmail.getText());

        switch (message) {
            case EMAIL_EXIST -> ViewUtils.alert(Alert.AlertType.ERROR, "Change Email Failed",
                    "Email already exist!");
            case INVALID_EMAIL -> ViewUtils.alert(Alert.AlertType.ERROR, "Change Email Failed",
                    "Invalid email format!");
            case SUCCESS -> {
                ViewUtils.alert(Alert.AlertType.INFORMATION, "Change Email Successful",
                        "You have successfully changed your email!");
                email.setText(newEmail.getText());
            }
        }
    }

    public void changeNickname() {
        ViewUtils.alert(Alert.AlertType.INFORMATION, "Change Nickname Successful",
                "You have successfully changed your nickname!");
        nickname.setText(newNickname.getText());
    }

    public void showScoreboard(ActionEvent actionEvent) {

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
}
