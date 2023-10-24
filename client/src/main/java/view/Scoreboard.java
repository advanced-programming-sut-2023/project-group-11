package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Parsers;
import model.User;
import view.enums.Message;
import webConnection.Client;
import webConnection.Connection;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Scoreboard extends Application {
    //TODO: add lazy loading to scoreboard
    private static Stage stage;
    private static Scoreboard instance;
    private Connection connection = Client.getConnection();
    @FXML
    private Button addFriendButton;
    @FXML
    private TableView scoreboard;
    @FXML
    private TextField search;

    public Scoreboard() {
        super();
        instance = this;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scoreboard.stage = stage;
        BorderPane borderPane = FXMLLoader.load(
                new URL(SignupMenu.class.getResource("/FXML/Scoreboard.fxml").toExternalForm()));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> Client.getConnection().doInServer("Utils", "setInScoreboard", false));

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    public void initialize() throws IOException {
        scoreboard.setItems(ViewUtils.getUsersObservable());
        scoreboard.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        scoreboard.getSelectionModel().selectedItemProperty().addListener(observable -> addFriendButton.setVisible(true));
        ViewUtils.addColumns(scoreboard);
        search.textProperty().addListener((observableValue, old, newText) -> find(scoreboard, newText));
    }

    public void back() throws Exception {
        Client.getConnection().doInServer("Utils", "setInScoreboard", false);
        stage.close();
    }

    public void refresh() throws IOException {
        scoreboard.getItems().clear();
        scoreboard.setItems(ViewUtils.getUsersObservable());
    }

    public static void updateScoreboard() throws IOException {
        instance.refresh();
    }

    public void addFriendRequest() {
        ArrayList<User> users = new ArrayList<>(scoreboard.getSelectionModel().getSelectedItems());
        Message message;
        for (User user : users) {
            message = Client.getConnection().checkAction(
                    "ProfileMenuController", "addFriendRequest", user.getUsername());
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
                    "Request sent!");
        }
    }

    private void find(TableView tableView, String newText) {
        ArrayList<User> friends = Parsers.parseUserArrayList(
                connection.receiveJsonData("ProfileMenuController", "findUser", newText));

        tableView.setItems(FXCollections.observableList(friends));
    }
}
