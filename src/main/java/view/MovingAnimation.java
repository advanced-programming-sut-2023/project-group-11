package view;

import controller.SelectUnitMenuController;
import controller.ShowMapMenuController;
import controller.Utils;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import model.Path;
import model.people.Unit;

import java.util.ArrayList;

public class MovingAnimation {
    private final GameMenu gameMenu;
    private final ArrayList<Unit> units;
    private final Path shortestPath;
    private Timeline timeline;
    private int move = 0;


    public MovingAnimation(ArrayList<Unit> units, Path shortestPath) {
        this.gameMenu = Utils.getGameMenu();
        this.units = units;
        this.shortestPath = shortestPath;
        initializeTimeline(units);
    }

    private void initializeTimeline(ArrayList<Unit> units) {
        timeline = new Timeline(new KeyFrame(
                Duration.millis(2000.0 / units.get(0).getSpeed()), actionEvent -> this.start()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void start() {
        int[] currentLocation = shortestPath.getPath().get(move);
        int[] nextLocation = shortestPath.getPath().get(move + 1);

        for (Unit unit : units) unit.setLocation(nextLocation);

        ShowMapMenuController.getCurrentMap().getTile(currentLocation).clearUnitsByType(units);
        ShowMapMenuController.getCurrentMap().getTile(nextLocation).getUnits().addAll(units);
        SelectUnitMenuController.applyPathEffects(nextLocation, units);

        gameMenu.showMap();

        move++;
        if (move >= shortestPath.getLength() - 1) timeline.stop();
    }
}
