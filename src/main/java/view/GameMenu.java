package view;

import controller.ShowMapMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.map.Tile;

import java.net.URL;

public class GameMenu extends Application {
    @FXML
    private AnchorPane mapPane;
    private final int mapPaneHeight = 720;
    private final int mapPaneWidth = 990;
    private int tileSize = 30;
    private int mapSize;
    private int firstTileX = 0;
    private int firstTileY = 0;
    private int selectedTileX = 0;
    private int selectedTileY = 0;
    private Tile selectedTile;

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
        showMap();
        mapSize = ShowMapMenuController.getCurrentMap().getSize();
    }

    // ---------------------------------- Getter/Setter -------------------------------------------

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    // ---------------------------------- Controller-kind Methods ---------------------------------

    private void showMap() {
        double time = System.currentTimeMillis();

        mapPane.getChildren().clear();
        int rowsCount = mapPaneHeight / tileSize;
        int columnCount = mapPaneWidth / tileSize;
        Tile[][] mapTiles = ShowMapMenuController.getTiles(firstTileX, firstTileY, rowsCount, columnCount);

        setTextureTreeImages(mapTiles);
        setBuidingUnitImages(mapTiles);

        System.out.println((System.currentTimeMillis() - time) / 1000);
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

    private void setBuidingUnitImages(Tile[][] mapTiles) {
        int xCoordinate = 0, yCoordinate = 0;

        for (Tile[] tiles : mapTiles) {
            for (Tile tile : tiles) {
                //TODO: needs debug for buildings with size more than 1
                if (tile.getBuilding() != null)
                    setTileBuildingImage(tile.getBuilding().getImage(), xCoordinate, yCoordinate, tile.getBuilding().getSize(),
                            tile.getBuilding().getXCoordinate(), tile.getBuilding().getYCoordinate());
                if (tile.getUnits().size() != 0)
                    setTileImage(tile.getUnits().get(0).getImage(), xCoordinate, yCoordinate);
                if (tile.equals(selectedTile)) boldSelectedTile(xCoordinate, yCoordinate);
                xCoordinate += tileSize;
            }
            yCoordinate += tileSize;
            xCoordinate = 0;
        }
    }

    private void boldSelectedTile(int xCoordinate, int yCoordinate) {
        Rectangle border = new Rectangle(xCoordinate, yCoordinate, tileSize, tileSize);
        border.setStroke(Color.RED);
        border.setStrokeWidth(2);
        border.setFill(null);
        mapPane.getChildren().add(border);
    }

    private void setTileBuildingImage(Image image, int xCoordinate, int yCoordinate, int buildingSize, int buildingX, int buildingY) {
        if (buildingY != firstTileX + (xCoordinate / tileSize) || buildingX != firstTileY + (yCoordinate / tileSize)) return; //TODO: Be careful about inverse x & y
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

    public void moveMapMove(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            int endTileX = Math.floorDiv((int) mouseEvent.getX(), tileSize);
            int endTileY = Math.floorDiv((int) mouseEvent.getY(), tileSize);
            int deltaX = endTileX - selectedTileX;
            int deltaY = endTileY - selectedTileY;
            if (deltaX == 0 && deltaY == 0) return;

            if (firstTileX - deltaX >= 0 && firstTileX - deltaX < mapSize - (mapPaneWidth / tileSize))
                firstTileX -= deltaX;
            if (firstTileY - deltaY >= 0 && firstTileY - deltaY < mapSize - (mapPaneHeight / tileSize))
                firstTileY -= deltaY;

            showMap();

            selectedTileX = endTileX;
            selectedTileY = endTileY;
        }
    }

    public void setSelectedTile(MouseEvent mouseEvent) {//TODO: for clicking
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            selectedTileX = Math.floorDiv((int) mouseEvent.getX(), tileSize);
            selectedTileY = Math.floorDiv((int) mouseEvent.getY(), tileSize);
            Tile tile = ShowMapMenuController.getSelectedTile(selectedTileX, selectedTileY, firstTileX, firstTileY);
            if (tile.equals(selectedTile)) selectedTile = null;
            else selectedTile = tile;
            showMap();
        }
    }

    public void setStartCoordinates(MouseEvent mouseEvent) { //TODO: for pressing
        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            selectedTileX = Math.floorDiv((int) mouseEvent.getX(), tileSize);
            selectedTileY = Math.floorDiv((int) mouseEvent.getY(), tileSize);
        }
    }
}
