package view.animation;

import controller.SelectUnitMenuController;
import controller.ShowMapMenuController;
import controller.Utils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import model.Path;
import model.people.Unit;
import view.GameMenu;

import java.util.ArrayList;

public class MovingAnimation {
    private final GameMenu gameMenu;
    private final ArrayList<Unit> units;
    private final Path shortestPath;
    private final double MOVE_TIME;
    private Timeline timeline;
    private int move = 0;


    public MovingAnimation(ArrayList<Unit> units, Path shortestPath) {
        this.gameMenu = Utils.getGameMenu();
        this.units = units;
        this.shortestPath = shortestPath;
        this.MOVE_TIME = 2.0 / units.get(0).getSpeed();
        if (slowsDown(shortestPath.getPath().get(0))) initializeTimeline(MOVE_TIME * 2);
        else initializeTimeline(MOVE_TIME);
    }

    private void initializeTimeline(double moveTime) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(moveTime), actionEvent -> this.start()));
        timeline.play();
    }

    private void start() {
        int[] currentLocation = shortestPath.getPath().get(move);
        int[] nextLocation = shortestPath.getPath().get(move + 1);

        if (slowsDown(nextLocation)) initializeTimeline(MOVE_TIME * 2);
        else initializeTimeline(MOVE_TIME);

        for (Unit unit : units) unit.setLocation(nextLocation);

        ShowMapMenuController.getCurrentMap().getTile(currentLocation).clearUnitsByType(units);
        ShowMapMenuController.getCurrentMap().getTile(nextLocation).getUnits().addAll(units);
        SelectUnitMenuController.applyPathEffects(nextLocation, units);

        gameMenu.showMap();

        move++;
        if (move >= shortestPath.getLength() - 1) timeline.stop();
    }

    private static boolean slowsDown(int[] location) {
        return ShowMapMenuController.getCurrentMap().getTile(location).getTexture().slowsDown();
    }
}
