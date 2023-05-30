package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Stronghold;
import model.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class Scoreboard extends Application {
    private static Stage stage;
    @FXML
    private TableView<User> scoreboard;

    @Override
    public void start(Stage stage) throws Exception {
        Scoreboard.stage = stage;
        BorderPane borderPane = FXMLLoader.load(
                new URL(SignupMenu.class.getResource("/FXML/Scoreboard.fxml").toExternalForm()));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    public void initialize() {
        ArrayList<User> users = Stronghold.getUsers();
        Collections.sort(users);
        ObservableList<User> observableUsers = FXCollections.observableArrayList(users);
        setRanks(users);
        scoreboard.setItems(observableUsers);
        addColumns();
    }

    private void addColumns() {
        columnMaker("Rank");
        columnMaker("Username");
        columnMaker("Score");
    }

    private void setRanks(ArrayList<User> users) {
        for (int i = 0; i < users.size(); i++) users.get(i).setRank(i + 1);
    }

    private void columnMaker(String field) {
        TableColumn<User, String> tableColumn = new TableColumn<>(field);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>(field.toLowerCase()));
        scoreboard.getColumns().add(tableColumn);
    }

    public void back() throws Exception {
        stage.close();
    }
}
