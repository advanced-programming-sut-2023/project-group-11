package view;

import controller.MapEditMenuController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.enums.messages.MapEditMenuMessages;

import java.net.URL;

public class MapSelection extends Application {
    @FXML
    private Label mapSizeError;
    @FXML
    private Label mapNameError;
    @FXML
    private TextField newMapName;
    @FXML
    private TextField newMapSize;
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

    public void selectMap() throws Exception {
        stage.close();
        new MapEditMenu().start(SignupMenu.getStage());
    }

    public void makeNewMap() throws Exception {
        MapEditMenuMessages message = MapEditMenuController.checkMakeNewMap(newMapName.getText(), newMapSize.getText());
        ViewUtils.clearLabels(mapNameError, mapSizeError);

        switch (message) {
            case MAP_NAME_FIELD_EMPTY -> ViewUtils.fieldError(mapNameError, "Required fields must be filled in!");
            case MAP_SIZE_FIELD_EMPTY -> ViewUtils.fieldError(mapSizeError, "Required fields must be filled in!");
            case INVALID_MAP_SIZE_FORMAT -> ViewUtils.fieldError(mapSizeError, "Invalid format!");
            case INVALID_MAP_SIZE -> ViewUtils.fieldError(mapSizeError, "Invalid size, enter between 50 & 200");
            case MAP_EXIST -> ViewUtils.fieldError(mapNameError, "Map already exist!");
            case SUCCESS -> {
                ViewUtils.alert(Alert.AlertType.INFORMATION, "New Map", "New map made successfully!");
                stage.close();
                new MapEditMenu().start(SignupMenu.getStage());
            }
        }
    }
}
