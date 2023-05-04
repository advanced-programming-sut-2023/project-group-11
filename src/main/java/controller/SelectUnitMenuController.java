package controller;

import model.Governance;
import model.Path;
import model.Stronghold;
import model.buildings.*;
import model.map.Map;
import model.map.Texture;
import model.map.Tile;
import model.people.Troops;
import model.people.Units;
import model.people.enums.MachineTypes;
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
                map.getTile(destinationX, destinationY).getBuilding() instanceof Climbable climbable &&
                !climbable.isClimbable())
            return SelectUnitMenuMessages.INVALID_DESTINATION_UNCLIMBABLE_BUILDING;
        else if ((shortestPath = findRootToDestination(map, unitType, currentX, currentY, destinationX, destinationY)) == null)
            return SelectUnitMenuMessages.INVALID_DISTANCE;

        moveUnits(map, unitType, shortestPath, currentLocation, destinationX, destinationY);

        return SelectUnitMenuMessages.SUCCESS;
    }

    public static SelectUnitMenuMessages checkAirAttack(Matcher matcher, int[] currentLocation, String unitType) {
        if (!Utils.isValidCommandTags(matcher, "xGroup", "yGroup"))
            return SelectUnitMenuMessages.INVALID_COMMAND;

        Map map = Stronghold.getCurrentGame().getMap();
        int targetX = Integer.parseInt(matcher.group("xCoordinate"));
        int targetY = Integer.parseInt(matcher.group("yCoordinate"));
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];
        ArrayList<Units> selectedUnits = map.getTile(currentX, currentY).getUnitsByType(unitType);
        Tile targetTile = map.getTile(targetX, targetY);

        if (!Utils.isValidCoordinates(map, targetX, targetY))
            return SelectUnitMenuMessages.INVALID_COORDINATE;
        else if (!isValidUnitForAirAttack(unitType))
            return SelectUnitMenuMessages.INVALID_UNIT_TYPE_TO_ATTACK;
        else if (((Troops) selectedUnits.get(0)).getRange() < Math.abs(currentX - targetX) ||
                ((Troops) selectedUnits.get(0)).getRange() < Math.abs(currentY - targetY))
            return SelectUnitMenuMessages.OUT_OF_RANGE;
        else if (noAttackLeft(selectedUnits))
            return SelectUnitMenuMessages.NO_ATTACK_LEFT;
        else if (targetTile.getUnits().size() == 0 &&
                targetTile.getBuilding() == null)
            return SelectUnitMenuMessages.EMPTY_TILE;
        else if (targetTile.getUnits().get(0).getOwner().equals(selectedUnits.get(0).getOwner()) ||
                targetTile.getBuilding().getOwner().equals(selectedUnits.get(0).getOwner()))
            return SelectUnitMenuMessages.FRIENDLY_ATTACK;

        if (targetTile.getBuilding() != null) attackBuilding(selectedUnits, targetTile);
        else attackUnits(selectedUnits, targetTile);

        return SelectUnitMenuMessages.SUCCESS;
    }

    private static boolean noAttackLeft(ArrayList<Units> selectedUnits) {
        ArrayList<Units> removings = new ArrayList<>();
        for (int i = 0; i < selectedUnits.size(); i++) {
            if (selectedUnits.get(i).hasAttacked())
                removings.add(selectedUnits.get(i));
        }
        selectedUnits.removeAll(removings);

        return selectedUnits.size() == 0;
    }

    public static SelectUnitMenuMessages checkGroundAttack(Matcher matcher) {
        return null;
    }

    public static SelectUnitMenuMessages checkPatrolUnit(Matcher matcher) {
        return null;
    }

    public static SelectUnitMenuMessages checkSetUnitState(Matcher matcher) {
        return null;
    }

    public static SelectUnitMenuMessages checkPourOil(Matcher matcher) {
        return null;
    }

    public static SelectUnitMenuMessages checkDigTunnel(Matcher matcher) {
        return null;
    }

    public static SelectUnitMenuMessages checkBuildMachine(Matcher matcher, int[] currentLocation, String unitType) {
        if (!unitType.equals("engineer"))
            return SelectUnitMenuMessages.INVALID_COMMAND;
        String machineType = matcher.group("machineType");
        try {
            MachineTypes.valueOf(machineType.replace(" ", "_").toUpperCase());
        } catch (IllegalArgumentException e) {
            return SelectUnitMenuMessages.INVALID_MACHINE_TYPE;
        }
        return SelectUnitMenuMessages.SUCCESS;
    }

    private static SelectUnitMenuMessages disbandUnit() {
        return null;
    }

    private static void moveUnits(Map map, String unitType, Path shortestPath, int[] currentLocation, int destinationX, int destinationY) {
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];
        ArrayList<Units> selectedUnits = map.getTile(currentX, currentY).getUnitsByType(unitType);
        map.getTile(currentX, currentY).clearUnitsByType(selectedUnits);

        setLeftMoves(shortestPath, selectedUnits);
        applyPathEffects(map, shortestPath, selectedUnits);

        map.getTile(destinationX, destinationY).getUnits().addAll(selectedUnits);
        currentLocation[0] = destinationX;
        currentLocation[1] = destinationY;
    }

    private static Path findRootToDestination(Map map, String unitType, int currentX, int currentY, int destinationX, int destinationY) {
        int speed = minimumSpeed(map.getTile(currentX, currentY).getUnitsByType(unitType));
        ArrayList<Path> paths = new ArrayList<>();

        pathDFS(map, unitType, currentX, currentY, destinationX, destinationY, speed, paths, new Path());

        if (paths.size() == 0) return null;
        else return getShortestPath(paths);
    }

    private static void pathDFS(Map map, String unitType, int currentX, int currentY, int destinationX, int destinationY, int speed, ArrayList<Path> paths, Path path) {
        int[] previousLocation = path.getPath().get(path.getLength() - 1);
        int[] currentLocation = {currentX, currentY};
        int[] destinationLocation = {destinationX, destinationY};
        int leftMoves = speed - path.getLength();

        if (!Utils.isValidCoordinates(map, currentX, currentY)) return;
        Building building = map.getTile(currentX, currentY).getBuilding();

        if (!Arrays.equals(currentLocation, destinationLocation) && leftMoves == 0) return;
        else if (path.getPath().contains(currentLocation)) return;
        else if (notValidTextureForMoving(map.getTile(currentX, currentY))) return;
        else if (building != null) {
            if (!(building instanceof Climbable || building instanceof Trap)) return;
            else if (!canClimbTheBuilding(map, unitType, previousLocation, building))
                return;
        } else if (map.getTile(previousLocation[0], previousLocation[1]).getBuilding() != null &&
                !canDescend(map, unitType, currentLocation, path.getPath().get(path.getLength() - 1)))
            return;

        path.addToPath(currentLocation);
        if (Arrays.equals(currentLocation, destinationLocation)) {
            paths.add(path);
        } else {
            pathDFS(map, unitType, currentX + 1, currentY, destinationX, destinationY, speed, paths, path);
            pathDFS(map, unitType, currentX - 1, currentY, destinationX, destinationY, speed, paths, path);
            pathDFS(map, unitType, currentX, currentY + 1, destinationX, destinationY, speed, paths, path);
            pathDFS(map, unitType, currentX, currentY - 1, destinationX, destinationY, speed, paths, path);
        }
        path.removeFromPath(currentLocation);
    }

    private static boolean canClimbTheBuilding(Map map, String unitType, int[] previousLocation, Building currentBuilding) {
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        int previousX = previousLocation[0];
        int previousY = previousLocation[1];
        Tile previousTile = map.getTile(previousX, previousY);
        Building previousBuilding = previousTile.getBuilding();

        if (notValidUnitTypeForClimbing(unitType))
            return false;

        if (currentBuilding instanceof Trap)
            return true;
        else if (currentBuilding instanceof GateHouse gateHouse) { // TODO: change gate controller end of choosing path
            if (currentGovernance.equals(gateHouse.getGateController()))
                return true;
            else if (previousBuilding instanceof Climbable && !previousBuilding.getName().equals("stairs"))
                return true;
            else if (unitType.equals("assassin"))
                return true;
            else if (previousTile.getUnitsByType("ladderman").size() != 0)
                return true;
            else return previousTile.getUnitsByType("siege tower").size() != 0;
        } else if (currentBuilding instanceof Tower) {
            return previousBuilding instanceof Climbable;
        } else {
            if (previousBuilding instanceof Climbable)
                return true;
            else if (unitType.equals("assassin"))
                return true;
            else if (previousTile.getUnitsByType("ladderman").size() != 0)
                return true;
            else return previousTile.getUnitsByType("siege tower").size() != 0;
        }
    }

    private static boolean canDescend(Map map, String unitType, int[] currentLocation, int[] previousLocation) {
        Tile previousTile = map.getTile(previousLocation[0], previousLocation[1]);
        Tile currentTile = map.getTile(currentLocation[0], currentLocation[1]);
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();

        if (previousTile.getBuilding().getName().equals("stairs"))
            return true;
        else if (currentTile.getUnitsByType("ladderman").size() != 0)
            return true;
        else if (currentTile.getUnitsByType("siege tower").size() != 0)
            return true;
        else if (unitType.equals("assassin"))
            return true;
        else
            return previousTile.getBuilding() instanceof GateHouse gateHouse &&
                    gateHouse.getGateController().equals(currentGovernance);
    }

    private static int minimumSpeed(ArrayList<Units> units) {
        int minimumSpeed = 5 + 1;
        for (Units unit : units) {
            if (unit.getLeftMoves() < minimumSpeed)
                minimumSpeed = unit.getLeftMoves();
        }
        return minimumSpeed;
    }

    private static Path getShortestPath(ArrayList<Path> paths) {
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

    static boolean notValidUnitTypeForClimbing(String type) {
        return type.equals("knight") || type.equals("ladderman") || type.equals("horse archer") ||
                type.equals("siege tower") || type.equals("battering ram") || type.equals("trebuchets") ||
                type.equals("fire ballista") || type.equals("catapults");
    }

    private static boolean notValidTextureForMoving(Tile destination) {
        return destination.getTexture().equals(Texture.MARSH) || destination.getTexture().equals(Texture.RIVER) ||
                destination.getTexture().equals(Texture.SEA) || destination.getTexture().equals(Texture.SMALL_LAKE) ||
                destination.getTexture().equals(Texture.BIG_LAKE) || destination.getTexture().equals(Texture.CLIFF);
    }

    private static boolean isValidDestinationSameOwnerUnits(Tile currentTile, Tile destination) {
        return currentTile.getUnits().get(0).getOwner().equals(destination.getUnits().get(0).getOwner());
    }

    private static void setLeftMoves(Path shortestPath, ArrayList<Units> selectedUnits) {
        for (Units unit : selectedUnits) {
            unit.setLeftMoves(unit.getLeftMoves() - (shortestPath.getLength() - 1));
        }
    }

    private static void applyPathEffects(Map map, Path shortestPath, ArrayList<Units> selectedUnits) {
        Building currentBuilding;
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        for (int[] location : shortestPath.getPath()) {
            if ((currentBuilding = map.getTile(location[0], location[1]).getBuilding()) instanceof GateHouse gateHouse)
                gateHouse.setGateController(Stronghold.getCurrentGame().getCurrentGovernance());
            else if (currentBuilding instanceof Trap trap && !trap.getOwner().equals(currentGovernance))
                applyTrapDamage(selectedUnits, trap);

            if (selectedUnits.size() == 0) return;
        }
    }

    private static void applyTrapDamage(ArrayList<Units> selectedUnits, Trap currentBuilding) {
        for (Units unit : selectedUnits) {
            unit.setHp(unit.getHp() - currentBuilding.getDamage());
        }
        int size = selectedUnits.size();
        for (int i = 0, j = 0; i < size; i++, j++) {
            if (selectedUnits.get(j).getHp() <= 0) {
                selectedUnits.remove(j);
                j--;
            }
        }
    }

    private static boolean isValidUnitForAirAttack(String type) {
        return type.equals("archer") ||
                type.equals("archer bow") || type.equals("horse archer") || type.equals("fire thrower") ||
                type.equals("trebuchets") || type.equals("fire ballista") || type.equals("catapults");
    }

    private static void attackBuilding(ArrayList<Units> selectedUnits, Tile targetTile) {
        Building targetBuilding = targetTile.getBuilding();
        targetBuilding.setHitPoint(targetBuilding.getHitPoint() - selectedUnits.size()*((Troops) selectedUnits.get(0)).getDamage());

        for (Units unit : selectedUnits)
            unit.setAttacked(true);
        // destroy building

    }

    private static void attackUnits(ArrayList<Units> selectedUnits, Tile targetTile) {

    }
}
