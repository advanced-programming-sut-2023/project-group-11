package view;

import controller.MainMenuController;
import controller.MapEditMenuController;
import controller.Utils;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.User;
import model.map.Map;

import java.net.URL;

public class NewGame extends Application {
    private static Stage stage;
    public TableView<User> users;
    public ChoiceBox<Map> mapName;

    @Override
    public void start(Stage stage) throws Exception {
        NewGame.stage = stage;
        BorderPane borderPane = FXMLLoader.load(
                new URL(SignupMenu.class.getResource("/FXML/NewGame.fxml").toExternalForm()));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    public void initialize() {
        initializeMapNames();
        initializeUsers();
    }

    private void initializeUsers() {
        users.setItems(MainMenuController.removeCurrentUserFromList(Utils.getUsersObservable()));
        addColumns();
        users.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void initializeMapNames() {
        MapEditMenuController.setMapsOnChoiceBox(mapName);
    }

    private void addColumns() {
        Utils.columnMaker(users, "Avatar", "avatar");
        Utils.columnMaker(users, "Rank", "rank");
        Utils.columnMaker(users, "Username", "username");
        Utils.columnMaker(users, "Score", "score");
    }

    public void startGame() throws Exception {
        if (users.getSelectionModel().getSelectedItems().size() >= 1) {
            MainMenuController.startGame(users.getSelectionModel().getSelectedItems(), mapName.getValue().getName());
            stage.close();
        }
        else ViewUtils.alert(Alert.AlertType.ERROR,"Start Game","Please choose a player!");
    }

    public void back() throws Exception {
        stage.close();
        new MainMenu().start(SignupMenu.getStage());
    }
}
