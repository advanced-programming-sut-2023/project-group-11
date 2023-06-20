package view.animation;

import javafx.animation.Timeline;
import javafx.animation.Transition;
import model.Path;
import model.people.Unit;
import view.GameMenu;

import java.util.ArrayList;

public class AttackAnimation extends Transition {
    private final GameMenu gameMenu;
    private final ArrayList<Unit> units;
    private final Path shortestPath;
    private final double MOVE_TIME;
    private Timeline timeline;
    private int move = 0;

    public AttackAnimation(GameMenu gameMenu, ArrayList<Unit> units, Path shortestPath) {
        this.gameMenu = gameMenu;
        this.units = units;
        this.shortestPath = shortestPath;
        this.MOVE_TIME = 0.4;
    }

    @Override
    protected void interpolate(double v) {

    }
}
