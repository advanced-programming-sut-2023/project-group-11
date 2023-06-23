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
import java.util.Arrays;

public class DigAnimation {
    private final GameMenu gameMenu = Utils.getGameMenu();
    private final ArrayList<Unit> units;
    private final Path shortestPath;
    private final double MOVE_TIME;
    private Timeline timeline;
    private MovingAnimation movingAnimation;
    private int move = 0;

    public DigAnimation(ArrayList<Unit> units, Path shortestPath) {
        this.units = units;
        this.shortestPath = shortestPath;
        movingAnimation = new MovingAnimation(units, shortestPath);
        this.MOVE_TIME = 2.0 / units.get(0).getSpeed();
        initializeTimeline(MOVE_TIME);
    }

    private void initializeTimeline(double moveTime) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(moveTime), actionEvent -> this.start()));
        timeline.play();
    }

    private void start() {
        int[] currentLocation = shortestPath.getPath().get(move);
        ShowMapMenuController.getCurrentMap().getTile(currentLocation).setTexture(Texture.PITCH);
        move++;
        System.out.println(shortestPath.getPath());
        System.out.println(Arrays.toString(currentLocation));
        System.out.println(move);

        if (move >= shortestPath.getLength() - 1) timeline.stop();
    }
}
