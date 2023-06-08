package view;

import controller.SelectUnitMenuController;
import controller.ShowMapMenuController;
import controller.Utils;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.map.Tile;

import java.net.URL;
import java.util.ArrayList;

public class MoveUnit extends Application {
    private static Stage stage;
    public TextField xDestination;
    public TextField yDestination;
    public ImageView selectedSoldierImage;
    private ArrayList<Tile> selectedTiles = Utils.getGameMenu().getSelectedTiles();
    public HBox soldiers;

    @Override
    public void start(Stage stage) throws Exception {
        MoveUnit.stage = stage;
        AnchorPane anchorPane = FXMLLoader.load(new URL(getClass().getResource("/FXML/MoveUnit.fxml").toExternalForm()));

        stage.setScene(new Scene(anchorPane));
        stage.show();
    }

    public void initialize() {
        ArrayList<HBox> hBoxes = SelectUnitMenuController.getTilesUnits(selectedTiles);
        soldiers.getChildren().addAll(hBoxes);
        for (HBox hBox : hBoxes)
            hBox.setOnMouseClicked(mouseEvent ->
                    selectedSoldierImage.setImage(((ImageView) hBox.getChildren().get(0)).getImage()));
    }

    @FXML
    private void getDestinationCoordinates() {
        try {
            int destinationX = Integer.parseInt(xDestination.getText());
            int destinationY = Integer.parseInt(yDestination.getText());
            String[] splitted = selectedSoldierImage.getImage().getUrl().split("/");
            String unitType = splitted[splitted.length - 1].split("\\.")[0].replace('_', ' ');
            stage.close();
            selectedTiles = new ArrayList<>(SelectUnitMenuController.getUnEmptyTiles(selectedTiles));
            for (Tile selectedTile : selectedTiles) {
                int[] location = ShowMapMenuController.getCurrentMap().getTileLocation(selectedTile);
                moveUnit(location[0], location[1], destinationX, destinationY, unitType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void moveUnit(int pressedTileX, int pressedTileY, int destinationX, int destinationY, String unitType) {
        SelectUnitMenuController.checkMoveUnit(new int[]{pressedTileX, pressedTileY},
                destinationY, destinationX, unitType, false);
    }
}
