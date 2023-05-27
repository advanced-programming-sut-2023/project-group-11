package view;

import controller.EntryMenuController;
import controller.Utils;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;

public class SignupMenu extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        SignupMenu.stage = stage;
        BorderPane borderPane = FXMLLoader.load(
                new URL(SignupMenu.class.getResource("/FXML/SignupMenu.fxml").toExternalForm()));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);

        EntryMenuController.fillAllFieldsWithPreviousData();
        stage.setOnCloseRequest(windowEvent -> Utils.endStronghold());
        if (EntryMenuController.getStayLoggedIn() != null) new MainMenu().start(stage);
    }
}
