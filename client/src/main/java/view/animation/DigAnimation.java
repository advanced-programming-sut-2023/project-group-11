package view.animation;

import controller.ShowMapMenuController;
import controller.Utils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import model.Path;
import model.map.Texture;
import model.people.Unit;

import java.util.ArrayList;

public class DigAnimation {
    private final Path shortestPath;
    private Timeline timeline;
    private int move = 0;
    private final Texture texture;
    private final boolean isDigPitch;

    public DigAnimation(ArrayList<Unit> units, Path shortestPath, Texture texture) {
        Utils.getGameMenu().showMap(false);
        this.shortestPath = shortestPath;
        this.texture = texture;
        isDigPitch = texture.equals(Texture.PITCH);
        double MOVE_TIME;

        if (isDigPitch) MOVE_TIME = 10.0 / (units.get(0).getSpeed() * units.size());
        else MOVE_TIME = 1.5;
        initializeTimeline(MOVE_TIME);
        if (isDigPitch) new MovingAnimation(units, shortestPath, MOVE_TIME);
    }

    private void initializeTimeline(double moveTime) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(moveTime), actionEvent -> this.start()));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void start() {
        int[] currentLocation;
        if (isDigPitch) currentLocation = shortestPath.getPath().get(move++);
        else currentLocation = shortestPath.getPath().get(++move);
        ShowMapMenuController.getCurrentMap().getTile(currentLocation).setTexture(texture);
        if (!isDigPitch)
            if (ShowMapMenuController.getCurrentMap().getTile(currentLocation).getBuilding() != null) {
                ShowMapMenuController.getCurrentMap().getTile(currentLocation).getBuilding().removeFromGame();
                Utils.getGameMenu().showMap(false);
            }
        if (move >= shortestPath.getLength() - 1) timeline.stop();
    }
}
