package view;

import controller.Utils;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class Scoreboard extends Application {
    //TODO: add lazy loading to scoreboard
    private static Stage stage;
    @FXML
    private TableView<Object> scoreboard;

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
        Utils.sortUsers();
        scoreboard.setItems(Utils.getObservable());
        addColumns();
    }

    private void addColumns() {
        columnMaker("Rank");
        columnMaker("Username");
        columnMaker("Score");
    }


    private void columnMaker(String field) {
        Utils.columnMaker(scoreboard, field);
    }

    public void back() throws Exception {
        stage.close();
    }
}
