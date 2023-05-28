package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

public class ForgotPassword extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        ForgotPassword.stage = stage;
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(ForgotPassword.class.getResource("/FXML/ForgotPassword.fxml").toExternalForm()));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);

        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }
}
