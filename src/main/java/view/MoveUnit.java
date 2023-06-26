package view;

import controller.SelectUnitMenuController;
import controller.ShowMapMenuController;
import controller.Utils;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.map.Tile;
import model.people.Troop;
import model.people.Unit;
import view.enums.messages.SelectUnitMenuMessages;

import java.net.URL;
import java.util.ArrayList;

public class MoveUnit extends Application {
    private static Stage stage;
    @FXML
    private TextField xDestination;
    @FXML
    private TextField yDestination;
    @FXML
    private ImageView selectedSoldierImage;
    @FXML
    private ToggleGroup checkAction;
    @FXML
    private RadioButton attackButton;
    @FXML
    private RadioButton digPitchButton;
    @FXML
    private RadioButton digTunnelButton;
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
        limitButtons();
        for (HBox hBox : hBoxes)
            hBox.setOnMouseClicked(mouseEvent -> {
                selectedSoldierImage.setImage(getHBoxImage(hBox));
                limitButtons();
            });
    }

    private void limitButtons() {
        Unit unit = SelectUnitMenuController.getUnitByType(getUnitTypeByImage(selectedSoldierImage));
        attackButton.setDisable(!unit.canAttack());
        digPitchButton.setDisable(!(unit instanceof Troop troop && troop.canDigPitch()));
        digTunnelButton.setDisable(!unit.canDigTunnel());
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

    public void checkDigPitchUnit(int destinationX, int destinationY) {
        for (Tile selectedTile : selectedTiles) {
            message = SelectUnitMenuController.checkDigPitch(
                    ShowMapMenuController.getCurrentMap().getTileLocation(selectedTile), destinationX, destinationY, unitType);

            handleDigPitchError(message);
        }
    }

    public void checkDigTunnelUnit(int destinationX, int destinationY) {
        for (Tile selectedTile : selectedTiles) {
            message = SelectUnitMenuController.checkDigTunnel(
                    ShowMapMenuController.getCurrentMap().getTileLocation(selectedTile), destinationX, destinationY, unitType);

            handleDigTunnelError(message);
        }
    }

    public void getDestinationTile() {
        stage.close();
        Utils.getGameMenu().selectDestinationTile(this);
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

    private void handleAttackError(SelectUnitMenuMessages message) {
        switch (message) {
            case SUCCESS -> stage.close();
            case INVALID_COORDINATE -> ViewUtils.alert(Alert.AlertType.ERROR, "Attack Unit",
                    "Invalid Coordinate!");
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

    private void handleDigPitchError(SelectUnitMenuMessages message) {
        switch (message) {
            case SUCCESS -> System.out.println("Digging Pitch Started!");
            case INVALID_DIRECTION -> ViewUtils.alert(Alert.AlertType.ERROR, "Digging Error", "Invalid Direction!");
            case INVALID_LENGTH ->
                    ViewUtils.alert(Alert.AlertType.ERROR, "Digging Error", "Invalid Final Destination Based On Digging Length!");
            case INVALID_AREA_FOR_DIGGING_PITCH ->
                    ViewUtils.alert(Alert.AlertType.ERROR, "Digging Error", "Invalid Area For Digging Pitch!");
        }
    }

    private void handleDigTunnelError(SelectUnitMenuMessages message) {
        switch (message) {
            case INVALID_DIRECTION ->
                    ViewUtils.alert(Alert.AlertType.ERROR, "Dig Tunnel", "Direction must be up or left or down or right!");
            case INVALID_AREA_FOR_DIGGING_TUNNEL ->
                    ViewUtils.alert(Alert.AlertType.ERROR, "Dig Tunnel", "Invalid Area For Digging Pitch!");
        }
    }
}
