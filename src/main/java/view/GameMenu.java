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
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Governance;
import model.Stronghold;
import model.buildings.Building;
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
    @FXML
    private Pane buildingsPane;
    @FXML
    private Pane selectBuildingPane;
    @FXML
    private Label selectBuildingLabel;
    @FXML
    private ImageView selectBuildingImageView;
    @FXML
    private Pane buildingCommandPane;
    @FXML
    private TilePane unitPane;
    @FXML
    private Label unitLabel;
    @FXML
    private AnchorPane governanceInformationPane;
    @FXML
    private Label governancePopularity;
    @FXML
    private Label governanceGold;
    @FXML
    private Label governancePopulation;
    @FXML
    private Pane clipBoardPane;
    @FXML
    private TilePane clipBoardTilePane;
    private final ArrayList<String> clipBoard = new ArrayList<>();
    private final int mapPaneHeight = 720;
    private final int mapPaneWidth = 990;
    private Zoom currentZoom = Zoom.NORMAL;
    private int tileSize = currentZoom.getSize();
    private int mapSize;
    private double buildingDragX;
    private double buildingDragY;
    private String buildingDragName;
    private String copiedBuildingName;
    private int firstTileXInMap = 0;
    private int firstTileYInMap = 0;
    private int selectedTileXInScreen = 0;
    private int selectedTileYInScreen = 0;
    private int pressedTileXInScreen = 0;
    private int pressedTileYInScreen = 0;
    private Tile selectedTile;
    private final ArrayList<Tile> selectedTiles = new ArrayList<>();
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
        GameMenuController.dropUnit(i++, i, 1, "fire ballista");
