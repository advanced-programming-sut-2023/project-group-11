package view;

import controller.ProfileMenuController;
import controller.SignupMenuController;
import controller.Utils;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Stronghold;
import net.Client;
import net.packets.Packet02UpdateProfile;
import view.enums.messages.ProfileMenuMessages;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class ProfileMenu extends Application {
    //TODO: copy other users' avatars for current user
    private static Stage stage;
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
    private TextField newSlogan;
    @FXML
    private TextField newEmail;
    @FXML
    private TextField newNickname;
    @FXML
    private Label newEmailError;
    private ProfileMenuMessages message;
    private String currentUsername;
    private String currentNickname;
    private String currentEmail;
    private String currentSlogan;
    private String currentRecoveryQuestion;

    @FXML
    public void initialize() {
        setUserFields(Utils.getCurrentUserFields());
        slogan.setWrapText(true);
        updateDefaultLabels();
        updateNewUsernameLabel();
        updateEmailLabel();
    }

    private void setUserFields(String[] userFields) {
        currentUsername = userFields[0];
        currentEmail = userFields[1];
        currentRecoveryQuestion = userFields[2];
        currentNickname = userFields[3];
        currentSlogan = userFields[4];
    }

    private void updateDefaultLabels() {
        username.setText(currentUsername);
        email.setText(currentEmail);
        nickname.setText(currentNickname);
        if (currentSlogan != null) slogan.setText(currentSlogan);
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
                ViewUtils.alert(Alert.AlertType.INFORMATION, "Change Username Successful",
                        "You have successfully changed your username!");
                ProfileMenuController.changeUsername(newUsername.getText());
                new Packet02UpdateProfile(username.getText(), Stronghold.getCurrentUser()).writeData(Client.getClient());
                username.setText(newUsername.getText());
            }
        }
    }

    @FXML
    private void createAvatarChooser() {
        FileChooser avatarChooser = new FileChooser();

        configureFileChooser(avatarChooser);
        File selectedFile = avatarChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            ProfileMenuController.changeAvatar(selectedFile);
            ViewUtils.alert(Alert.AlertType.INFORMATION, "Change Avatar Successful",
                    "You have successfully changed your avatar!");
            new Packet02UpdateProfile(username.getText(), Stronghold.getCurrentUser()).writeData(Client.getClient());
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

    public void setTransferModes(DragEvent dragEvent) {
        dragEvent.acceptTransferModes(TransferMode.COPY);
        dragEvent.consume();
    }

    public void changeAvatar(DragEvent dragEvent) {
        Dragboard dragboard = dragEvent.getDragboard();

        if (dragboard.hasFiles() && dragboard.getFiles().size() == 1) {
            File avatarFile = dragboard.getFiles().get(0);
            if (isImage(avatarFile.getPath())) {
                ProfileMenuController.changeAvatar(avatarFile);
                new Packet02UpdateProfile(username.getText(), Stronghold.getCurrentUser()).writeData(Client.getClient());
                updateAvatar();
            }
        }
        dragEvent.consume();
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
        avatar.setImage(Utils.getCurrentUserAvatar().getImage());
    }

    public void generateRandomSlogan() {
        newSlogan.setText(SignupMenuController.generateRandomSlogan());
    }

    public void removeSlogan() {
        ProfileMenuController.removeSlogan();
        new Packet02UpdateProfile(username.getText(), Stronghold.getCurrentUser()).writeData(Client.getClient());
        currentSlogan = null;
        slogan.setText("empty!");
    }

    public void changeSlogan() {
        ViewUtils.alert(Alert.AlertType.INFORMATION, "Change Slogan",
                "You have successfully changed your slogan!");
        ProfileMenuController.changeSlogan(newSlogan.getText());
        new Packet02UpdateProfile(username.getText(), Stronghold.getCurrentUser()).writeData(Client.getClient());
        currentSlogan = newSlogan.getText();
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
                ProfileMenuController.changeEmail(newEmail.getText());
                new Packet02UpdateProfile(username.getText(), Stronghold.getCurrentUser()).writeData(Client.getClient());
            }
        }
    }

    public void changeNickname() {
        ViewUtils.alert(Alert.AlertType.INFORMATION, "Change Nickname Successful",
                "You have successfully changed your nickname!");
        nickname.setText(newNickname.getText());
        ProfileMenuController.changeNickname(newNickname.getText());
        new Packet02UpdateProfile(username.getText(), Stronghold.getCurrentUser()).writeData(Client.getClient());
    }

    public void showScoreboard() throws Exception {
        new Scoreboard().start(new Stage());
    }

    public void back() throws Exception {
        new MainMenu().start(stage);
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

    public void changePassword() throws Exception {
        new ChangePassword().start(new Stage());
    }

}
