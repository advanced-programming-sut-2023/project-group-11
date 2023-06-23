package view;

import controller.SelectUnitMenuController;
import controller.ShowMapMenuController;
import controller.Utils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.map.Tile;

import java.net.URL;
import java.util.ArrayList;

public class SetUnitState extends Application {
    private static Stage stage;
    @FXML
    private ImageView selectedSoldierImage;
    @FXML
    private ToggleGroup checkAction;
    private ArrayList<Tile> selectedTiles = Utils.getGameMenu().getSelectedTiles();
    private String unitType;
    public HBox soldiers;

    @Override
    public void start(Stage stage) throws Exception {
        SetUnitState.stage = stage;
        AnchorPane anchorPane = FXMLLoader.load(new URL(getClass().getResource("/FXML/SetUnitState.fxml").toExternalForm()));

        stage.setScene(new Scene(anchorPane));
        stage.show();
    }

    public void initialize() {
        ArrayList<HBox> hBoxes = SelectUnitMenuController.getTilesUnits(selectedTiles);
        soldiers.getChildren().addAll(hBoxes);
        selectedSoldierImage.setImage(getHBoxImage(hBoxes.get(0)));
        for (HBox hBox : hBoxes)
            hBox.setOnMouseClicked(mouseEvent -> selectedSoldierImage.setImage(getHBoxImage(hBox)));
    }

    private Image getHBoxImage(HBox hBox) {
        return ((ImageView) hBox.getChildren().get(0)).getImage();
    }

    public void checkAction() {
        initializeAction();
        RadioButton selected = (RadioButton) checkAction.getSelectedToggle();
        for (Tile selectedTile : selectedTiles) {
            int[] location = ShowMapMenuController.getCurrentMap().getTileLocation(selectedTile);
            switch (selected.getText()) {
                case "Disband" -> {
                    SelectUnitMenuController.disbandUnit(location, unitType);
                    Utils.getGameMenu().showMap();
                }
                case "Standing", "Offensive", "Defensive" ->
                        SelectUnitMenuController.checkSetUnitState(selected.getText(), location, unitType);
            }
        }
    }

    private String getUnitTypeByImage(ImageView selectedSoldierImage) {
        String[] splitted = selectedSoldierImage.getImage().getUrl().split("/");
        String imageName = splitted[splitted.length - 1];
        return imageName.substring(0, imageName.lastIndexOf('.'));
    }

    private void initializeAction() {
        unitType = getUnitTypeByImage(selectedSoldierImage);
        selectedTiles = new ArrayList<>(SelectUnitMenuController.getUnEmptyTiles(selectedTiles, unitType));
    }

    public void apply() {
        checkAction();
        stage.close();
    }
}
