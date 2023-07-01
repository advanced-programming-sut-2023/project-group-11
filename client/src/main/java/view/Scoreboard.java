package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import webConnection.Client;

import java.io.IOException;
import java.net.URL;

import static java.lang.Thread.sleep;

public class Scoreboard extends Application {
    //TODO: add lazy loading to scoreboard
    private static Stage stage;
    private static Scoreboard instance;
    @FXML
    private TableView scoreboard;

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
        addColumns();
    }

    private void addColumns() throws IOException {
        ViewUtils.columnMaker(scoreboard, "Avatar", "avatar");
        ViewUtils.columnMaker(scoreboard, "Rank", "rank");
        ViewUtils.columnMaker(scoreboard, "Username", "username");
        ViewUtils.columnMaker(scoreboard, "Score", "score");
        ViewUtils.columnMaker(scoreboard, "LastSeen", "lastSeen");
        scoreboard.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void back() throws Exception {
        Client.getConnection().doInServer("Utils", "setInScoreboard", false);
        stage.close();
    }

    public void refresh() throws IOException {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scoreboard.getItems().clear();
        scoreboard.setItems(ViewUtils.getUsersObservable());
    }

    public static void updateScoreboard() throws IOException {
        instance.refresh();
    }
}
