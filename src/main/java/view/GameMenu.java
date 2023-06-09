package view;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.map.Tile;
import view.enums.Zoom;

import java.awt.*;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameMenu extends Application {
    public GameMenu() {
        Utils.setGameMenu(this);
    }

    @FXML
    private AnchorPane sidePane;
    @FXML
    private AnchorPane mapPane;
    @FXML
    private TilePane productiveBox;
    @FXML
    private TilePane warBox;
    @FXML
    private TilePane governanceBox;
    @FXML
    private Label buildingNameLabel;
    private final int mapPaneHeight = 720;
    private final int mapPaneWidth = 990;
    private int tileSize = 30;
    private Zoom currentZoom = Zoom.NORMAL;
    private int mapSize;
    private double buildingDragX;
    private double buildingDragY;
    private String buildingDragName;
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
    private Tooltip tooltip;

    // -------------------------------- Start -----------------------------------------------------

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(getClass().getResource("/FXML/GameMenu.fxml").toExternalForm()));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        GameMenuController.setCurrentGame();
        initMap();
        mapSize = ShowMapMenuController.getCurrentMap().getSize();
        setTraversable();
        showMap();
        initializeToolTip();
        initializeBuildingBoxes();
    }

    private void initMap() {
//        ArrayList<Building> buildings = new ArrayList<>();
//        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
//        for (ProductiveBuildingType productiveBuildingType : ProductiveBuildingType.values()) {
//            buildings.add(BuildingUtils.getBuildingByType(productiveBuildingType.getName()));
//        }
//        for (UnitMakerType productiveBuildingType : UnitMakerType.values()) {
//            buildings.add(BuildingUtils.getBuildingByType(productiveBuildingType.getName()));
//        }
//
        int i = 20;
//        for (Building building : buildings) {
//            BuildingUtils.build(currentGovernance, building, i, i, building.getSize());
//            i += 4;
//        }


//        GameMenuController.dropUnit(i++, i, 10, "battering ram");
//        GameMenuController.dropUnit(i++, i, 10, "trebuchets");
//        GameMenuController.dropUnit(i++, i, 10, "portable shield");
//        GameMenuController.dropUnit(i++, i, 10, "catapults");
//        GameMenuController.dropUnit(i++, i, 10, "siege tower");
//        GameMenuController.dropUnit(i++, i, 10, "fire ballista");
//        GameMenuController.dropUnit(i++, i, 10, "engineer");
        GameMenuController.dropUnit(i++, i, 10, "horse archer");
        GameMenuController.dropUnit(i++, i, 10, "tunneler");
        GameMenuController.dropUnit(i++, i, 10, "slaves");
        GameMenuController.dropUnit(i++, i, 10, "slaves");
        GameMenuController.dropUnit(i++, i, 10, "slinger");
        GameMenuController.dropUnit(i++, i, 10, "ladderman");
        GameMenuController.dropUnit(i++, i, 10, "fire thrower");
        GameMenuController.dropUnit(i++, i, 10, "archer");
        GameMenuController.dropUnit(i++, i, 10, "archer bow");
        GameMenuController.dropUnit(i++, i, 10, "swordsman");
        GameMenuController.dropUnit(i++, i, 10, "knight");
        GameMenuController.dropUnit(i++, i, 10, "black monk");
        GameMenuController.dropUnit(i++, i, 10, "pikeman");
        GameMenuController.dropUnit(i++, i, 10, "crossbowman");
        GameMenuController.dropUnit(i++, i, 10, "crossbowman");
        GameMenuController.dropUnit(i++, i, 10, "assassin");
        GameMenuController.dropUnit(i++, i, 10, "maceman");
        GameMenuController.dropUnit(i++, i, 10, "spearman");
        GameMenuController.dropUnit(i++, i, 10, "arabian swordsman");
        GameMenuController.dropUnit(39, 44, 10, "horse archer");
        GameMenuController.dropUnit(41, 44, 10, "horse archer");
        GameMenuController.dropUnit(42, 44, 10, "horse archer");
        GameMenuController.dropUnit(43, 44, 10, "horse archer");
    }

    private void setTraversable() {
        for (Node child : sidePane.getChildren())
            child.setFocusTraversable(false);
        mapPane.requestFocus();
    }

    // ---------------------------------- Getter/Setter -------------------------------------------

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public void setSelectedTile(Tile tile) {
        this.selectedTile = tile;
    }

    // ---------------------------------- Controller-kind Methods ---------------------------------

    public void showMap() {
        mapPane.getChildren().clear();
        int rowsCount = mapPaneHeight / tileSize;
        int columnCount = mapPaneWidth / tileSize;
        Tile[][] mapTiles = ShowMapMenuController.getTiles(firstTileXInMap, firstTileYInMap, rowsCount, columnCount);

        setTextureTreeImages(mapTiles);
        setBuildingUnitImages(mapTiles);
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

    private void setBuildingUnitImages(Tile[][] mapTiles) {
        int xCoordinate = 0, yCoordinate = 0;

        for (Tile[] tiles : mapTiles) {
            for (Tile tile : tiles) {
                //TODO: needs debug for buildings with size more than 1
                if (tile.getBuilding() != null)
                    setTileBuildingImage(tile.getBuilding().getImage(), xCoordinate, yCoordinate, tile.getBuilding().getSize(),
                            tile.getBuilding().getXCoordinate(), tile.getBuilding().getYCoordinate());
                if (tile.getUnits().size() != 0)
                    setTileImage(tile.getUnits().get(tile.getUnits().size() - 1).getImage(), xCoordinate, yCoordinate);
                if (tile.equals(selectedTile)) boldSelectedTile(xCoordinate, yCoordinate);
                xCoordinate += tileSize;
            }
            yCoordinate += tileSize;
            xCoordinate = 0;
        }
    }

    private void boldSelectedTile(int xCoordinate, int yCoordinate) {
        Rectangle border = new Rectangle(xCoordinate, yCoordinate, selectedBorderWidth * tileSize, selectedBorderHeight * tileSize);
        border.setStroke(Color.RED);
        border.setStrokeWidth(2);
        border.setFill(null);
        mapPane.getChildren().add(border);
    }

    private void setTileBuildingImage(Image image, int xCoordinate, int yCoordinate, int buildingSize, int buildingX, int buildingY) {
        if (buildingY != firstTileXInMap + (xCoordinate / tileSize) || buildingX != firstTileYInMap + (yCoordinate / tileSize))
            return; //TODO: Be careful about inverse x & y
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(xCoordinate);
        imageView.setLayoutY(yCoordinate);
        imageView.setFitWidth(tileSize * buildingSize); //TODO: do not get out of the pane
        imageView.setFitHeight(tileSize * buildingSize);
        imageView.setPreserveRatio(false);
        mapPane.getChildren().add(imageView);
    }

    private void setTileImage(Image image, int xCoordinate, int yCoordinate) {
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(xCoordinate);
        imageView.setLayoutY(yCoordinate);
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);
        mapPane.getChildren().add(imageView);
    }

    public void back() throws Exception {
        new MainMenu().start(SignupMenu.getStage());
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

//        System.out.println(selectedTiles.size());
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

    public void checkShortcut(KeyEvent keyEvent) throws Exception {
        KeyCode keyCode = keyEvent.getCode();

        switch (keyCode) {
            case ADD -> zoom(true);
            case SUBTRACT -> zoom(false);
            case E -> seeTiles();
            case M -> checkMoveUnit();
        }
    }

    private void seeTiles() {
        System.out.println(selectedTiles.size());
        for (Tile tile : selectedTiles) {
            System.out.println(tile);
        }
        System.out.println("end");
    }

    private void checkMoveUnit() throws Exception {
        if (SelectUnitMenuController.hasUnit(selectedTiles)) new MoveUnit().start(new Stage());
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

    @FXML
    private void hover(MouseEvent mouseEvent) {
        initializeToolTip();
        selectedTileXInScreen = Math.floorDiv((int) mouseEvent.getX(), tileSize);
        selectedTileYInScreen = Math.floorDiv((int) mouseEvent.getY(), tileSize);
        Tile tile = ShowMapMenuController.getSelectedTile(selectedTileXInScreen, selectedTileYInScreen, firstTileXInMap, firstTileYInMap);
        if (selectedTiles.size() > 1) tooltip.setText(ShowMapMenuController.getTilesData(selectedTiles));
        else tooltip.setText(tile.toString());
    }

    private void initializeToolTip() {
        tooltip = new Tooltip();
        tooltip.setShowDelay(Duration.seconds(1));
        tooltip.setShowDuration(Duration.seconds(3));
        tooltip.setWrapText(true);
        Tooltip.install(mapPane, tooltip);
    }

    private void initializeBuildingBoxes() {
        initializeBuildingBox(governanceBox, "Church", "Filler", "DrawBridge", "Trap");
        initializeBuildingBox(warBox, "Keep", "Tower", "Storage", "UnitMaker", "GateHouse");
        //TODO: gatehouse
        //TODO: blackBackground pics + optimizing pics(drawBridge ...)
        initializeBuildingBox(productiveBox, "ProductiveBuilding");
    }

    private void initializeBuildingBox(TilePane box, String... address) {
        ArrayList<File> buildingImages = new ArrayList<>();
        try {
            for (String add : address)
                fillBuildingImages(buildingImages, add);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        fillBuildingBox(buildingImages, box);
    }

    private void fillBuildingBox(ArrayList<File> buildingImages, TilePane box) {
        for (File buildingImage : buildingImages) {
            ImageView buildingImageView = new ImageView(buildingImage.getPath());
            buildingImageView.setFitHeight(60);
            buildingImageView.setFitWidth(60);
            buildingImageView.setPreserveRatio(true);
            buildingImageView.setId(buildingImage.getName().substring(0, buildingImage.getName().length() - 4));
            buildingImageView.setOnMouseClicked(this::buildingMouseClick);
            buildingImageView.setOnDragDetected(this::buildingDrag);
            box.getChildren().add(buildingImageView);
        }
    }

    private void buildingDrag(MouseEvent mouseEvent) {
        ImageView buildingImageView = (ImageView) mouseEvent.getSource();
        int buildingSize = BuildingUtils.getBuildingByType(buildingImageView.getId()).getSize();
        buildingDragName = buildingImageView.getId();
        ImageView dragImage = new ImageView(buildingImageView.getImage());
        dragImage.setFitWidth(tileSize * buildingSize * 1.25);
        dragImage.setFitHeight(tileSize * buildingSize * 1.25);
        dragImage.setPreserveRatio(false);
        Dragboard dragboard = buildingImageView.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putImage(dragImage.snapshot(null, null));
        dragboard.setContent(content);
        dragboard.setDragView(dragImage.snapshot(null, null), 15, 15);
        mouseEvent.consume();
    }

    private static void fillBuildingImages(ArrayList<File> buildingImages, String address) throws URISyntaxException {
        File[] array;
        array = new File(GameMenu.class.getResource("/IMG/Building/" + address).toURI()).listFiles();
        buildingImages.addAll(List.of(array));
    }

    private void buildingMouseClick(MouseEvent mouseEvent) {
        buildingNameLabel.setText(((ImageView) mouseEvent.getSource()).getId());
    }

    public void showProductiveBox() {
        productiveBox.setVisible(true);
        warBox.setVisible(false);
        governanceBox.setVisible(false);
    }

    public void showWarBox() {
        warBox.setVisible(true);
        productiveBox.setVisible(false);
        governanceBox.setVisible(false);
    }

    public void showGovernanceBox() {
        governanceBox.setVisible(true);
        warBox.setVisible(false);
        productiveBox.setVisible(false);
    }

    public void buildingDragOver(DragEvent dragEvent) {
        buildingDragX = dragEvent.getSceneX();
        buildingDragY = dragEvent.getSceneY();
//        System.out.println("asdasd");
    }

    public void buildingDragDone(DragEvent dragEvent) {
        int x = Math.floorDiv((int) buildingDragX, tileSize) + firstTileXInMap;
        int y = Math.floorDiv((int) buildingDragY, tileSize) + firstTileYInMap;
        if (buildingDragX > mapPaneWidth || buildingDragX < 0 || buildingDragY > mapPaneHeight || buildingDragY < 0)
            return;
        switch (GameMenuController.checkDropBuilding(y, x, buildingDragName)) {
            case CANT_BUILD_HERE -> ViewUtils.alert(Alert.AlertType.ERROR, "Build Error", "Can't build here!");
            case NOT_ENOUGH_MONEY ->
                    ViewUtils.alert(Alert.AlertType.ERROR, "Build Error", "You don't have enough money!");
            case NOT_ENOUGH_RESOURCE ->
                    ViewUtils.alert(Alert.AlertType.ERROR, "Build Error", "You don't have enough resource!");
            case NOT_ENOUGH_POPULATION ->
                    ViewUtils.alert(Alert.AlertType.ERROR, "Build Error", "You don't have enough population!");
            case SUCCESS -> setTileBuildingImage(BuildingUtils.getBuildingByType(buildingDragName).getImage()
                    , (x - firstTileXInMap) * tileSize, (y - firstTileYInMap) * tileSize,
                    BuildingUtils.getBuildingByType(buildingDragName).getSize(), y, x);
        }
    }

    public ArrayList<Tile> getSelectedTiles() {
        return selectedTiles;
    }

    public void nextTurn() {
        ViewUtils.alert(Alert.AlertType.INFORMATION, "Next Turn Successful", GameMenuController.nextTurn());
    }

    public void selectMoveTile(MoveUnit moveUnit) {
        mapPane.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.SECONDARY))
                moveUnit.moveUnit(firstTileXInMap + pressedTileXInScreen,
                        firstTileYInMap + selectedTileYInScreen);
            mouseEvent.consume();
        });
    }
}
