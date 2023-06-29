package view;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import webConnection.Client;
import webConnection.Connection;

import java.io.IOException;
import java.net.URL;

public class Scoreboard extends Application {
    //TODO: add lazy loading to scoreboard
    private static Stage stage;
    @FXML
    private TableView scoreboard;
    private Connection connection = Client.getConnection();
    private String utils = "Utils";

    @Override
    public void start(Stage stage) throws Exception {
        Scoreboard.stage = stage;
        BorderPane borderPane = FXMLLoader.load(
                new URL(SignupMenu.class.getResource("/FXML/Scoreboard.fxml").toExternalForm()));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    public void initialize() throws IOException {
        connection.doInServer(utils, "sortUsers");
        scoreboard.setItems((ObservableList) connection.getArrayData(utils, "getUsersObservable"));//TODO: may produce exception
        addColumns();
    }

    private void addColumns() throws IOException {
        connection.doInServer(utils, "columnMaker", scoreboard, "Avatar", "avatar");
        connection.doInServer(utils, "columnMaker", scoreboard, "Rank", "rank");
        connection.doInServer(utils, "columnMaker", scoreboard, "Username", "username");
        connection.doInServer(utils, "columnMaker", scoreboard, "Score", "score");
    }

    public void back() throws Exception {
        stage.close();
    }
}
