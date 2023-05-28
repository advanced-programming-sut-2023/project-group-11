package view;

import controller.SignupMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class SignupMenu extends Application {

    @FXML
    private TextField signupUsername;
    @FXML
    private Label signupUsernameLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(
                new URL(SignupMenu.class.getResource("/FXML/SignupMenu.fxml").toExternalForm()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    public void initialize(){
        signupUsername.textProperty().addListener((observable, oldText, newText)->{
            signupUsernameLabel.setText(SignupMenuController.checkUsername(newText).getMessage());
        });
    }
}
