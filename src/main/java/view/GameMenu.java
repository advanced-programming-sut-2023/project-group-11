package view;

import controller.ShowMapMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.map.Tile;

import java.net.URL;

public class GameMenu extends Application {
    @FXML
    private AnchorPane mapPane;
    private int tileSize = 30;
    private int mapSize;
    private int xTile = 0;
    private int yTile = 0;
    private final int mapPaneHeight = 720;
    private final int mapPaneWidth = 990;

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
        Tile[][] mapTiles = ShowMapMenuController.getTiles(xTile, yTile, rowsCount, columnCount);

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
                if (tile.getBuilding() != null) {
                    //TODO: needs debug for buildings with size more than 1
                    setTileBuildingImage(tile.getBuilding().getImage(), xCoordinate, yCoordinate, tile.getBuilding().getSize(),
                            tile.getBuilding().getXCoordinate(), tile.getBuilding().getYCoordinate());
                }
                if (tile.getUnits().size() != 0) {
                    setTileImage(tile.getUnits().get(0).getImage(), xCoordinate, yCoordinate);
                }
                xCoordinate += tileSize;
            }
            yCoordinate += tileSize;
            xCoordinate = 0;
        }
    }

    private void setTileBuildingImage(Image image, int xCoordinate, int yCoordinate, int buildingSize, int buildingX, int buildingY) {
        if (buildingY != xTile + (xCoordinate / tileSize) || buildingX != yTile + (yCoordinate / tileSize)) return; //TODO: Be careful about inverse x & y
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(xCoordinate);
        imageView.setLayoutY(yCoordinate);
        imageView.setFitWidth(tileSize * buildingSize);
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

    public void moveMapClick(MouseEvent mouseEvent) throws InterruptedException {
        if (Math.abs(mouseEvent.getX() - mapPaneWidth) < tileSize) {
            if (mapSize - (mapPaneWidth / tileSize) > xTile) xTile += 1;
            showMap();
        } else if (Math.abs(mouseEvent.getY() - mapPaneHeight) < tileSize) {
            if (mapSize - (mapPaneHeight / tileSize) > yTile) yTile += 1;
            showMap();
        } else if (mouseEvent.getX() < tileSize) {
            if (xTile > 0) xTile -= 1;
            showMap();
        } else if (mouseEvent.getY() < tileSize) {
            if (yTile > 0) yTile -= 1;
            showMap();
        }
    }

    public void moveMapMove(MouseEvent mouseEvent) {

    }
}
