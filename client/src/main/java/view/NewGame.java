package view;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;
import model.map.Map;
import webConnection.Client;
import webConnection.Connection;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class NewGame extends Application {
    private static Stage stage;
    @FXML
    private TableView<User> users;
    @FXML
    private ChoiceBox<Map> mapName;
    private final Connection connection = Client.getConnection();
    private final String utils = "Utils";

    @Override
    public void start(Stage stage) throws Exception {
        NewGame.stage = stage;
        BorderPane borderPane = FXMLLoader.load(
                new URL(SignupMenu.class.getResource("/FXML/NewGame.fxml").toExternalForm()));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    public void initialize() throws IOException {
        initializeMapNames();
        initializeUsers();
    }

    private void initializeUsers() throws IOException {
//        users.setItems((ObservableList<User>) connection.getData("MainMenuController",
//                "removeCurrentUserFromList", (connection.getData(utils, "getUsersObservable"))));//TODO: check this later
//        users.setItems(MainMenuController.removeCurrentUserFromList(Utils.getUsersObservable()));
        addColumns();
        users.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void initializeMapNames() throws IOException {
        connection.doInServer("MapEditMenuController", "setMapsOnChoiceBox", mapName);
    }

    private void addColumns() throws IOException {
        connection.doInServer(utils, "columnMaker", users, "Avatar", "avatar");
        connection.doInServer(utils, "columnMaker", users, "Rank", "rank");
        connection.doInServer(utils, "columnMaker", users, "Username", "username");
        connection.doInServer(utils, "columnMaker", users, "Score", "score");
    }

    public void startGame() throws Exception {
        if (users.getSelectionModel().getSelectedItems().size() >= 1) {
            connection.doInServer("MainMenuController", "startGame",
                    new ArrayList<>(users.getSelectionModel().getSelectedItems()), mapName.getValue().getName());
            new GameMenu().start(SignupMenu.getStage());
            stage.close();
        } else ViewUtils.alert(Alert.AlertType.ERROR, "Start Game", "Please choose a player!");
    }

    public void back() throws Exception {
        stage.close();
        new MainMenu().start(SignupMenu.getStage());
    }
}
