package controller;

import model.Governance;
import model.Path;
import model.Stronghold;
import model.buildings.Building;
import model.buildings.Climbable;
import model.buildings.GateHouse;
import model.map.Map;
import model.map.Texture;
import model.map.Tile;
import model.people.Units;
import view.enums.messages.SelectUnitMenuMessages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;

public class SelectUnitMenuController {
    public static SelectUnitMenuMessages checkMoveUnit(Matcher matcher, int[] currentLocation, String unitType) {
        if (!Utils.isValidCommandTags(matcher, "xGroup", "yGroup")) return SelectUnitMenuMessages.INVALID_COMMAND;

        Map map = Stronghold.getCurrentGame().getMap();
        Path shortestPath;
        int destinationX = Integer.parseInt(matcher.group("xCoordinate"));
        int destinationY = Integer.parseInt(matcher.group("yCoordinate"));
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];

        if (!Utils.isValidCoordinates(map, destinationX, destinationY))
            return SelectUnitMenuMessages.INVALID_COORDINATE;
        else if (notValidTextureForMoving(map.getTile(destinationX, destinationY)))
            return SelectUnitMenuMessages.INVALID_DESTINATION_TEXTURE;
        else if (!isValidDestinationSameOwnerUnits(map.getTile(currentX, currentY), map.getTile(destinationX, destinationY)))
            return SelectUnitMenuMessages.INVALID_DESTINATION_DIFFERENT_OWNER_UNIT;
        else if (BuildingUtils.isBuildingInTile(map.getTile(destinationX, destinationY).getBuilding()) &&
                map.getTile(destinationX, destinationY).getBuilding() instanceof Climbable &&
                !((Climbable) map.getTile(destinationX, destinationY).getBuilding()).isClimbable())
            return SelectUnitMenuMessages.INVALID_DESTINATION_ONCLIMABLE_BUILDING;
        else if ((shortestPath = findRootToDestination(map, unitType, currentX, currentY, destinationX, destinationY)) == null)
            return SelectUnitMenuMessages.INVALID_DISTANCE;

        moveUnits(map, unitType, shortestPath, currentLocation, destinationX, destinationY);

        return SelectUnitMenuMessages.SUCCESS;
    }

    private static void moveUnits(Map map, String unitType, Path shortestPath, int[] currentLocation, int destinationX, int destinationY) {
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];
        ArrayList<Units> selectedUnits = map.getTile(currentX, currentY).getUnitsByType(unitType);

        for (Units unit : selectedUnits) {
            unit.setLeftMoves(unit.getLeftMoves() - (shortestPath.getLength() - 1));
        }
        map.getTile(destinationX, destinationY).getUnits().addAll(selectedUnits);
        map.getTile(currentX, currentY).clearUnitsByType(selectedUnits);
        currentLocation[0] = destinationX;
        currentLocation[1] = destinationY;
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

    private static Path findRootToDestination(Map map, String unitType, int currentX, int currentY, int destinationX, int destinationY) {
        int speed = minimumSpeed(map.getTile(currentX, currentY).getUnitsByType(unitType));
        ArrayList<Path> paths = new ArrayList<>();

        pathDFS(map, unitType, currentX, currentY, destinationX, destinationY, speed, paths, new Path());

        if (paths.size() == 0) return null;
        else return shortestPath(paths);
    }

    private static void pathDFS(Map map, String unitType, int currentX, int currentY, int destinationX, int destinationY, int speed, ArrayList<Path> paths, Path path) {
        int[] currentLocation = {currentX, currentY};
        int[] destinationLocation = {destinationX, destinationY};
        int leftMoves = speed - path.getLength();
        Building building = map.getTile(currentX, currentY).getBuilding();

        if (!Utils.isValidCoordinates(map, currentX, currentY)) return;
        else if (!Arrays.equals(currentLocation, destinationLocation) && leftMoves == 0) return;
        else if (path.getPath().contains(currentLocation)) return;
        else if (notValidTextureForMoving(map.getTile(currentX, currentY))) return;
        else if (building != null) {
            if (!(building instanceof Climbable)) return;
            else if (!canClimbTheBuilding(map, unitType, currentLocation, path.getPath().get(path.getLength() - 2), building))
                return;
        }

        path.addLocationToPath(currentLocation);
        if (Arrays.equals(currentLocation, destinationLocation)) {
            paths.add(path);
        } else {
            pathDFS(map, unitType, currentX + 1, currentY, destinationX, destinationY, speed, paths, path);
            pathDFS(map, unitType, currentX - 1, currentY, destinationX, destinationY, speed, paths, path);
            pathDFS(map, unitType, currentX, currentY + 1, destinationX, destinationY, speed, paths, path);
            pathDFS(map, unitType, currentX, currentY - 1, destinationX, destinationY, speed, paths, path);
        }
        path.removePath(path);
    }

    private static boolean canClimbTheBuilding(Map map, String unitType, int[] currentLocation, int[] previousLocation, Building currentBuilding) {
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];
        int previousX = previousLocation[0];
        int previousY = previousLocation[1];
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        Building previousBuilding = map.getTile(previousX, previousY).getBuilding();

        if (currentBuilding instanceof GateHouse) {
            if (currentGovernance.equals(((GateHouse) currentBuilding).getGateController()))
                return true;
            else if (previousBuilding instanceof Climbable) // TODO: change gate controller end of choosing path
                return true;
            else if (map.getTile(previousX, previousY).getUnits().get(0).getName().equals("assassin"))
                return true;
//            else if (map.getTile(previousX, previousY).getUnits())
        }

        return false;
    }

    private static int minimumSpeed(ArrayList<Units> units) {
        int minimumSpeed = 5 + 1;
        for (Units unit : units) {
            if (unit.getSpeed() < minimumSpeed)
                minimumSpeed = unit.getSpeed();
        }
        return minimumSpeed;
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

    private static boolean isValidDestinationSameOwnerUnits(Tile currentTile, Tile destination) {
        return currentTile.getUnits().get(0).getOwnerGovernance().equals(destination.getUnits().get(0).getOwnerGovernance());
    }

    private static void attackMachine() {

    }
}
