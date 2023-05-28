package view;

import controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class MainMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = FXMLLoader.load(
                new URL(MainMenu.class.getResource("/FXML/MainMenu.fxml").toExternalForm()));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);

        stage.show();
    }

    public void newGame(MouseEvent mouseEvent) {

    }

    public void profileMenu(MouseEvent mouseEvent) {

    }

    public void mapEditMenu(MouseEvent mouseEvent) {

    }

    public void logout() throws Exception {
        MainMenuController.logout();
        new SignupMenu().start(SignupMenu.getStage());
    }
}
