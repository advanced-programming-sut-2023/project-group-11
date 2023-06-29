package view.animation;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import view.GameMenu;
import view.ViewUtils;
import webConnection.Connection;

import java.io.IOException;

public class AirAttackAnimation extends Transition {
    private final AnchorPane mapPane;
    private final ImageView arrow = new ImageView(new Image(getClass().getResource("/IMG/arrow.png").toExternalForm()));
    private final ImageView attackBanner = new ImageView(new Image(getClass().getResource("/IMG/attack banner.png").toExternalForm()));
    private final double[] currentLocation;
    private final double[] destinationLocation;
    private final double xIncrement;
    private final double yIncrement;
    private Connection connection;
    private String gameMenuController = "GameMenuController";

    public AirAttackAnimation(GameMenu gameMenu, double[] currentLocation, double[] destinationLocation) throws IOException {
        this.currentLocation = (double[]) connection.getData(gameMenuController, "getCoordinateWithTile", currentLocation);
        this.destinationLocation = (double[]) connection.getData(gameMenuController, "getCoordinateWithTile", destinationLocation);
        this.mapPane = gameMenu.getMapPane();
        this.arrow.setRotate((Double) connection.getData(gameMenuController, "getArrowAngle", currentLocation, destinationLocation));
        int tileSize = gameMenu.getTileSize();
        xIncrement = destinationLocation[0] - currentLocation[0];
        yIncrement = destinationLocation[1] - currentLocation[1];
        addImage(arrow, tileSize, tileSize);
        addImage(attackBanner, 262, 142);
        attackBanner.setLayoutX(360 + 131 / 2.0);
        attackBanner.setLayoutY(0);
        setCycleDuration(Duration.seconds(1));
        setCycleCount(INDEFINITE);
        play();
    }

    private void addImage(ImageView imageView, int width, int height) {
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        mapPane.getChildren().add(imageView);
    }

    @Override
    protected void interpolate(double v) {
        double currentX = currentLocation[0];
        double currentY = currentLocation[1];
        double distance;
        try {
            distance = (double) connection.getData(gameMenuController, "calculateDestination", xIncrement, yIncrement);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        double changingRate = 3 / distance;
        double destinationX = currentX + changingRate * xIncrement;
        double destinationY = currentY + changingRate * yIncrement;
        arrow.setLayoutX(destinationX);
        arrow.setLayoutY(destinationY);
        arrow.toFront();
        attackBanner.toFront();
        currentLocation[0] = destinationX;
        currentLocation[1] = destinationY;
        try {
            if ((Boolean) connection.getData(gameMenuController, "hasReachedDestination", currentLocation, destinationLocation)) {
                mapPane.getChildren().removeAll(arrow, attackBanner);
                ViewUtils.getGameMenu().showMap(false);
                stop();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}