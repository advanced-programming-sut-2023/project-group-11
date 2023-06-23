package view.animation;

import controller.ShowMapMenuController;
import controller.Utils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import model.Path;
import model.map.Texture;
import model.people.Unit;
import view.GameMenu;

import java.util.ArrayList;

public class DigAnimation {
    private final Path shortestPath;
    private Timeline timeline;
    private int move = 0;

    public DigAnimation(ArrayList<Unit> units, Path shortestPath) {
        this.shortestPath = shortestPath;
        double MOVE_TIME = 10.0 / (units.get(0).getSpeed() * units.size());
        initializeTimeline(MOVE_TIME);
        new MovingAnimation(units, shortestPath, MOVE_TIME);
    }

    private void initializeTimeline(double moveTime) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(moveTime), actionEvent -> this.start()));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void start() {
        int[] currentLocation = shortestPath.getPath().get(move);
        ShowMapMenuController.getCurrentMap().getTile(currentLocation).setTexture(Texture.PITCH);
        move++;

        if (move >= shortestPath.getLength() - 1) timeline.stop();
    }
}
