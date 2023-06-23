package view.animation;

import controller.GameMenuController;
import controller.Utils;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import view.GameMenu;

public class AttackAnimation extends Transition {
    private final AnchorPane mapPane;
    private final ImageView arrow = new ImageView(new Image(getClass().getResource("/IMG/arrow.png").toExternalForm()));
    private final ImageView attackBanner = new ImageView(new Image(getClass().getResource("/IMG/attack banner.png").toExternalForm()));
    private final double[] currentLocation;
    private final double[] destinationLocation;
    private final double xIncrement;
    private final double yIncrement;

    public AttackAnimation(GameMenu gameMenu, double[] currentLocation, double[] destinationLocation) {
        this.currentLocation = GameMenuController.getCoordinateWithTile(currentLocation);
        this.destinationLocation = GameMenuController.getCoordinateWithTile(destinationLocation);
        this.mapPane = gameMenu.getMapPane();
        this.arrow.setRotate(GameMenuController.getArrowAngle(currentLocation, destinationLocation));
        int tileSize = gameMenu.getTileSize();
        xIncrement = destinationLocation[0] - currentLocation[0];
        yIncrement = destinationLocation[1] - currentLocation[1];
        addImage(arrow, tileSize, tileSize);
        addImage(attackBanner, 131, 71);
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
        double destinationX = currentX + 0.5 * xIncrement;
        double destinationY = currentY + 0.5 * yIncrement;
        arrow.setLayoutX(destinationX);
        arrow.setLayoutY(destinationY);
        arrow.toFront();
        attackBanner.toFront();
        currentLocation[0] = destinationX;
        currentLocation[1] = destinationY;
        if (GameMenuController.hasReachedDestination(currentLocation, destinationLocation)) {
            mapPane.getChildren().removeAll(arrow, attackBanner);
            Utils.getGameMenu().showMap(false);
            stop();
        }
    }
}