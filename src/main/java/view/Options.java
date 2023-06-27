package view;

import controller.GameMenuController;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;

public class Options extends Application {
    private static Stage stage;
    @FXML
    private VBox labels;

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(getClass().getResource("/FXML/Options.fxml").toExternalForm()));
        Options.stage = stage;
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public void initialize() {
        for (Node child : labels.getChildren()) {
            Label label = (Label) child;
            label.setOnMouseEntered(mouseEvent -> label.setStyle("-fx-border-color: white;"));
            label.setOnMouseExited(mouseEvent -> label.setStyle("-fx-border-style: none"));
        }
    }

    @FXML
    private void exit() {
        Optional<ButtonType> result =
                ViewUtils.alert(Alert.AlertType.CONFIRMATION, "End Game", "Are you sure to exit game?");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            GameMenuController.endGame(false);
            stage.close();
            SignupMenu.getStage().close();
        }
    }

    @FXML
    private void backToMainMenu() throws Exception {
        Optional<ButtonType> result =
                ViewUtils.alert(Alert.AlertType.CONFIRMATION, "End Game", "Are you sure to go back to main menu?");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage.close();
            GameMenuController.endGame(false);
            new MainMenu().start(SignupMenu.getStage());
        }
    }

    @FXML
    private void restart() throws Exception {
        Optional<ButtonType> result =
                ViewUtils.alert(Alert.AlertType.CONFIRMATION, "End Game", "Are you sure to restart game?");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage.close();
            ArrayList<User> users = GameMenuController.getUsernames();
            String mapName = GameMenuController.getMapName();
            GameMenuController.endGame(false);
            MainMenuController.startGame(users, mapName);
        }
    }

    @FXML
    private void resume() {
        stage.close();
    }
}
