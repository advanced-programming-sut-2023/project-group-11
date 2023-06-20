package view;

import controller.SelectUnitMenuController;
import controller.ShowMapMenuController;
import controller.Utils;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
    public ToggleGroup checkAction;
    private ArrayList<Tile> selectedTiles = Utils.getGameMenu().getSelectedTiles();
    private String unitType;
    public HBox soldiers;
    private SelectUnitMenuMessages message;

    @Override
    public void start(Stage stage) throws Exception {
        MoveUnit.stage = stage;
        AnchorPane anchorPane = FXMLLoader.load(new URL(getClass().getResource("/FXML/SelectUnit.fxml").toExternalForm()));

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

    @FXML
    public void getDestinationCoordinates() throws Exception {
        try {
            int destinationX = Integer.parseInt(xDestination.getText());
            int destinationY = Integer.parseInt(yDestination.getText());
            checkAction(destinationX, destinationY);
        } catch (NumberFormatException e) {
            ViewUtils.alert(Alert.AlertType.ERROR, "Move Error", "Please enter a number!");
        }
    }

    public void checkAction(int destinationX, int destinationY) throws Exception {
        initializeAction();
        RadioButton selected = (RadioButton) checkAction.getSelectedToggle();
        getClass().getDeclaredMethod("check" + selected.getText() + "Unit", int.class, int.class).
                invoke(this, destinationX, destinationY);
    }

    private void checkMoveUnit(int destinationX, int destinationY) {
        moveUnit(destinationX, destinationY, false);
    }

    public void checkPatrolUnit(int destinationX, int destinationY) {
        moveUnit(destinationX, destinationY, true);
    }

    public void moveUnit(int destinationX, int destinationY, boolean isPatrol) {
        for (Tile selectedTile : selectedTiles) {
            int[] location = ShowMapMenuController.getCurrentMap().getTileLocation(selectedTile);
            message = SelectUnitMenuController.checkMoveUnit(new int[]{location[0], location[1]},
                    destinationX, destinationY, unitType, isPatrol);

            handleMoveError(message);
        }
    }

    public void checkAttackUnit(int destinationX, int destinationY) {
        for (Tile selectedTile : selectedTiles) {
            message = SelectUnitMenuController.checkAttack(ShowMapMenuController.getCurrentMap().getTileLocation(selectedTile),
                    destinationX, destinationY, unitType);

            handleAttackError(message);
        }
    }

    public void getDestinationTile() {
        stage.close();
        Utils.getGameMenu().selectDestinationTile(this);
    }

    private void handleAttackError(SelectUnitMenuMessages message) {
        switch (message) {
            case SUCCESS -> {
                stage.close();
                //TODO: add attack animation and attack banner
                Utils.getGameMenu().showMap();
            }
            case INVALID_COORDINATE -> ViewUtils.alert(Alert.AlertType.ERROR, "Attack Unit",
                    "Invalid Coordinate!");
            case INVALID_UNIT_TYPE_TO_ATTACK -> ViewUtils.alert(Alert.AlertType.ERROR, "Attack Unit",
                    "Cannot Attack With This Unit!");
            case OUT_OF_RANGE -> ViewUtils.alert(Alert.AlertType.ERROR, "Attack Unit",
                    "Target is Out Of Range!");
            case NO_ATTACK_LEFT -> ViewUtils.alert(Alert.AlertType.ERROR, "Attack Unit",
                    "You Attacked Once This Round With This Unit!");
            case EMPTY_TILE -> ViewUtils.alert(Alert.AlertType.ERROR, "Attack Unit",
                    "There Is No One In Target-Tile!");
            case FRIENDLY_ATTACK -> ViewUtils.alert(Alert.AlertType.ERROR, "Attack Unit",
                    "You Can't Attack To Your Own Troops And Building!");
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
