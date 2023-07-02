package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Parsers;
import model.User;
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

    public void addFriend() {
        ArrayList<User> users = new ArrayList<>(scoreboard.getSelectionModel().getSelectedItems());
        users.forEach(user -> Client.getConnection().doInServer(
                "ProfileMenuController", "addFriendRequest", user.getUsername()));
    }

    private void find(TableView tableView, String newText) {
        ArrayList<User> friends = Parsers.parseUserArrayList(
                connection.receiveJsonData("ProfileMenuController", "findUser", newText));

        tableView.setItems(FXCollections.observableList(friends));
    }
}
