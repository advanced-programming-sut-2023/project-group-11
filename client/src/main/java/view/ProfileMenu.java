package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Parsers;
import model.User;
import org.json.JSONArray;
import view.enums.Message;
import webConnection.Client;
import webConnection.Connection;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class ProfileMenu extends Application {
    //TODO: copy other users' avatars for current user
    private static Stage stage;
    private final TableView<User> tableView = new TableView<>();
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
    private Message message;
    private final String profileMenuController = "ProfileMenuController";
    private final String signupMenuController = "SignupMenuController";
    private final String utils = "Utils";
    private final Connection connection = Client.getConnection();
    private String currentUsername;
    private String currentNickname;
    private String currentEmail;
    private String currentSlogan;
    private String currentRecoveryQuestion;

    @FXML
    public void initialize() throws IOException {
        setUserFields(convertToArray((JSONArray) connection.getData(utils, "getCurrentUserFields")));
        slogan.setWrapText(true);
        updateDefaultLabels();
        updateNewUsernameLabel();
        updateEmailLabel();
    }

    private String[] convertToArray(JSONArray jsonArray) {
        ArrayList<Object> list = new ArrayList<>(jsonArray.toList());
        String[] fields = new String[list.size()];
        for (int i = 0; i < list.size(); i++) fields[i] = (String) list.get(i);
        return fields;
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

            message = connection.checkAction(profileMenuController, "checkChangeUsername", newUsername.getText());
            if (newText.isEmpty()) newUsernameError.setText("");
            else switch (message) {
                case INVALID_USERNAME -> newUsernameError.setText("Invalid format");
                case USERNAME_EXIST -> newUsernameError.setText("Username exists");
                case SUCCESS -> newUsernameError.setText("");
            }
        }));
    }

    public void changeUsername() {
        message = connection.checkAction(profileMenuController, "checkChangeUsername", newUsername.getText());

        switch (message) {
            case USERNAME_EXIST -> ViewUtils.alert(Alert.AlertType.ERROR, "Change Username Failed",
                    "Username Already exists!");
            case INVALID_USERNAME -> ViewUtils.alert(Alert.AlertType.ERROR, "Change Username Failed",
                    "Invalid username format!");
            case SUCCESS -> {
                ViewUtils.alert(Alert.AlertType.INFORMATION, "Change Username Successful",
                        "You have successfully changed your username!");
                connection.doInServer(profileMenuController, "changeUsername", newUsername.getText());
                username.setText(newUsername.getText());
            }
        }
    }

    @FXML
    private void createAvatarChooser() throws URISyntaxException {
        FileChooser avatarChooser = new FileChooser();

        configureFileChooser(avatarChooser);
        File selectedFile = avatarChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            connection.doInServer(profileMenuController, "changeAvatar", selectedFile.getPath());
            ViewUtils.alert(Alert.AlertType.INFORMATION, "Change Avatar Successful",
                    "You have successfully changed your avatar!");
            updateAvatar();
        }
    }

    private void configureFileChooser(FileChooser fileChooser) throws URISyntaxException {
        fileChooser.setInitialDirectory(new File(getClass().getResource("/IMG/avatars").toURI()));
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
                connection.doInServer(profileMenuController, "changeAvatar", avatarFile.getPath());
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
        Image image = new Image(((String) connection.getData(utils, "getCurrentUserAvatarFileName")));
        avatar.setImage(image);
    }

    public void generateRandomSlogan() {
        newSlogan.setText((String) connection.getData(signupMenuController, "generateRandomSlogan"));
    }

    public void removeSlogan() {
        connection.doInServer(profileMenuController, "removeSlogan");
        currentSlogan = null;
        slogan.setText("empty!");
    }

    public void changeSlogan() {
        ViewUtils.alert(Alert.AlertType.INFORMATION, "Change Slogan",
                "You have successfully changed your slogan!");
        connection.doInServer(profileMenuController, "changeSlogan", newSlogan.getText());
        currentSlogan = newSlogan.getText();
        slogan.setText(newSlogan.getText());
    }

    private void updateEmailLabel() {
        newEmail.textProperty().addListener((observableValue, oldText, newText) -> {
            message = connection.checkAction(profileMenuController, "checkChangeEmail", newText);

            if (newText.isEmpty()) newEmailError.setText("");
            else switch (message) {
                case INVALID_EMAIL -> newEmailError.setText("Invalid format!");
                case EMAIL_EXIST -> newEmailError.setText("Email exists!");
                case SUCCESS -> newEmailError.setText("");
            }
        });
    }

    public void changeEmail() {
        message = connection.checkAction(profileMenuController, "checkChangeEmail", newEmail.getText());

        switch (message) {
            case EMAIL_EXIST -> ViewUtils.alert(Alert.AlertType.ERROR, "Change Email Failed",
                    "Email already exist!");
            case INVALID_EMAIL -> ViewUtils.alert(Alert.AlertType.ERROR, "Change Email Failed",
                    "Invalid email format!");
            case SUCCESS -> {
                ViewUtils.alert(Alert.AlertType.INFORMATION, "Change Email Successful",
                        "You have successfully changed your email!");
                email.setText(newEmail.getText());
                connection.doInServer(profileMenuController, "changeEmail", newEmail.getText());
            }
        }
    }

    public void changeNickname() {
        ViewUtils.alert(Alert.AlertType.INFORMATION, "Change Nickname Successful",
                "You have successfully changed your nickname!");
        nickname.setText(newNickname.getText());
        connection.doInServer(profileMenuController, "changeNickname", newNickname.getText());
    }

    public void showScoreboard() throws Exception {
        Client.getConnection().doInServer("Utils", "setInScoreboard", true);
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

    public void showFriends() {
        ArrayList<User> friends = Parsers.parseUserArrayList(
                connection.receiveJsonData(profileMenuController, "getCurrentUserFriends"));
        showTableView(new VBox(tableView), friends);
    }

    public void showRequests() {
        ArrayList<User> friends = Parsers.parseUserArrayList(
                connection.receiveJsonData(profileMenuController, "getCurrentUserFriendsRequest"));
        Button accept = new Button("accept");
        Button reject = new Button("reject");
        HBox hBox = new HBox(10, accept, reject);
        VBox vBox = new VBox(20, tableView, hBox);
        hBox.setAlignment(Pos.CENTER);
        ObservableList<User> selected = tableView.getSelectionModel().getSelectedItems();
        accept.setOnMouseClicked(mouseEvent -> {
            accept(new ArrayList<>(selected));
            tableView.getItems().removeAll(selected);
        });
        reject.setOnMouseClicked(mouseEvent -> tableView.getItems().removeAll(selected));
        showTableView(vBox, friends);
    }

    private void showTableView(VBox vBox, ArrayList<User> friends) {
        tableView.getItems().clear();
        tableView.getColumns().clear();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setItems(FXCollections.observableList(friends));
        ViewUtils.addColumns(tableView);

        vBox.setPadding(new Insets(10, 0, 0, 0));
        Stage stage1 = new Stage();
        stage1.setScene(new Scene(vBox));
        stage1.show();
    }

    private void accept(ArrayList<User> users) {
        for (User user : users) {
            message = connection.checkAction(profileMenuController, "addFriend", user.getUsername());
            handleFriendError(message);
        }
    }

    private static void handleFriendError(Message message) {
        switch (message) {
            case ALREADY_REQUESTED -> ViewUtils.alert(Alert.AlertType.ERROR, "Add Friend Error",
                    "You have already requested friendship to this user!");
            case ALREADY_FRIEND -> ViewUtils.alert(Alert.AlertType.ERROR, "Add Friend Error",
                    "You are already friend with this user");
            case YOU_REACHED_FRIEND_LIMIT -> ViewUtils.alert(Alert.AlertType.ERROR, "Add Friend Error",
                    "You have 100 friends. You can't add more!");
            case HE_REACHED_FRIEND_LIMIT -> ViewUtils.alert(Alert.AlertType.ERROR, "Add Friend Error",
                    "This user has 100 friends. He have more!");
            case SELF_FRIENDSHIP -> ViewUtils.alert(Alert.AlertType.ERROR, "Add Friend Error",
                    "You can't make friendship with yourself!");
            case SUCCESS -> ViewUtils.alert(Alert.AlertType.INFORMATION, "Add Friend Successful",
                    "You are now friend with this user!");
        }
    }
}