//        GameMenuController.dropUnit(i++, i, 10, "engineer");
        GameMenuController.dropUnit(i++, i, 2, "horse archer");
        GameMenuController.dropUnit(i++, i, 2, "tunneler");
        GameMenuController.dropUnit(i++, i, 2, "slaves");
        GameMenuController.dropUnit(i++, i, 2, "slaves");
        GameMenuController.dropUnit(i++, i, 2, "slinger");
        GameMenuController.dropUnit(i++, i, 2, "ladderman");
        GameMenuController.dropUnit(i++, i, 2, "fire thrower");
        GameMenuController.dropUnit(i++, i, 2, "archer");
        GameMenuController.dropUnit(i++, i, 2, "archer bow");
        GameMenuController.dropUnit(i++, i, 2, "swordsman");
        GameMenuController.dropUnit(i++, i, 2, "knight");
        GameMenuController.dropUnit(i++, i, 2, "black monk");
        GameMenuController.dropUnit(i++, i, 2, "pikeman");
        GameMenuController.dropUnit(i++, i, 2, "crossbowman");
        GameMenuController.dropUnit(i++, i, 2, "crossbowman");
        GameMenuController.dropUnit(i++, i, 2, "assassin");
        GameMenuController.dropUnit(i++, i, 2, "maceman");
        GameMenuController.dropUnit(i++, i, 2, "spearman");
        GameMenuController.dropUnit(i++, i, 2, "arabian swordsman");
        GameMenuController.dropUnit(39, 44, 2, "horse archer");
        GameMenuController.dropUnit(41, 44, 2, "horse archer");
        GameMenuController.dropUnit(42, 44, 2, "horse archer");
        GameMenuController.dropUnit(43, 44, 2, "horse archer");
    }

    private void setTraversable() {
        for (Node child : sidePane.getChildren())
            child.setFocusTraversable(false);
        mapPane.requestFocus();
    }

    // ---------------------------------- Controller-kind Methods ---------------------------------

    public void showMap() {
        mapPane.getChildren().clear();
        int rowsCount = mapPaneHeight / tileSize;
        int columnCount = mapPaneWidth / tileSize;
        Tile[][] mapTiles = ShowMapMenuController.getTiles(firstTileXInMap, firstTileYInMap, rowsCount, columnCount);

        setTextureTreeImages(mapTiles);
        setBuildingUnitImages(mapTiles);
        sidePane.toFront();
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
                if (BuildingUtils.isBuildingInTile(tile.getBuilding()))
                    setTileBuildingImage(tile.getBuilding(), xCoordinate, yCoordinate, tile.getBuilding().getSize(),
                            tile.getBuilding().getXCoordinate(), tile.getBuilding().getYCoordinate());
                if (tile.getLastUnitInTile() != null)
                    setTileImage(tile.getLastUnitInTile().getImage(), xCoordinate, yCoordinate);
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

    private void setTileBuildingImage(Building building, int xCoordinate, int yCoordinate, int buildingSize, int buildingX, int buildingY) {
        if (!(buildingY != firstTileYInMap + (yCoordinate / tileSize) || buildingX != firstTileXInMap + (xCoordinate / tileSize))) {
            Image image = building.getImage();
            ImageView imageView = new ImageView(image);
            imageView.setLayoutX(xCoordinate);
            imageView.setLayoutY(yCoordinate);
            imageView.setFitWidth(tileSize * buildingSize);
            imageView.setFitHeight(tileSize * buildingSize);
            imageView.setPreserveRatio(false);
            mapPane.getChildren().add(imageView);
            if (building.isSick()) {
                Image image1 = new Image(this.getClass().getResource("/IMG/sickness.png").toExternalForm());
                ImageView imageView1 = new ImageView(image1);
                imageView1.setLayoutX(xCoordinate);
                imageView1.setLayoutY(yCoordinate);
                imageView1.setFitWidth(tileSize * buildingSize);
                imageView1.setFitHeight(tileSize * buildingSize);
                imageView1.setOpacity(0.4);
                mapPane.getChildren().add(imageView1);
            }
        }
        if (building.isFiring()) {
            Image image = new Image(this.getClass().getResource("/IMG/fire.jpg").toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setLayoutX(xCoordinate);
            imageView.setLayoutY(yCoordinate);
            imageView.setFitWidth(tileSize);
            imageView.setFitHeight(tileSize);
            mapPane.getChildren().add(imageView);
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
                if (tile.hasBuilding()) {
                    if (tile.getBuilding().getOwner().equals(Stronghold.getCurrentGame().getCurrentGovernance())) {
                        selectBuildingTiles(tile);
                    }
                    //TODO: debug needed in if
                } else {
                    buildingsPane.setVisible(true);
                    buildingNameLabel.setVisible(false);
                    selectBuildingPane.setVisible(false);
                }
            }
            showMap();
        }
    }

    private void selectBuildingTiles(Tile tile) {
        prepareSelectBuildingMenu(tile);
        if (SelectBuildingMenuController.isShop(tile.getBuilding())) {
            try {
                new MarketMenu().start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (SelectBuildingMenuController.isKeep(tile.getBuilding())) showGovernanceInformation();
        int buildingSize = tile.getBuilding().getSize();
        int buildingX = tile.getBuilding().getXCoordinate();
        int buildingY = tile.getBuilding().getYCoordinate();
        selectedTileXInScreen = buildingX - firstTileXInMap;
        selectedTileYInScreen = buildingY - firstTileYInMap;
        selectedTile = ShowMapMenuController.getSelectedTile(selectedTileXInScreen, selectedTileYInScreen, firstTileXInMap, firstTileYInMap);
        pressedTileXInScreen = buildingX - firstTileXInMap + buildingSize - 1;
        pressedTileYInScreen = buildingY - firstTileYInMap + buildingSize - 1;
        selectMultipleTiles(0, 0);
    }

    private void showGovernanceInformation() {
        Governance governance = Stronghold.getCurrentGame().getCurrentGovernance();
        showGovernanceInformationPane();
        setGovernanceInformationLabels(governance);
    }

    private void setGovernanceInformationLabels(Governance governance) {
        governanceGold.setText("gold=" + governance.getGold());
        governancePopularity.setText("popularity=" + governance.getPopularity());
        governancePopulation.setText(governance.getUnemployedPopulation() + "\\" + governance.getMaxPopulation());
        if (governance.getPopularity() >= 50) governancePopularity.setTextFill(Color.GREEN);
        else governancePopularity.setTextFill(Color.RED);
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

    public void checkShortcut(KeyEvent keyEvent) throws Exception {
        KeyCode keyCode = keyEvent.getCode();

        switch (keyCode) {
            case ADD -> zoom(true);
            case SUBTRACT -> zoom(false);
            case M -> checkMoveUnit();
            case C -> copyBuilding();
            case V -> pasteBuilding();
            case S -> checkSetUnitState();
            case ESCAPE -> stopGame();
            case B -> clipBoard();
        }
    }

    private void clipBoard() {
        clipBoardPane.setVisible(!clipBoardPane.isVisible());
    }

    private void zoom(boolean zoomIn) {
        if (currentZoom.getLevel() < 4 && zoomIn) {
            currentZoom = Zoom.getZoomByLevel(currentZoom.getLevel() + 1);
            tileSize = currentZoom.getSize();
        } else if (currentZoom.getLevel() > 0 && !zoomIn && (mapPaneWidth / Zoom.getZoomByLevel(currentZoom.getLevel() - 1).getSize()) < mapSize) {
            currentZoom = Zoom.getZoomByLevel(currentZoom.getLevel() - 1);
            tileSize = currentZoom.getSize();
        } else Toolkit.getDefaultToolkit().beep();

        showMap();
    }

    private void checkMoveUnit() throws Exception {
        if (SelectUnitMenuController.hasUnit(selectedTiles)) new MoveUnit().start(new Stage());
    }

    private void pasteBuilding() {
        if (copiedBuildingName != null && !copiedBuildingName.equals("keep")) {
            buildingDragName = copiedBuildingName;
            buildingDragX = pressedTileXInScreen * tileSize;
            buildingDragY = pressedTileYInScreen * tileSize;
            buildingDragDone();
        }
    }

    private void copyBuilding() {
        try {
            copiedBuildingName = selectedTile.getBuilding().getName();
            fillClipBoard();
        } catch (Exception e) {
            copiedBuildingName = null;
        }
    }

    private void fillClipBoard() {
        if (!clipBoard.contains(copiedBuildingName) || copiedBuildingName.equals("keep")) {
            clipBoard.add(copiedBuildingName);
            ImageView buildingImage = new ImageView(BuildingUtils.getBuildingByType(copiedBuildingName).getImage());
            buildingImage.setFitHeight(60);
            buildingImage.setFitWidth(60);
            buildingImage.setPreserveRatio(true);
            buildingImage.setId(copiedBuildingName);
            buildingImage.setOnMouseClicked(this::clipBoardMouseClick);
            clipBoardTilePane.getChildren().add(buildingImage);
        }
        if (clipBoard.size() > 9) {
            clipBoard.remove(0);
            clipBoardTilePane.getChildren().remove(0);
        }
    }

    private void clipBoardMouseClick(MouseEvent mouseEvent) {
        copiedBuildingName = ((ImageView) mouseEvent.getSource()).getId();
        clipBoardPane.setVisible(false);
    }

    private void checkSetUnitState() throws Exception {
        if (SelectUnitMenuController.hasUnit(selectedTiles)) new SetUnitState().start(new Stage());
    }

    private void stopGame() {
        //TODO: implement
    }

    @FXML
    private void hover(MouseEvent mouseEvent) {
        initializeToolTip();
        int mouseX = Math.floorDiv((int) mouseEvent.getX(), tileSize);
        int mouseY = Math.floorDiv((int) mouseEvent.getY(), tileSize);
        Tile hoveredTile = ShowMapMenuController.getSelectedTile(mouseX, mouseY, firstTileXInMap, firstTileYInMap);
        if (selectedTiles.contains(hoveredTile)) tooltip.setText(ShowMapMenuController.getTilesData(selectedTiles));
        else tooltip.setText(hoveredTile.toString());
    }

    private void prepareSelectBuildingMenu(Tile tile) {
        selectBuildingImageView.setImage(tile.getBuilding().getImage());
        selectBuildingLabel.setText(tile.getBuilding().getName());
        buildingCommandPane.setVisible(SelectBuildingMenuController.hasCommand(tile.getBuilding()));
        if (SelectBuildingMenuController.isUnitMaker(tile.getBuilding())) {
            unitPane.setVisible(true);
            fillUnitBox(tile);
        } else {
            unitLabel.setText("");
            unitPane.setVisible(false);
        }
        selectBuildingPane.setVisible(true);
        buildingsPane.setVisible(false);
    }

    private void fillUnitBox(Tile tile) {
        unitPane.getChildren().clear();
        ArrayList<File> UnitImages = new ArrayList<>();
        try {
            File[] array;
            array = new File(GameMenu.class.getResource("/IMG/Units").toURI()).listFiles();
            UnitImages.addAll(List.of(array));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        for (File UnitImage : UnitImages) {
            ImageView UnitImageView = new ImageView(UnitImage.getPath());
            String unitType = UnitImage.getName().substring(0, UnitImage.getName().length() - 4);
            if (!SelectBuildingMenuController.isUnitMakerSuitable(unitType, tile.getBuilding()))
                continue;
            UnitImageView.setFitHeight(60);
            UnitImageView.setFitWidth(60);
            UnitImageView.setPreserveRatio(true);
            UnitImageView.setId(unitType);
            UnitImageView.setOnMouseClicked(this::UnitMouseClick);
            unitPane.getChildren().add(UnitImageView);
        }
    }

    private void UnitMouseClick(MouseEvent mouseEvent) {
        //TODO: how to create machines?
        String unitType = ((ImageView) mouseEvent.getSource()).getId();
        unitLabel.setText(unitType + "\n(double click to create)");
        if (mouseEvent.getClickCount() == 2) {
            switch (SelectBuildingMenuController.checkCreateUnit(selectedTile.getBuilding(), unitType)) {
                case NOT_ENOUGH_POPULATION ->
                        ViewUtils.alert(Alert.AlertType.ERROR, "Create unit error", "You don't have enough population!");
                case NOT_ENOUGH_GOLD ->
                        ViewUtils.alert(Alert.AlertType.ERROR, "Create unit error", "You don't have enough gold!");
                case NOT_ENOUGH_RESOURCE ->
                        ViewUtils.alert(Alert.AlertType.ERROR, "Create unit error", "You don't have enough resource!");
                case BAD_UNIT_MAKER_PLACE ->
                        ViewUtils.alert(Alert.AlertType.ERROR, "Create unit error", "Bad unitMaker place!!!");
                case SUCCESS -> showMap();
            }
        }
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
        initializeBuildingBox(warBox, "Tower", "Storage", "UnitMaker", "GateHouse");
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
        buildingNameLabel.setVisible(true);
        buildingNameLabel.setText(BuildingUtils.getBuildingByType(((ImageView)mouseEvent.getSource()).getId()).getPublicDetails());
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
        buildingNameLabel.setVisible(true);
        buildingNameLabel.setText(BuildingUtils.getBuildingByType(((ImageView)mouseEvent.getSource()).getId()).getPublicDetails());
    }

    private void changePaneVisibility(Pane pane, Pane... panes) {
        pane.setVisible(true);
        for (Pane pane1 : panes) pane1.setVisible(false);
    }

    public void showProductiveBox() {
        changePaneVisibility(productiveBox, warBox, governanceBox, governanceInformationPane);
    }

    public void showWarBox() {
        changePaneVisibility(warBox, productiveBox, governanceBox, governanceInformationPane);
    }

    public void showGovernanceBox() {
        changePaneVisibility(governanceBox, warBox, productiveBox, governanceInformationPane);
    }

    public void showGovernanceInformationPane() {
        changePaneVisibility(governanceInformationPane, governanceBox, warBox, productiveBox);
    }


    public void buildingDragOver(DragEvent dragEvent) {
        buildingDragX = dragEvent.getSceneX();
        buildingDragY = dragEvent.getSceneY();
    }

    public void buildingDragDone() {
        int x = Math.floorDiv((int) buildingDragX, tileSize) + firstTileXInMap;
        int y = Math.floorDiv((int) buildingDragY, tileSize) + firstTileYInMap;
        if (buildingDragX > mapPaneWidth || buildingDragX < 0 || buildingDragY > mapPaneHeight || buildingDragY < 0)
            return;
        switch (GameMenuController.checkDropBuilding(x, y, buildingDragName)) {
            case CANT_BUILD_HERE -> ViewUtils.alert(Alert.AlertType.ERROR, "Build Error", "Can't build here!");
            case NOT_ENOUGH_MONEY ->
                    ViewUtils.alert(Alert.AlertType.ERROR, "Build Error", "You don't have enough money!");
            case NOT_ENOUGH_RESOURCE ->
                    ViewUtils.alert(Alert.AlertType.ERROR, "Build Error", "You don't have enough resource!");
            case NOT_ENOUGH_POPULATION ->
                    ViewUtils.alert(Alert.AlertType.ERROR, "Build Error", "You don't have enough population!");
            case SUCCESS -> {
                selectedTile = ShowMapMenuController.getSelectedTile(x, y, 0, 0);
                setTileBuildingImage(BuildingUtils.getBuildingByType(buildingDragName)
                        , (x - firstTileXInMap) * tileSize, (y - firstTileYInMap) * tileSize,
                        BuildingUtils.getBuildingByType(buildingDragName).getSize(), x, y);
                selectBuildingTiles(selectedTile);
            }
        }
    }

    public ArrayList<Tile> getSelectedTiles() {
        return selectedTiles;
    }

    public void nextTurn() {
        ViewUtils.alert(Alert.AlertType.INFORMATION, "Next Turn Successful", GameMenuController.nextTurn());
        showMap();
    }

    public void selectDestinationTile(MoveUnit moveUnit) {
        mapPane.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                try {
                    moveUnit.checkAction(firstTileXInMap + pressedTileXInScreen,
                            firstTileYInMap + pressedTileYInScreen);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            mouseEvent.consume();
        });
    }

    public void checkRepair() {
        switch (SelectBuildingMenuController.checkRepair(selectedTile.getBuilding())) {
            case NO_NEED_TO_REPAIR -> ViewUtils.alert(Alert.AlertType.ERROR, "Repair error", "No need to repair!");
            case ENEMY_AROUND -> ViewUtils.alert(Alert.AlertType.ERROR, "Repair error", "There's enemy around!");
            case NOT_ENOUGH_RESOURCE ->
                    ViewUtils.alert(Alert.AlertType.ERROR, "Repair error", "Not enough resource to repair");
            case SUCCESS -> ViewUtils.alert(Alert.AlertType.INFORMATION, "Repair successful", "Successfully repaired!");
        }
    }

    public void showPopularityFactors() throws Exception {
        if (governanceInformationPane.isVisible()) new PopularityFactors().start(new Stage());
    }
}
