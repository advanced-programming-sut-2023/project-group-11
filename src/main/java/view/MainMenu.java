package view;

import controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.lang.reflect.Method;
import java.net.URL;

public class MainMenu extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = FXMLLoader.load(
                new URL(MainMenu.class.getResource("/FXML/MainMenu.fxml").toExternalForm()));
        MainMenu.stage = stage;
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);

        stage.show();
    }

    public void newGame() throws Exception {
        new NewGame().start(stage);
//        new NewGame().start(new Stage());
    }

    public void profileMenu() throws Exception {
        new ProfileMenu().start(SignupMenu.getStage());
    }

    public void mapSelection() throws Exception {
        new MapSelection().start(new Stage());
    }

    public void logout() throws Exception {
        MainMenuController.logout();
        new SignupMenu().start(SignupMenu.getStage());
    }
}
