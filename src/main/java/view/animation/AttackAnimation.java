package view.animation;

import controller.GameMenuController;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.people.Unit;
import view.GameMenu;

import java.util.ArrayList;

public class AttackAnimation extends Transition {
    private final GameMenu gameMenu;
    private final ArrayList<Unit> units;
    private final AnchorPane mapPane;
    private final ImageView arrow = new ImageView(new Image(System.getProperty("user.dir") + "/src/main/resources/IMG/arrow.png"));
    //            getClass().getResource("IMG/arrow.png").toExternalForm()));
    private double[] currentLocation;
    private final double[] destinationLocation;
    private final double xIncrement;
    private final double yIncrement;

    public AttackAnimation(GameMenu gameMenu, ArrayList<Unit> units, double[] currentLocation, double[] destinationLocation) {
        this.gameMenu = gameMenu;
        this.units = units;
        this.currentLocation = GameMenuController.getCoordinateWithTile(currentLocation);
        this.destinationLocation = GameMenuController.getCoordinateWithTile(destinationLocation);
        this.mapPane = gameMenu.getMapPane();
        System.out.println(GameMenuController.getArrowAngle(currentLocation,destinationLocation));
        this.arrow.setRotate(-GameMenuController.getArrowAngle(currentLocation,destinationLocation));
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
        if (currentLocation[0] > destinationLocation[0] || currentLocation[1] > destinationLocation[1]) {
            mapPane.getChildren().remove(arrow);
            stop();
        }
    }
}