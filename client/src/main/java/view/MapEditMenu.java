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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.map.Texture;
import model.map.Tile;
import model.map.Tree;
import view.enums.Zoom;
import view.enums.messages.MapEditMenuMessages;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MapEditMenu extends Application {
    @FXML
    private AnchorPane texturesPane;
    @FXML
    private TilePane sandBox;
    @FXML
    private TilePane waterBox;
    @FXML
    private TilePane treeBox;
    @FXML
    private Label textureNameLabel;
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
        initializeTextureBoxes();
    }

    private void initializeTextureBoxes() {
        initializeTextureBox(sandBox, Texture.SAND, Texture.IRON, Texture.STONE, Texture.CLIFF, Texture.DENSE_GRASSLAND,
                Texture.GRASS, Texture.GRASSLAND, Texture.ROCK, Texture.SAND_DUNE);
        initializeTextureBox(waterBox, Texture.BEACH, Texture.SEA, Texture.RIVER, Texture.SHALLOW_WATER, Texture.BIG_LAKE,
                Texture.SMALL_LAKE, Texture.MARSH, Texture.OIL);
        initializeTreeBox("cherry", "coconut", "date", "olive", "small");
    }

    private void initializeTextureBox(TilePane box, Texture... textures) {
        HashMap<String, Image> texturesImages = new HashMap<>();
        for (Texture texture : textures)
            texturesImages.put(texture.getName(), texture.getImage(false));
        fillBox(box, texturesImages, true);
    }

    private void initializeTreeBox(String... treeNames) {
        HashMap<String, Image> treesImages = new HashMap<>();
        for (String treeName : treeNames) {
            Tree tree = new Tree(treeName);
            treesImages.put(treeName, tree.getImage());
        }
        fillBox(treeBox, treesImages, false);
    }

    private void fillBox(TilePane box, HashMap<String, Image> images, boolean isTexture) {
        for (String imageName : images.keySet()) {
            ImageView imageView = new ImageView(images.get(imageName));
            imageView.setFitHeight(60);
            imageView.setFitWidth(60);
            imageView.setPreserveRatio(true);
            imageView.setId(imageName);
            if (isTexture)
                imageView.setOnMouseClicked(this::setTexture);
            else
                imageView.setOnMouseClicked(this::dropTree);
            box.getChildren().add(imageView);
        }
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

    public void clear() {
        MapEditMenuController.clear(selectedTiles);
        showMap();
    }

    private void changePaneVisibility(Pane pane, Pane... panes) {
        pane.setVisible(true);
        for (Pane pane1 : panes) pane1.setVisible(false);
    }

    public void showSandBox() {
        changePaneVisibility(sandBox, waterBox, treeBox);
    }

    public void showWaterBox() {
        changePaneVisibility(waterBox, sandBox, treeBox);
    }

    public void showTreeBox() {
        changePaneVisibility(treeBox, sandBox, waterBox);
    }

    public void setTexture(MouseEvent mouseEvent) {
        String textureName = ((ImageView) mouseEvent.getSource()).getId();
        textureNameLabel.setVisible(true);
        textureNameLabel.setText(textureName);

        MapEditMenuMessages message = MapEditMenuController.setTexture(selectedTiles, textureName,
                selectedTileXInScreen + firstTileXInMap, selectedTileYInScreen + firstTileYInMap);

        switch (message) {
            case SELECT_ONLY_ONE_TILE -> ViewUtils.alert(Alert.AlertType.ERROR, "Set Texture Failed",
                    "Select only on tile for this texture!");
            case INVALID_PLACE_TO_DEPLOY -> ViewUtils.alert(Alert.AlertType.ERROR, "Set Texture Failed",
                    "Invalid place to deploy this texture!");
        }

        showMap();
    }

    public void dropTree(MouseEvent mouseEvent) {
        String treeName = ((ImageView) mouseEvent.getSource()).getId();

        textureNameLabel.setVisible(true);
        textureNameLabel.setText(treeName);

        MapEditMenuMessages message = MapEditMenuController.dropTree(selectedTiles, treeName);

        switch (message) {
            case INVALID_PLACE_TO_DEPLOY -> ViewUtils.alert(Alert.AlertType.ERROR, "Drop Tree Failed",
                    "Invalid place to deploy tree!");
        }

        showMap();
    }

    // ---------------------------------- Show map ------------------------------------------------

    private void showMap() {
        mapPane.getChildren().clear();
        int rowsCount = mapPaneHeight / tileSize;
        int columnCount = mapPaneWidth / tileSize;
        Tile[][] mapTiles = ShowMapMenuController.getTiles(firstTileXInMap, firstTileYInMap, rowsCount, columnCount);

        setTextureTreeImages(mapTiles);
        boldSelectedTile(mapTiles);
        sidePane.toFront();
    }

    private void setTextureTreeImages(Tile[][] mapTiles) {
        int xCoordinate = 0, yCoordinate = 0;

        for (Tile[] tiles : mapTiles) {
            for (Tile tile : tiles) {
                setTileImage(tile.getTexture().getImage(false), xCoordinate, yCoordinate);
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

        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) moveMap(deltaX, deltaY);
        else if (selectedTile != null && mouseEvent.getButton().equals(MouseButton.MIDDLE))
            selectMultipleTiles(deltaX, deltaY);
    }

    public void moveMap(int deltaX, int deltaY) {
        if (firstTileXInMap - deltaX >= 0 && firstTileXInMap - deltaX < mapSize - (mapPaneWidth / tileSize))
            firstTileXInMap -= deltaX;
        if (firstTileYInMap - deltaY >= 0 && firstTileYInMap - deltaY < mapSize - (mapPaneHeight / tileSize))
            firstTileYInMap -= deltaY;

        showMap();

        pressedTileXInScreen += deltaX;
        pressedTileYInScreen += deltaY;
    }

    private boolean outOfPane(int deltaX, int deltaY) {
        return (pressedTileXInScreen + deltaX) >= (mapPaneWidth / tileSize) ||
                (pressedTileYInScreen + deltaY) >= (mapPaneHeight / tileSize);

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
        } else if (currentZoom.getLevel() > 0 && !zoomIn &&
                (mapPaneWidth / Zoom.getZoomByLevel(currentZoom.getLevel() - 1).getSize()) < mapSize) {
            currentZoom = Zoom.getZoomByLevel(currentZoom.getLevel() - 1);
            tileSize = currentZoom.getSize();
        } else Toolkit.getDefaultToolkit().beep();

        showMap();
    }
}
