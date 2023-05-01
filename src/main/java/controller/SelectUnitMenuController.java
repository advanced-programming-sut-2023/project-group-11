package controller;

import model.Path;
import model.Stronghold;
import model.buildings.Climbable;
import model.map.Map;
import model.map.Texture;
import model.map.Tile;
import model.people.Engineer;
import model.people.Troops;
import model.people.Units;
import view.enums.messages.SelectUnitMenuMessages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;

public class SelectUnitMenuController {
    public static SelectUnitMenuMessages checkMoveUnit(Matcher matcher, int[] currentLocation) {
        if (!Utils.isValidCommandTags(matcher, "xGroup", "yGroup")) return SelectUnitMenuMessages.INVALID_COMMAND;

        Map map = Stronghold.getCurrentGame().getMap();
        int destinationX = Integer.parseInt(matcher.group("xCoordinate"));
        int destinationY = Integer.parseInt(matcher.group("yCoordinate"));
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];

        if (!Utils.isValidCoordinates(map, destinationX, destinationY))
            return SelectUnitMenuMessages.INVALID_COORDINATE;
        else if (notValidTextureForMoving(map.getTile(destinationX, destinationY)))
            return SelectUnitMenuMessages.INVALID_DESTINATION_TEXTURE;
        else if (notValidDestinationDifferentUnits(map.getTile(currentX, currentY), map.getTile(destinationX, destinationY)))
            return SelectUnitMenuMessages.INVALID_DESTINATION_DIFFERENT_UNIT;
        else if (BuildingUtils.isBuildingInTile(map.getTile(destinationX, destinationY).getBuilding()) && map.getTile(destinationX, destinationY).getBuilding() instanceof Climbable && !((Climbable) map.getTile(destinationX, destinationY).getBuilding()).isClimbable())
            return SelectUnitMenuMessages.INVALID_DESTINATION_ONCLIMABLE_BUILDING;
        else if (findRootToDestination(map, currentX, currentY, destinationX, destinationY) == null)
            return SelectUnitMenuMessages.INVALID_DISTANCE;



        currentLocation[0] = destinationX;
        currentLocation[1] = destinationY;
        return SelectUnitMenuMessages.SUCCESS;
    }

    private static SelectUnitMenuMessages checkPatrolUnit(Matcher matcher) {
        return null;
    }

    private static SelectUnitMenuMessages checkSetUnitState(Matcher matcher) {
        return null;
    }

    private static SelectUnitMenuMessages checkAttackUnit(Matcher matcher) {
        return null;
    }

    private static SelectUnitMenuMessages checkPourOil(Matcher matcher) {
        return null;
    }

    private static SelectUnitMenuMessages checkDigTunnel(Matcher matcher) {
        return null;
    }

    private static SelectUnitMenuMessages checkBuildMachine(Matcher matcher) {
        return null;
    }

    private static SelectUnitMenuMessages disbandUnit() {
        return null;
    }

    private static Path findRootToDestination(Map map, int currentX, int currentY, int destinationX, int destinationY) {
        int speed = map.getTile(currentX, currentY).getUnits().get(0).getSpeed();
        ArrayList<Path> paths = new ArrayList<>();

        pathDFS(map, currentX, currentY, destinationX, destinationY, speed, paths, new Path());

        if (paths.size() == 0) return null;
        else return shortestPath(paths);
    }

    private static void pathDFS(Map map, int currentX, int currentY, int destinationX, int destinationY, int speed, ArrayList<Path> paths, Path path) {
        int[] currentLocation = {currentX, currentY};
        int[] destinationLocation = {destinationX, destinationY};
        int leftMoves = speed - path.getLength();

        if (!Utils.isValidCoordinates(map, currentX, currentY)) return;
        else if (!Arrays.equals(currentLocation, destinationLocation) && leftMoves == 0) return;
        else if (path.getPath().contains(currentLocation)) return;
        else if (notValidTextureForMoving(map.getTile(currentX, currentY))) return;
        else if (!(map.getTile(currentX, currentY).getBuilding() instanceof Climbable)) return;
            //TODO: climbable building -> unit & environment situations
//        else if ( );


        path.addLocationToPath(currentLocation);
        if (Arrays.equals(currentLocation, destinationLocation)) {
            paths.add(path);
        } else {
            pathDFS(map, currentX + 1, currentY, destinationX, destinationY, speed, paths, path);
            pathDFS(map, currentX - 1, currentY, destinationX, destinationY, speed, paths, path);
            pathDFS(map, currentX, currentY + 1, destinationX, destinationY, speed, paths, path);
            pathDFS(map, currentX, currentY - 1, destinationX, destinationY, speed, paths, path);
        }
        path.removePath(path);
    }

    private static Path shortestPath(ArrayList<Path> paths) {
        int length = 6 + 1;
        Path shortestPath = new Path();
        for (Path path : paths) {
            if (path.getLength() < length) {
                shortestPath = path;
                length = path.getLength();
            }
        }
        return shortestPath;
    }

    private static boolean notValidTextureForMoving(Tile destination) {
        return destination.getTexture().equals(Texture.MARSH) || destination.getTexture().equals(Texture.RIVER) ||
                destination.getTexture().equals(Texture.SEA) || destination.getTexture().equals(Texture.SMALL_LAKE) ||
                destination.getTexture().equals(Texture.BIG_LAKE) || destination.getTexture().equals(Texture.CLIFF);
    }

    private static boolean notValidDestinationDifferentUnits(Tile currentTile, Tile destination) {
        for (Units unit : destination.getUnits()) {
            if (!unit.getOwnerGovernance().equals(Stronghold.getCurrentGame().getCurrentGovernance())) return true;
            if (unit instanceof Engineer && !(currentTile.getUnits().get(0) instanceof Engineer)) return true;
            else if (!((Troops) unit).getType().equals(((Troops) currentTile.getUnits().get(0)).getType())) return true;
        }
        return false;
    }

    private static void attackMachine() {

    }
}
