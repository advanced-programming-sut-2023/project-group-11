package controller;

import model.Governance;
import model.Path;
import model.Stronghold;
import model.buildings.*;
import model.map.Map;
import model.map.Texture;
import model.map.Tile;
import model.people.Attacker;
import model.people.Engineer;
import model.people.Machine;
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
                map.getTile(destinationX, destinationY).getBuilding() instanceof Climbable climbable &&
                !climbable.isClimbable())
            return SelectUnitMenuMessages.INVALID_DESTINATION_UNCLIMBABLE_BUILDING;
        else if ((shortestPath = findRootToDestination(map, unitType, currentX, currentY, destinationX, destinationY)) == null)
            return SelectUnitMenuMessages.INVALID_DISTANCE;

        moveUnits(map, unitType, shortestPath, currentLocation, destinationX, destinationY);

        return SelectUnitMenuMessages.SUCCESS;
    }

    public static SelectUnitMenuMessages checkAttack(Matcher matcher, int[] currentLocation, String unitType, String attackType) {
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
        else if ((attackType.equals("air attack") && !isValidUnitForAirAttack(unitType)) ||
                (attackType.equals("ground attack") && !isValidUnitForGroundAttack(unitType)))
            return SelectUnitMenuMessages.INVALID_UNIT_TYPE_TO_ATTACK;
        else if (((Attacker) selectedUnits.get(0)).getRange() < Math.abs(currentX - targetX) ||
                ((Attacker) selectedUnits.get(0)).getRange() < Math.abs(currentY - targetY))
            return SelectUnitMenuMessages.OUT_OF_RANGE;
        else if (noAttackLeft(selectedUnits))
            return SelectUnitMenuMessages.NO_ATTACK_LEFT;
        else if (targetTile.getUnits().size() == 0 && targetTile.getBuilding() == null)
            return SelectUnitMenuMessages.EMPTY_TILE;
        else if (targetTile.getUnits().get(0).getOwner().equals(selectedUnits.get(0).getOwner()) ||
                targetTile.getBuilding().getOwner().equals(selectedUnits.get(0).getOwner()))
            return SelectUnitMenuMessages.FRIENDLY_ATTACK;

        //TODO: set damaging concepts
        //TODO: attack both building and units
        if (targetTile.getBuilding() != null) attackBuilding(selectedUnits, targetTile);
        else attackUnits(selectedUnits, targetTile);

        //TODO: reset leftMoves and attacked for units in next turn

        return SelectUnitMenuMessages.SUCCESS;
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
        String machineType = Utils.removeDoubleQuotation(matcher.group("machineType"));
        if (!Utils.isValidMachineType(machineType))
            return SelectUnitMenuMessages.INVALID_MACHINE_TYPE;
        Governance governance = Stronghold.getCurrentGame().getCurrentGovernance();
        Machine machine = new Machine(machineType);
        if (governance.getGold() < machine.getCost())
            return SelectUnitMenuMessages.NOT_ENOUGH_GOLD;
        Tile tile = Stronghold.getCurrentGame().getMap().getTile(currentLocation[0], currentLocation[1]);
        if (tile.getUnitsByType("engineer").size() < machine.getEngineersNeededToActivate())
            return SelectUnitMenuMessages.NOT_ENOUGH_ENGINEERS;
        buildMachine(machine, tile, governance);
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
        else if (currentBuilding instanceof GateHouse gateHouse) {
            if (currentGovernance.equals(gateHouse.getGateController()))
                return true;
            else if (previousBuilding instanceof Climbable climbable && climbable.getName().equals("stairs"))
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
        return type.equals("archer") || type.equals("archer bow") ||
                type.equals("slinger") || type.equals("horse archer") || type.equals("fire thrower") ||
                type.equals("trebuchets") || type.equals("fire ballista") || type.equals("catapults");
    }

    private static boolean isValidUnitForGroundAttack(String type) {
        return type.equals("spearman") || type.equals("pikeman") || type.equals("maceman") ||
                type.equals("swordsman") || type.equals("knight") || type.equals("black monk") ||
                type.equals("slaves") || type.equals("assassin") || type.equals("arabian swordsman") ||
                type.equals("battering ram");
    }

    private static boolean noAttackLeft(ArrayList<Units> selectedUnits) {
        ArrayList<Units> removings = new ArrayList<>();
        for (int i = 0; i < selectedUnits.size(); i++) {
            if (((Attacker) selectedUnits.get(i)).hasAttacked())
                removings.add(selectedUnits.get(i));
        }
        selectedUnits.removeAll(removings);

        return selectedUnits.size() == 0;
    }

    private static void attackBuilding(ArrayList<Units> selectedUnits, Tile targetTile) {
        Building targetBuilding = targetTile.getBuilding();
        Map map = Stronghold.getCurrentGame().getMap();
        int targetHp = targetBuilding.getHitPoint();
        int attackerDamage = selectedUnits.size() * ((Attacker) selectedUnits.get(0)).getDamage();

        if (targetBuilding instanceof GateHouse && selectedUnits.get(0).getName().equals("battle ram"))
            targetBuilding.setHitPoint(targetHp - 3 * attackerDamage);
        else
            targetBuilding.setHitPoint(targetHp - attackerDamage);
        setAttackedTrue(selectedUnits);
        if (targetBuilding.getHitPoint() <= 0) {
            destroyBuilding(map, targetBuilding);
            //TODO: rearrange the climbablity
        }
    }

    private static void destroyBuilding(Map map, Building building) {
        int buildingX = building.getXCoordinate();
        int buildingY = building.getYCoordinate();
        int buildingSize = building.getSize();

        building.getOwner().getBuildings().remove(building);
        for (int i = 0; i < buildingSize; i++)
            for (int j = 0; j < buildingSize; j++)
                map.getTile(buildingX + i, buildingY + j).setBuilding(null);
    }

    private static void attackUnits(ArrayList<Units> selectedUnits, Tile targetTile) {
        int attackerDamage = selectedUnits.size() * ((Attacker) selectedUnits.get(0)).getDamage();

        for (Units unit : targetTile.getUnits())
            unit.setHp(unit.getHp() - attackerDamage);
        setAttackedTrue(selectedUnits);
        removeDeadUnits(targetTile);
        //TODO: set reacting to attacks
        //TODO: kill engineers in siege machines
        //TODO: apply troops' dying in population
    }

    private static void removeDeadUnits(Tile targetTile) {
        ArrayList<Units> removings = new ArrayList<>();
        for (int i = 0; i < targetTile.getUnits().size(); i++)
            if (targetTile.getUnits().get(i).getHp() <= 0)
                removings.add(targetTile.getUnits().get(i));
        targetTile.getUnits().removeAll(removings);
    }

    private static void setAttackedTrue(ArrayList<Units> selectedUnits) {
        for (Units unit : selectedUnits) ((Attacker) unit).setAttacked(true);
    }

    private static void buildMachine(Machine machine, Tile tile, Governance governance) {
        governance.setGold(governance.getGold() - machine.getCost());
        machine.setOwnerGovernance(governance);
        machine.setActive(true);
        //TODO: add to governance units
        for (int i = 0; i < machine.getEngineersNeededToActivate(); i++) {
            Engineer engineer = (Engineer) tile.getUnitsByType("engineer").get(0);
            machine.getEngineers().add(engineer);
            tile.getUnits().remove(engineer);
        }
        tile.addUnit(machine);
    }
}
