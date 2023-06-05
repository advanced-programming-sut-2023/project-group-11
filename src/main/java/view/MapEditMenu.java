package view;

import controller.ShowMapMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.map.Tile;

import java.net.URL;

public class MapEditMenu extends Application {
    @FXML
    private AnchorPane mapPane;
    private int tileSize = 30;
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
        showMap(0, 0);
    }

    // ---------------------------------- Getter/Setter -------------------------------------------

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    // ---------------------------------- Controller-kind Methods ---------------------------------

    private void showMap(int xTile, int yTile) {
        int rowsCount = mapPaneHeight / tileSize;
        int columnCount = mapPaneWidth / tileSize;
        Tile[][] mapTiles = ShowMapMenuController.getTiles(xTile, yTile, rowsCount, columnCount);

        int xCoordinate = xTile * tileSize, yCoordinate = yTile * tileSize;

        for (Tile[] tiles : mapTiles) {
            for (Tile tile : tiles) {
                ImageView imageView = new ImageView(tile.getTexture().getImage());
                imageView.setLayoutX(xCoordinate);
                imageView.setLayoutY(yCoordinate);
                mapPane.getChildren().add(imageView);
                xCoordinate += tileSize;
            }
            yCoordinate += tileSize;
        }
    }

    public void back() throws Exception {
        new MainMenu().start(SignupMenu.getStage());
    }
}
