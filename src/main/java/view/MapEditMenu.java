package view;

import controller.ShowMapMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.map.Tile;

import java.net.URL;

public class MapEditMenu extends Application {
    @FXML
    private AnchorPane mapPane;
    private int tileSize = 30;
    private int xTile = 0;
    private int yTile = 0;
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
        showMap();
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

        int xCoordinate = 0, yCoordinate = 0;

        for (Tile[] tiles : mapTiles) {
            for (Tile tile : tiles) {
                ImageView texture = new ImageView(tile.getTexture().getImage());
                texture.setLayoutX(xCoordinate);
                texture.setLayoutY(yCoordinate);
                texture.setFitWidth(30);
                texture.setFitHeight(30);
                mapPane.getChildren().add(texture);
                if (tile.getTree() != null) {
                    ImageView tree = new ImageView((tile.getTree().getImage()));
                    tree.setLayoutX(xCoordinate);
                    tree.setLayoutY(yCoordinate);
                    tree.setFitWidth(30);
                    tree.setFitHeight(30);
                    mapPane.getChildren().add(tree);
                }
                xCoordinate += tileSize;
            }
            yCoordinate += tileSize;
            xCoordinate = 0;
        }
        System.out.println((System.currentTimeMillis() - time) / 1000);
    }

    public void back() throws Exception {
        new MainMenu().start(SignupMenu.getStage());
    }

    public void moveMapClick(MouseEvent mouseEvent) throws InterruptedException {
        if (Math.abs(mouseEvent.getX() - mapPaneWidth) < tileSize) {
            if (mapPaneWidth / tileSize > xTile + 1) xTile += 1;
            showMap();
        } else if (Math.abs(mouseEvent.getY() - mapPaneHeight) < tileSize) {
            if (mapPaneHeight / tileSize > yTile + 1) yTile += 1;
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
