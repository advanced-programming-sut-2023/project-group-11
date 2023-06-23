package view.animation;

import controller.GameMenuController;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import view.GameMenu;

public class AttackAnimation extends Transition {
    private final AnchorPane mapPane;
    private final ImageView arrow = new ImageView(new Image(System.getProperty("user.dir") + "/src/main/resources/IMG/arrow.png"));
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
        arrow.setFitWidth(tileSize);
        arrow.setFitHeight(tileSize);
        mapPane.getChildren().add(arrow);
        setCycleDuration(Duration.seconds(1));
        setCycleCount(INDEFINITE);
        play();
    }

    @Override
    protected void interpolate(double v) {
        double currentX = currentLocation[0];
        double currentY = currentLocation[1];
        double destinationX = currentX + 0.5 * xIncrement;
        double destinationY = currentY + 0.5 * yIncrement;
        arrow.setLayoutX(destinationX);
        arrow.setLayoutY(destinationY);
        currentLocation[0] = destinationX;
        currentLocation[1] = destinationY;
        if (GameMenuController.hasReachedDestination(currentLocation, destinationLocation)) {
            mapPane.getChildren().remove(arrow);
            stop();
        }
    }
}