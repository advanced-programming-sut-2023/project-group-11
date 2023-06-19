package view;

import controller.MapEditMenuController;
import controller.ShowMapMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.map.Tile;
import view.enums.Zoom;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MapEditMenu extends Application {
    @FXML
    private AnchorPane sidePane;
    @FXML
    private AnchorPane mapPane;
    private Zoom currentZoom = Zoom.NORMAL;
    private int tileSize = currentZoom.getSize();
    private int mapSize;
    private int firstTileXInMap = 0;
    private int firstTileYInMap = 0;
    private int selectedTileXInScreen = 0;
    private int selectedTileYInScreen = 0;
    private int pressedTileXInScreen = 0;
    private int pressedTileYInScreen = 0;
    private Tile selectedTile;
    private ArrayList<Tile> selectedTiles = new ArrayList<>();
    private int selectedBorderWidth = 1;
    private int selectedBorderHeight = 1;
    private final int mapPaneHeight = 720;
    private final int mapPaneWidth = 990;

    // -------------------------------- Start -----------------------------------------------------

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(MainMenu.class.getResource("/FXML/MapEditMenu.fxml").toExternalForm()));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    public void initialize() {
        mapSize = ShowMapMenuController.getCurrentMap().getSize();
        showMap();
        setTraversable();
    }

    private void setTraversable() {
        for (Node child : sidePane.getChildren())
            child.setFocusTraversable(false);
        mapPane.requestFocus();
    }

    // ---------------------------------- Controller-kind Methods ---------------------------------

    public void back() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Saving map");
        alert.setContentText("Are you sure about saving map?");
        ButtonType saveButton = new ButtonType("Save");
        ButtonType cancelButton = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(saveButton, cancelButton);
        ButtonType result = alert.showAndWait().orElse(cancelButton);

        if (result.equals(saveButton)) {
            MapEditMenuController.saveMap();
            ViewUtils.alert(Alert.AlertType.INFORMATION, "Saving map", "Map saved successfully!");
            new MainMenu().start(SignupMenu.getStage());
        }
    }

    // ---------------------------------- Show map ------------------------------------------------

    private void showMap() {
        mapPane.getChildren().clear();
        int rowsCount = mapPaneHeight / tileSize;
        int columnCount = mapPaneWidth / tileSize;
        Tile[][] mapTiles = ShowMapMenuController.getTiles(firstTileXInMap, firstTileYInMap, rowsCount, columnCount);

        setTextureTreeImages(mapTiles);
        boldSelectedTile(mapTiles);
    }

    private void setTextureTreeImages(Tile[][] mapTiles) {
        int xCoordinate = 0, yCoordinate = 0;

        for (Tile[] tiles : mapTiles) {
            for (Tile tile : tiles) {
                setTileImage(tile.getTexture().getImage(), xCoordinate, yCoordinate);
                if (tile.getTree() != null) setTileImage(tile.getTree().getImage(), xCoordinate, yCoordinate);
                xCoordinate += tileSize;
            }
            yCoordinate += tileSize;
            xCoordinate = 0;
        }
    }

    private void boldSelectedTile(Tile[][] mapTiles) {
        int xCoordinate = 0, yCoordinate = 0;

        for (Tile[] tiles : mapTiles) {
            for (Tile tile : tiles) {
                if (tile.equals(selectedTile)) {
                    javafx.scene.shape.Rectangle border = new Rectangle(xCoordinate, yCoordinate, selectedBorderWidth * tileSize, selectedBorderHeight * tileSize);
                    border.setStroke(Color.RED);
                    border.setStrokeWidth(2);
                    border.setFill(null);
                    mapPane.getChildren().add(border);
                    return;
                }
                xCoordinate += tileSize;
            }
            yCoordinate += tileSize;
            xCoordinate = 0;
        }
    }

    private void setTileImage(Image image, int xCoordinate, int yCoordinate) {
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(xCoordinate);
        imageView.setLayoutY(yCoordinate);
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);
        mapPane.getChildren().add(imageView);
    }

    public void press(MouseEvent mouseEvent) {
        pressedTileXInScreen = Math.floorDiv((int) mouseEvent.getX(), tileSize);
        pressedTileYInScreen = Math.floorDiv((int) mouseEvent.getY(), tileSize);
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            selectedTiles.clear();
            selectedBorderHeight = 1;
            selectedBorderWidth = 1;
            Tile tile = ShowMapMenuController.getSelectedTile(pressedTileXInScreen, pressedTileYInScreen, firstTileXInMap, firstTileYInMap);
            if (tile.equals(selectedTile)) selectedTile = null;
            else {
                selectedTile = tile;
                selectedTiles.add(selectedTile);
                selectedTileXInScreen = pressedTileXInScreen;
                selectedTileYInScreen = pressedTileYInScreen;
            }
            showMap();
        }
    }

    public void drag(MouseEvent mouseEvent) {
        int endTileX = Math.floorDiv((int) mouseEvent.getX(), tileSize);
        int endTileY = Math.floorDiv((int) mouseEvent.getY(), tileSize);
        int deltaX = endTileX - pressedTileXInScreen;
        int deltaY = endTileY - pressedTileYInScreen;
        if (deltaX == 0 && deltaY == 0) return;

        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) moveMapMove(deltaX, deltaY);
        else if (selectedTile != null && mouseEvent.getButton().equals(MouseButton.MIDDLE))
            selectMultipleTiles(deltaX, deltaY);
    }

    public void moveMapMove(int deltaX, int deltaY) {
        if (firstTileXInMap - deltaX >= 0 && firstTileXInMap - deltaX < mapSize - (mapPaneWidth / tileSize))
            firstTileXInMap -= deltaX;
        if (firstTileYInMap - deltaY >= 0 && firstTileYInMap - deltaY < mapSize - (mapPaneHeight / tileSize))
            firstTileYInMap -= deltaY;

        showMap();

        pressedTileXInScreen += deltaX;
        pressedTileYInScreen += deltaY;
    }

    private boolean outOfPane(int deltaX, int deltaY) {
        return (pressedTileXInScreen + deltaX) - firstTileXInMap >= (mapPaneWidth / tileSize) ||
                (pressedTileYInScreen + deltaY) - firstTileYInMap >= (mapPaneHeight / tileSize);

    }

    private void selectMultipleTiles(int deltaX, int deltaY) {
        int selectedColumns = (pressedTileXInScreen - selectedTileXInScreen) + deltaX + 1;
        int selectedRows = (pressedTileYInScreen - selectedTileYInScreen) + deltaY + 1;

        if (selectedRows < 1 || selectedColumns < 1 || outOfPane(deltaX, deltaY)) return;
        Tile[][] tempArray = ShowMapMenuController.getTiles(selectedTileXInScreen + firstTileXInMap,
                selectedTileYInScreen + firstTileYInMap, selectedRows, selectedColumns);

        selectedTiles.clear();
        for (Tile[] tiles : tempArray)
            selectedTiles.addAll(Arrays.asList(tiles));

        selectedBorderWidth = selectedColumns;
        selectedBorderHeight = selectedRows;

        showMap();

        pressedTileXInScreen += deltaX;
        pressedTileYInScreen += deltaY;
    }


    public void checkShortcut(KeyEvent keyEvent){
        KeyCode keyCode = keyEvent.getCode();

        switch (keyCode) {
            case ADD -> zoom(true);
            case SUBTRACT -> zoom(false);
        }
    }

    private void zoom(boolean zoomIn) {
        if (currentZoom.getLevel() < 4 && zoomIn) {
            currentZoom = Zoom.getZoomByLevel(currentZoom.getLevel() + 1);
            tileSize = currentZoom.getSize();
        } else if (currentZoom.getLevel() > 0 && !zoomIn) {
            currentZoom = Zoom.getZoomByLevel(currentZoom.getLevel() - 1);
            tileSize = currentZoom.getSize();
        } else Toolkit.getDefaultToolkit().beep();

        showMap();
    }
}
