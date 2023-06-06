package view;

import controller.MapEditMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

public class MapSelection extends Application {
    @FXML
    private ChoiceBox mapsChoiceBox;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        MapSelection.stage = stage;
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(MainMenu.class.getResource("/FXML/MapSelection.fxml").toExternalForm()));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    public void initialize() {
        MapEditMenuController.setMapsOnChoiceBox(mapsChoiceBox);
    }

    public void mapEditMenu() throws Exception {
        stage.close();
        new MapEditMenu().start(SignupMenu.getStage());
    }
}
