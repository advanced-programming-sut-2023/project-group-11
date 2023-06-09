package view;

import controller.SelectUnitMenuController;
import controller.ShowMapMenuController;
import controller.Utils;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.map.Tile;
import view.enums.messages.SelectUnitMenuMessages;

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
    public void getDestinationCoordinates() {
        try {
            int destinationX = Integer.parseInt(xDestination.getText());
            int destinationY = Integer.parseInt(yDestination.getText());
            moveUnit(destinationX, destinationY);
        } catch (NumberFormatException e) {
            ViewUtils.alert(Alert.AlertType.ERROR, "Move Error", "Please enter a number!");
        }
    }

    public void getDestinationTile() {
        stage.close();
        Utils.getGameMenu().selectMoveTile(this);
    }

    public void moveUnit(int destinationX, int destinationY) {
        SelectUnitMenuMessages message;

        if (selectedSoldierImage.getImage() != null) {
            String unitType = getUnitTypeByImage(selectedSoldierImage);
            selectedTiles = new ArrayList<>(SelectUnitMenuController.getUnEmptyTiles(selectedTiles, unitType));

            for (Tile selectedTile : selectedTiles) {
                int[] location = ShowMapMenuController.getCurrentMap().getTileLocation(selectedTile);
                message = SelectUnitMenuController.checkMoveUnit(new int[]{location[0], location[1]},
                        destinationY, destinationX, unitType, false);

                handleMoveError(message);
            }
        } else ViewUtils.alert(Alert.AlertType.ERROR, "Move Error", "Please select the unit image!");
    }

    private String getUnitTypeByImage(ImageView selectedSoldierImage) {
        String[] splitted = selectedSoldierImage.getImage().getUrl().split("/");
        String imageName = splitted[splitted.length - 1];
        return imageName.substring(0, imageName.lastIndexOf('.'));
    }

    private void handleMoveError(SelectUnitMenuMessages message) {
        switch (message) {
            case SUCCESS -> stage.close();
            case INVALID_COORDINATE -> ViewUtils.alert(Alert.AlertType.ERROR, "Move Error!",
                    "Invalid Coordinates!");
            case INVALID_DESTINATION_TEXTURE -> ViewUtils.alert(Alert.AlertType.ERROR, "Move Error!",
                    "Invalid Destination: Invalid Texture!");
            case NO_MOVES_NEEDED -> ViewUtils.alert(Alert.AlertType.ERROR, "Move Error!",
                    "No Move Is Needed!");
            case NO_MOVES_LEFT -> ViewUtils.alert(Alert.AlertType.ERROR, "Move Error!",
                    "No Moves Left!");
            case INVALID_DESTINATION_UNCLIMBABLE_BUILDING -> ViewUtils.alert(Alert.AlertType.ERROR, "Move Error!",
                    "Invalid Destination: Invalid Building In Destination!");
            case INVALID_DESTINATION_DIFFERENT_OWNER_UNIT -> ViewUtils.alert(Alert.AlertType.ERROR, "Move Error!",
                    "Invalid Destination: Invalid Unit type in destination!");
            case INVALID_DISTANCE -> ViewUtils.alert(Alert.AlertType.ERROR, "Move Error!",
                    "Invalid Destination: Too Far For Going There, Based On Unit's Speed!");
        }
    }
}
