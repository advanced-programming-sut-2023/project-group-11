//package view.animation;
//
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.AnchorPane;
//import javafx.util.Duration;
//import view.GameMenu;
//import view.ViewUtils;
//import webConnection.Client;
//import webConnection.Connection;
//
//import java.io.IOException;
//
//public class GroundAttackAnimation {
//    private final AnchorPane mapPane;
//    private final ImageView attackBanner = new ImageView(new Image(getClass().getResource("/IMG/attack banner.png").toExternalForm()));
//    private final ImageView boom = new ImageView(new Image(getClass().getResource("/IMG/boom.png").toExternalForm()));
//    private final double[] currentLocation;
//    private final double[] destinationLocation;
//
//    private final GameMenu gameMenu;
//    private Connection connection = Client.getConnection();
//    private String gameMenuController = "GameMenuController";
//
//    public GroundAttackAnimation(GameMenu gameMenu, double[] currentLocation, double[] destinationLocation) throws IOException {
//        this.gameMenu = gameMenu;
//        this.mapPane = gameMenu.getMapPane();
//        this.currentLocation = (double[]) connection.getData(gameMenuController, "getCoordinateWithTile", currentLocation, gameMenu.getTileSize(), gameMenu.getFirstTileXInMap(), gameMenu.getFirstTileYInMap());//TODO: may produce error
//        this.destinationLocation = (double[]) connection.getData(gameMenuController, "getCoordinateWithTile", destinationLocation, gameMenu.getTileSize(), gameMenu.getFirstTileXInMap(), gameMenu.getFirstTileYInMap());
//        int tileSize = gameMenu.getTileSize();
//
//        addImage(attackBanner, 262, 142, 360 + 131 / 2.0, 0);
//        addImage(boom, tileSize, tileSize, this.destinationLocation[0], this.destinationLocation[1]);
//        initializeTimeline();
//    }
//
//    private void initializeTimeline() {
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> this.start()));
//        timeline.play();
//    }
//
//    private void addImage(ImageView imageView, int width, int height, double layoutX, double layoutY) {
//        imageView.setFitWidth(width);
//        imageView.setFitHeight(height);
//        imageView.setLayoutX(layoutX);
//        imageView.setLayoutY(layoutY);
//        mapPane.getChildren().add(imageView);
//    }
//
//    protected void start() {
//        int tileSize = gameMenu.getTileSize();
//        attackBanner.toFront();
//        boom.toFront();
//        ViewUtils.getGameMenu().showMap(false);
//        mapPane.getChildren().removeAll(attackBanner);
//    }
//}
