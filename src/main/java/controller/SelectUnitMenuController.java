package controller;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.Game;
import model.Governance;
import model.Path;
import model.Stronghold;
import model.buildings.*;
import model.map.Map;
import model.map.Texture;
import model.map.Tile;
import model.people.*;
import model.people.enums.Speed;
import model.people.enums.UnitState;
import view.animation.AirAttackAnimation;
import view.animation.DigAnimation;
import view.animation.MovingAnimation;
import view.enums.messages.SelectUnitMenuMessages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;

public class SelectUnitMenuController {
    public static SelectUnitMenuMessages checkMoveUnit(int[] currentLocation, int destinationX, int destinationY, String unitType, boolean isPatrol) {
//        if (!Utils.isValidCommandTags(matcher, "xCoordinate", "yCoordinate"))
//            return SelectUnitMenuMessages.INVALID_COMMAND;
        Map map = Stronghold.getCurrentGame().getMap();
        Path shortestPath;
//        int destinationX = Integer.parseInt(matcher.group("xCoordinate"));
//        int destinationY = Integer.parseInt(matcher.group("yCoordinate"));
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];

        if (!Utils.isValidCoordinates(map, destinationX, destinationY))
            return SelectUnitMenuMessages.INVALID_COORDINATE;
        if (notValidTextureForMoving(map.getTile(destinationX, destinationY)))
            return SelectUnitMenuMessages.INVALID_DESTINATION_TEXTURE;
        else if (currentX == destinationX && currentY == destinationY)
            return SelectUnitMenuMessages.NO_MOVES_NEEDED;
        else if (minimumSpeed(map.getTile(currentLocation).getUnitsByType(unitType)) <= 0)
            return SelectUnitMenuMessages.NO_MOVES_LEFT;
        else if (map.getTile(destinationX, destinationY).getUnits().size() != 0 &&
                !isValidDestinationSameOwnerUnits(map.getTile(currentX, currentY), map.getTile(destinationX, destinationY)))
            return SelectUnitMenuMessages.INVALID_DESTINATION_DIFFERENT_OWNER_UNIT;
        else if (BuildingUtils.isBuildingInTile(map.getTile(destinationX, destinationY).getBuilding()) &&
                (!(map.getTile(destinationX, destinationY).getBuilding() instanceof Climbable)))
            return SelectUnitMenuMessages.INVALID_DESTINATION_UNCLIMBABLE_BUILDING;
        else if ((shortestPath = findRootToDestination(map, unitType, currentX, currentY, destinationX, destinationY)) == null)
            return SelectUnitMenuMessages.INVALID_DISTANCE;

        if (isPatrol) setPatrolUnit(destinationX, destinationY, currentLocation, unitType);
        else stopPatrol(map.getTile(currentX, currentY).getUnitsByType(unitType));
        stopDigging(currentLocation, unitType);
        moveUnits(map, unitType, shortestPath, currentLocation, destinationX, destinationY);

        return SelectUnitMenuMessages.SUCCESS;
    }

    public static SelectUnitMenuMessages checkAttack(int[] currentLocation, int targetX, int targetY, String unitType) {
        Map map = Stronghold.getCurrentGame().getMap();
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];
        ArrayList<Unit> selectedUnits = map.getTile(currentX, currentY).getUnitsByType(unitType);
        Tile targetTile = map.getTile(targetX, targetY);
        Tile currentTile = map.getTile(currentX, currentY);

        if (!Utils.isValidCoordinates(map, targetX, targetY))
            return SelectUnitMenuMessages.INVALID_COORDINATE;
        else if (!selectedUnits.get(0).isValidUnitForAirAttack() && !selectedUnits.get(0).isValidUnitForGroundAttack())
            return SelectUnitMenuMessages.INVALID_UNIT_TYPE_TO_ATTACK;
        else if (((Attacker) selectedUnits.get(0)).getRange(currentTile) < Math.abs(currentX - targetX) ||
                ((Attacker) selectedUnits.get(0)).getRange(currentTile) < Math.abs(currentY - targetY))
            return SelectUnitMenuMessages.OUT_OF_RANGE;
        else if (noAttackLeft(selectedUnits))
            return SelectUnitMenuMessages.NO_ATTACK_LEFT;
        else if (targetTile.getUnits().size() == 0 && targetTile.getBuilding() == null)
            return SelectUnitMenuMessages.EMPTY_TILE;
        else if ((targetTile.getUnits().size() != 0 &&
                targetTile.getUnits().get(0).getOwner().equals(selectedUnits.get(0).getOwner())) ||
                (targetTile.getBuilding() != null &&
                        targetTile.getBuilding().getOwner().equals(selectedUnits.get(0).getOwner())))
            return SelectUnitMenuMessages.FRIENDLY_ATTACK;

        attack(selectedUnits, unitType, targetTile, currentTile);

        return SelectUnitMenuMessages.SUCCESS;
    }

    public static void setPatrolUnit(int destinationX, int destinationY, int[] currentLocation, String unitType) {
        Map map = Stronghold.getCurrentGame().getMap();
//        int destinationX = Integer.parseInt(matcher.group("xCoordinate"));
//        int destinationY = Integer.parseInt(matcher.group("yCoordinate"));
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];
        ArrayList<Unit> selectedUnits = map.getTile(currentLocation).getUnitsByType(unitType);

        setPatrolDestination(destinationX, destinationY, currentX, currentY, selectedUnits);
    }

    public static void patrolUnit(ArrayList<Unit> units) {
        if (units.size() == 0)
            return;
        Map map = Stronghold.getCurrentGame().getMap();
        Unit unit = units.get(0);
        String unitType = unit.getName();
        Path shortestPath;
        int currentX = unit.getPatrolOrigin()[0];
        int currentY = unit.getPatrolOrigin()[1];
        int destinationX = unit.getPatrolDestination()[0];
        int destinationY = unit.getPatrolDestination()[1];

        if (!isValidForMoving(map, currentX, currentY, destinationX, destinationY)) stopPatrol(units);
        if ((shortestPath = findRootToDestination(map, unitType, currentX, currentY, destinationX, destinationY)) != null) {
            moveUnits(map, unitType, shortestPath, unit.getPatrolOrigin(), destinationX, destinationY);
            setPatrolDestination(destinationX, destinationY, currentX, currentY, units);
        } else stopPatrol(units);
    }

    private static void setPatrolDestination(int destinationX, int destinationY, int currentX, int currentY, ArrayList<Unit> selectedUnits) {
        for (Unit unit : selectedUnits) {
            unit.setPatrolOrigin(new int[]{destinationX, destinationY});
            unit.setPatrolDestination(new int[]{currentX, currentY});
        }
    }

//    public static SelectUnitMenuMessages checkStopPatrol(int[] currentLocation, String unitType) {
//        Map map = Stronghold.getCurrentGame().getMap();
//        int currentX = currentLocation[0];
//        int currentY = currentLocation[1];
//        ArrayList<Unit> selectedUnits = map.getTile(currentX, currentY).getUnitsByType(unitType);
//
//        if (!selectedUnits.get(0).isPatrolling()) return SelectUnitMenuMessages.NOT_PATROLLING;
//
//        stopPatrol(selectedUnits);
//        return SelectUnitMenuMessages.SUCCESS;
//    }

    private static void stopPatrol(ArrayList<Unit> units) {
        for (Unit unit : units) unit.stopPatrol();
    }

    public static void checkSetUnitState(String state, int[] currentLocation, String unitType) {
        Map map = Stronghold.getCurrentGame().getMap();
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];
        ArrayList<Unit> selectedUnits = map.getTile(currentX, currentY).getUnitsByType(unitType);

        for (Unit unit : selectedUnits) unit.setUnitState(UnitState.valueOf(state.toUpperCase()));
    }

    public static SelectUnitMenuMessages checkPourOil(Matcher matcher, int[] currentLocation, String unitType) {
        String direction = matcher.group("direction");
        if (!unitType.equals("engineer"))
            return SelectUnitMenuMessages.INVALID_UNIT_TYPE;
        Tile tile = Stronghold.getCurrentGame().getMap().getTile(currentLocation);
        ArrayList<Unit> engineers = tile.getUnitsByType("engineer");
        if (engineers.size() == 0)
            return SelectUnitMenuMessages.NOT_ENOUGH_ENGINEERS;
        if (engineers.size() > 1)
            return SelectUnitMenuMessages.JUST_ONE_ENGINEER;
        Engineer engineer = (Engineer) tile.getUnitsByType("engineer").get(0);
        if (!engineer.hasOilPail())
            return SelectUnitMenuMessages.ENGINEER_WITHOUT_PAIL;
        if (engineer.isEmptyPail())
            return SelectUnitMenuMessages.ENGINEER_EMPTY_PAIL;
        if (!pourOil(engineers, direction, currentLocation))
            return SelectUnitMenuMessages.CANT_REFILL_THE_PAIL;
        return SelectUnitMenuMessages.SUCCESS;
    }

    public static boolean pourOil(ArrayList<Unit> engineer, String direction, int[] location) {
        int currentX = location[0];
        int currentY = location[1];
        Map map = Stronghold.getCurrentGame().getMap();
        Engineer realEngineer = (Engineer) engineer.get(0);
        switch (direction) {
            case "up" -> {
                if (pourOil(currentX - 1, currentY) || pourOil(currentX - 2, currentY))
                    realEngineer.setEmptyPail(true);
            }
            case "down" -> {
                if (pourOil(currentX + 1, currentY) || pourOil(currentX + 2, currentY))
                    realEngineer.setEmptyPail(true);
            }
            case "left" -> {
                if (pourOil(currentX, currentY - 1) || pourOil(currentX, currentY - 2))
                    realEngineer.setEmptyPail(true);
            }
            case "right" -> {
                if (pourOil(currentX, currentY + 1) || pourOil(currentX, currentY + 2))
                    realEngineer.setEmptyPail(true);
            }
        }
        if (!realEngineer.isEmptyPail())
            return true;
        Path shortestPath;
        for (int i = -5; i <= 5; i++) {
            for (int j = Math.abs(i) - 5; j <= 5 - Math.abs(i); j++) {
                if (!Utils.isValidCoordinates(map, currentX + i, currentY + j))
                    continue;
                Tile tile = map.getTile(currentX + i, currentY + j);
                if (tile.getBuilding() != null && tile.getBuilding().getName().equals("oil smelter")) {
                    if ((shortestPath = findRootToDestination(map, "engineer", currentX, currentY, currentX + i, currentY + j)) != null
                            && shortestPath.getLength() <= 5) {
                        moveUnits(map, "engineer", shortestPath, location, currentX + i, currentY + j);
                        if (map.getTile(currentX + i, currentY + j).getUnitsByType("engineer").size() == 1) {
                            shortestPath = findRootToDestination(map, "engineer", currentX + i, currentY + j, currentX, currentY);
                            moveUnits(map, "engineer", shortestPath, new int[]{currentX + i, currentY + j}, currentX, currentY);
                            if (tile.getUnitsByType("engineer").size() == 1) {
                                ((Engineer) tile.getUnitsByType("engineer").get(0)).setEmptyPail(false);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean pourOil(int x, int y) {
        Map map = Stronghold.getCurrentGame().getMap();
        if (!Utils.isValidCoordinates(map, x, y))
            return false;
        Tile tile = map.getTile(x, y);
        if (tile.hasBuilding())
            return false;
        ArrayList<Unit> units = tile.getUnits();
        if (!units.get(0).getOwner().equals(Stronghold.getCurrentGame().getCurrentGovernance()) || units.size() == 0)
            return false;
        for (Unit unit : units) {
            unit.setHp(unit.getHp() - 50);
        }
        removeDeadUnits(tile);
        return true;
    }

    public static SelectUnitMenuMessages checkDigTunnel(int[] currentLocation, int destinationX, int destinationY, String unitType) {
        Map map = Stronghold.getCurrentGame().getMap();
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];
        ArrayList<Unit> units = map.getTile(currentX, currentY).getUnitsByType(unitType);

        int length = Math.min(10, getLength(currentX, currentY, destinationX, destinationY));
        String direction = getDirection(currentX, currentY, destinationX, destinationY);
        if (direction == null) return SelectUnitMenuMessages.INVALID_DIRECTION;

        for (Unit unit : units) unit.removeFromGame(map.getTile(currentX, currentY));
        moveUnitsForDigging(map, currentLocation, units.get(0).getName(), direction, length, Texture.SAND);
        return SelectUnitMenuMessages.SUCCESS;
    }

    public static SelectUnitMenuMessages checkDigPitch(int[] currentLocation, int destinationX, int destinationY, String unitType) {
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];
        Map map = Stronghold.getCurrentGame().getMap();
        ArrayList<Unit> selectedUnits = map.getTile(currentLocation).getUnitsByType(unitType);

        int length = getLength(currentX, currentY, destinationX, destinationY);
        String direction = getDirection(currentX, currentY, destinationX, destinationY);
        if (direction == null) return SelectUnitMenuMessages.INVALID_DIRECTION;

        if (!isValidDestinationForDiggingPitch(map, currentX, currentY, direction, length))
            return SelectUnitMenuMessages.INVALID_LENGTH;
        else if (notValidAreaForDiggingPitch(map, currentX, currentY, length, direction))
            return SelectUnitMenuMessages.INVALID_AREA_FOR_DIGGING_PITCH;

//        ArrayList<Tile> selectedTiles = getSelectedTiles(map, currentX, currentY, Math.min(minimumSpeed(selectedUnits), length), direction);

        setDiggingPitchConceptsForUnits(selectedUnits, currentX, currentY, length, direction, minimumSpeed(selectedUnits));
        moveUnitsForDigging(map, currentLocation, unitType, direction, Math.min(minimumSpeed(selectedUnits), length), Texture.PITCH);
//        digPitch(selectedTiles);

        return SelectUnitMenuMessages.SUCCESS;
    }

    private static int getLength(int currentX, int currentY, int destinationX, int destinationY) {
        return Math.abs(destinationX - currentX) + Math.abs(destinationY - currentY);
    }

    private static String getDirection(int currentX, int currentY, int destinationX, int destinationY) {
        if (destinationX == currentX) {
            if (currentY > destinationY) return "left";
            else if (currentY < destinationY) return "right";
            else return null;
        } else if (destinationY == currentY) {
            if (currentX > destinationX) return "up";
            else if (currentX < destinationX) return "down";
            else return null;
        } else return null;
    }

    public static SelectUnitMenuMessages checkBuildMachine(Tile tile, String machineType) {

        Governance governance = Stronghold.getCurrentGame().getCurrentGovernance();

        Machine machine = new Machine(machineType);
        machine.setLocation(Stronghold.getCurrentGame().getMap().getTileLocation(tile));

        if (governance.getGold() < machine.getCost()) return SelectUnitMenuMessages.NOT_ENOUGH_GOLD;


        if (tile.getUnitsByType("engineer").size() < machine.getEngineersNeededToActivate())
            return SelectUnitMenuMessages.NOT_ENOUGH_ENGINEERS;

        buildMachine(machine, tile, governance);
        return SelectUnitMenuMessages.SUCCESS;
    }

    public static void disbandUnit(int[] currentLocation, String unitType) {
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];
        Map map = Stronghold.getCurrentGame().getMap();
        Tile tile = map.getTile(currentX, currentY);
        ArrayList<Unit> selectedUnits = tile.getUnitsByType(unitType);
        Governance governance = selectedUnits.get(0).getOwner();
        int unEmployedPopulation = governance.getUnemployedPopulation();
        int currentPopulation = governance.getCurrentPopulation();

        for (Unit unit : selectedUnits) unit.removeFromGame(tile);

        governance.changeCurrentPopulation(Math.min(selectedUnits.size(), currentPopulation - unEmployedPopulation));
    }

    public static void stopUnit(int[] currentLocation, String unitType) {
        Map map = Stronghold.getCurrentGame().getMap();
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];
        ArrayList<Unit> selectedUnits = map.getTile(currentX, currentY).getUnitsByType(unitType);
        for (Unit selectedUnit : selectedUnits) selectedUnit.stop();
    }

    private static void moveUnits(Map map, String unitType, Path shortestPath, int[] currentLocation, int destinationX, int destinationY) {
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];
        ArrayList<Unit> selectedUnits = map.getTile(currentX, currentY).getUnitsByType(unitType);
        if (selectedUnits.size() == 0) return;

        setLeftMoves(shortestPath, selectedUnits);
        setLocation(selectedUnits, shortestPath);

        currentLocation[0] = destinationX;
        currentLocation[1] = destinationY;
    }

    public static Path findRootToDestination(Map map, String unitType, int currentX, int currentY, int destinationX, int destinationY) {
        int speed = minimumSpeed(map.getTile(currentX, currentY).getUnitsByType(unitType));
        ArrayList<Path> allPaths = new ArrayList<>();
        Path path = new Path();

        path.addToPath(new int[]{currentX, currentY});

        pathDFS(map, unitType, currentX + 1, currentY, destinationX, destinationY, speed, allPaths, path);
        pathDFS(map, unitType, currentX - 1, currentY, destinationX, destinationY, speed, allPaths, path);
        pathDFS(map, unitType, currentX, currentY + 1, destinationX, destinationY, speed, allPaths, path);
        pathDFS(map, unitType, currentX, currentY - 1, destinationX, destinationY, speed, allPaths, path);

        if (allPaths.size() == 0) return null;
        else return getShortestPath(allPaths);
    }

    private static void pathDFS(Map map, String unitType, int currentX, int currentY, int destinationX, int destinationY, int speed, ArrayList<Path> paths, Path path) {
        int[] previousLocation = path.getPath().get(path.getLength() - 1);
        int[] currentLocation = {currentX, currentY};
        int[] destinationLocation = {destinationX, destinationY};
        int leftMoves = speed - path.getLength();

        if (!Utils.isValidCoordinates(map, currentX, currentY)) return;
        Building building = map.getTile(currentX, currentY).getBuilding();

        if (!Arrays.equals(currentLocation, destinationLocation) && leftMoves <= 0) return;
        else if (path.getPath().contains(currentLocation)) return;
        else if (notValidTextureForMoving(map.getTile(currentX, currentY))) return;
        else if (map.getTile(currentLocation).getUnits().size() != 0 &&
                !isValidDestinationSameOwnerUnits(map.getTile(path.getPath().get(0)), map.getTile(currentLocation)))
            return;
        else if (building != null) {
            if (!(building instanceof Climbable || building instanceof Trap)) return;
            else if (!canClimbTheBuilding(map, unitType, previousLocation, building))
                return;
        } else if (map.getTile(previousLocation[0], previousLocation[1]).getBuilding() != null &&
                !canDescend(map, unitType, currentLocation, path.getPath().get(path.getLength() - 1)))
            return;

        path.addToPath(currentLocation);
        if (Arrays.equals(currentLocation, destinationLocation)) {
            paths.add(path.clone());
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

        if (currentBuilding instanceof Trap) return true;
        else if (notValidUnitTypeForClimbing(unitType))
            return false;
        else if (currentBuilding instanceof Keep)
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

        if (previousTile.getBuilding() instanceof Trap) return true;
        else if (previousTile.getBuilding().getName().equals("stairs")) return true;
        else if (previousTile.getBuilding() instanceof GateHouse) return true;
        else if (previousTile.getBuilding() instanceof Keep) return true;
        else if (currentTile.getUnitsByType("ladderman").size() != 0) return true;
        else if (currentTile.getUnitsByType("siege tower").size() != 0) return true;
        else return unitType.equals("assassin");
    }

    private static int minimumSpeed(ArrayList<Unit> units) {
        int minimumSpeed = Speed.VERY_HIGH.getMovesInEachTurn() + 1;
        for (Unit unit : units) {
            if (unit.getLeftMoves() < minimumSpeed)
                minimumSpeed = unit.getLeftMoves();
        }
        return minimumSpeed;
    }

    private static Path getShortestPath(ArrayList<Path> paths) {
        int length = Speed.VERY_HIGH.getMovesInEachTurn() + 2;
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

    public static boolean notValidTextureForMoving(Tile destination) {
        return !destination.getTexture().isSuitableForUnit();
    }

    public static boolean isValidDestinationSameOwnerUnits(Tile currentTile, Tile destination) {
        return destination.getUnits().size() == 0 ||
                currentTile.getUnits().size() == 0 ||
                currentTile.getUnits().get(0).getOwner().equals(destination.getUnits().get(0).getOwner());
    }

    private static void setLeftMoves(Path shortestPath, ArrayList<Unit> selectedUnits) {
        for (Unit unit : selectedUnits) {
            unit.setLeftMoves(unit.getLeftMoves() - (shortestPath.getLength() - 1));
        }
    }

    public static void setLocation(ArrayList<Unit> selectedUnits, Path shortestPath) {
        new MovingAnimation(selectedUnits, shortestPath, 2.0 / selectedUnits.get(0).getSpeed());
    }

    public static void applyPathEffects(int[] location, ArrayList<Unit> selectedUnits) {
        Building currentBuilding;
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        Map map = Stronghold.getCurrentGame().getMap();
        if ((currentBuilding = map.getTile(location[0], location[1]).getBuilding()) instanceof GateHouse gateHouse)
            gateHouse.setGateController(Stronghold.getCurrentGame().getCurrentGovernance());
        else if (currentBuilding instanceof Trap trap && !trap.getOwner().equals(currentGovernance))
            applyTrapDamage(selectedUnits, trap);
    }

    private static void applyTrapDamage(ArrayList<Unit> selectedUnits, Trap currentBuilding) {
        Map map = Stronghold.getCurrentGame().getMap();

        for (Unit unit : selectedUnits) {
            Tile tile = map.getTile(currentBuilding.getXCoordinate(), currentBuilding.getYCoordinate());
            unit.setHp(unit.getHp() - currentBuilding.getDamage());
            if (unit.getHp() <= 0) {
                unit.removeFromGame(tile);
                selectedUnits.remove(unit);
            }
        }
        currentBuilding.removeFromGame();
    }

    private static boolean noAttackLeft(ArrayList<Unit> selectedUnits) {
        ArrayList<Unit> removings = new ArrayList<>();
        for (Unit unit : selectedUnits) {
            if (((Attacker) unit).hasAttacked())
                removings.add(unit);
        }
        selectedUnits.removeAll(removings);

        return selectedUnits.size() == 0;
    }

    public static void attack(ArrayList<Unit> selectedUnits, String unitType, Tile targetTile, Tile currentTile) {
        boolean onlyBuilding = false;
        boolean onlyUnits = false;

        if (selectedUnits.get(0).isValidUnitForGroundAttack()) {
            if (targetTile.getBuilding() != null) {
                if (currentTile.getBuilding() != null && targetTile.getUnits().size() != 0)
                    onlyUnits = true;
                else onlyBuilding = true;
            } else if (!unitType.equals("battle ram")) onlyUnits = true;
        } else {
            if (unitType.equals("trebuchets") || unitType.equals("catapults") || unitType.equals("fire ballista") || unitType.equals("fire thrower")) {
                if (targetTile.getBuilding() != null) onlyBuilding = true;
                else onlyUnits = true;
            } else {
                if (targetTile.getUnits().size() != 0) onlyUnits = true;
            }
        }

        if (onlyBuilding) attackBuilding(selectedUnits, targetTile);
        else if (onlyUnits) attackUnits(selectedUnits, targetTile);
    }

    private static void attackBuilding(ArrayList<Unit> selectedUnits, Tile targetTile) {
        Building targetBuilding = targetTile.getBuilding();
        Map map = Stronghold.getCurrentGame().getMap();
        int targetHp = targetBuilding.getHitPoint();
        int attackerDamage = (int) (selectedUnits.size() * ((Attacker) selectedUnits.get(0)).getDamage() *
                (1 + selectedUnits.get(0).getOwner().getFearFactor() * 0.05));

        if (targetBuilding instanceof GateHouse && selectedUnits.get(0).getName().equals("battle ram"))
            targetBuilding.setHitPoint(targetHp - 3 * attackerDamage);
        else {
            targetBuilding.setHitPoint(targetHp - attackerDamage);
            if (selectedUnits.get(0).isValidUnitForAirAttack())
                attackAnimation(selectedUnits, targetTile);
        }

        if (targetBuilding.getHitPoint() <= 0)
            destroyBuilding(targetBuilding);
        else if (((Attacker) selectedUnits.get(0)).hasFiringWeapon() && !targetBuilding.isFiring()) {
            targetBuilding.setFiring(true);
            targetBuilding.setFiringLeft(2);
        }
        setAttackedTrue(selectedUnits);
    }

    private static void destroyBuilding(Building building) {
        building.removeFromGame();
    }

    private static void attackUnits(ArrayList<Unit> selectedUnits, Tile targetTile) {
        int attackerDamage = (int) (selectedUnits.size() * ((Attacker) selectedUnits.get(0)).getDamage() *
                (1 + selectedUnits.get(0).getOwner().getFearFactor() * 0.05));

        for (int i = 0; i < targetTile.getUnits().size(); i++) {
            Unit unit = targetTile.getUnits().get(i);
            if (unit.getHp() > attackerDamage) {
                unit.setHp(unit.getHp() - attackerDamage);
                break;
            } else {
                attackerDamage -= unit.getHp();
                unit.setHp(0);
            }
        }

        setAttackedTrue(selectedUnits);
        removeDeadUnits(targetTile);
        if (selectedUnits.get(0).isValidUnitForAirAttack())
            attackAnimation(selectedUnits, targetTile);
    }

    private static void attackAnimation(ArrayList<Unit> selectedUnits, Tile targetTile) {
        new AirAttackAnimation(Utils.getGameMenu(), new double[]{selectedUnits.get(0).getLocation()[0],
                selectedUnits.get(0).getLocation()[1]},
                new double[]{Stronghold.getCurrentGame().getMap().getTileLocation(targetTile)[0],
                        Stronghold.getCurrentGame().getMap().getTileLocation(targetTile)[1]});
    }

    public static void removeDeadUnits(Tile targetTile) {
        ArrayList<Unit> removings = new ArrayList<>();

        for (Unit unit : targetTile.getUnits()) {
            if (unit.getHp() <= 0) {
                if (unit instanceof Lord) {
                    killGovernance(unit.getOwner());
                    return;
                } else if (unit instanceof Machine machine) {
                    for (Engineer engineer : machine.getEngineers())
                        engineer.getOwner().removeUnit(engineer);
                }
                removings.add(unit);
            } else if (unit.getName().equals("assassin")) ((Troop) unit).setRevealed(true);
        }

        removings.forEach(unit -> unit.removeFromGame(targetTile));
    }

    private static void killGovernance(Governance owner) {
        Game currentGame = Stronghold.getCurrentGame();
        Map map = currentGame.getMap();
        currentGame.plusTurnCounter(-1);


        removeBuildings(owner);
        removeUnits(owner, map);
        currentGame.addLoserScore(owner, owner.getScore());
        currentGame.getGovernances().remove(owner);
        currentGame.setTurn(currentGame.getTurn() - (currentGame.getCurrentTurn() - 1));
    }

    private static void removeUnits(Governance owner, Map map) {
        int size = owner.getUnits().size();
        for (int i = 0; i < size; i++)
            owner.getUnits().get(0).removeFromGame(map.getTile(owner.getUnits().get(0).getLocation()));
    }

    private static void removeBuildings(Governance owner) {
        int size = owner.getBuildings().size();
        for (int i = 0; i < size; i++)
            owner.getBuildings().get(0).removeFromGame();
    }

    private static void setAttackedTrue(ArrayList<Unit> selectedUnits) {
        for (Unit unit : selectedUnits) {
            ((Attacker) unit).setAttacked(true);
            if (unit.getName().equals("assassin")) ((Troop) unit).setRevealed(true);
        }
    }

    private static void buildMachine(Machine machine, Tile tile, Governance governance) {
        governance.setGold(governance.getGold() - machine.getCost());
        machine.setActive(true);

        for (int i = 0; i < machine.getEngineersNeededToActivate(); i++) {
            Engineer engineer = (Engineer) tile.getUnitsByType("engineer").get(0);
            machine.addEngineer(engineer);
            tile.getUnits().remove(engineer);
        }
        machine.initializeUnit(tile, false);
    }

    private static boolean isValidForMoving(Map map, int currentX, int currentY, int destinationX, int destinationY) {
        return (!notValidTextureForMoving(map.getTile(destinationX, destinationY)) &&
                isValidDestinationSameOwnerUnits(map.getTile(currentX, currentY), map.getTile(destinationX, destinationY)));
    }

    private static boolean isValidDestinationForDiggingPitch(Map map, int currentX, int currentY, String direction, int length) {
        int destinationX = getDestinationForDigging(direction, currentX, currentY, length)[0];
        int destinationY = getDestinationForDigging(direction, currentX, currentY, length)[1];

        return Utils.isValidCoordinates(map, destinationX, destinationY) &&
                !notValidTextureForMoving(map.getTile(destinationX, destinationY)) &&
                !(map.getTile(destinationX, destinationY).getUnits().size() != 0 && !isValidDestinationSameOwnerUnits(map.getTile(currentX, currentY), map.getTile(destinationX, destinationY))) &&
                map.getTile(destinationX, destinationY).getBuilding() == null;
    }

    private static boolean notValidAreaForDiggingPitch(Map map, int x, int y, int length, String direction) {
        ArrayList<Tile> tilesToBeDug = getSelectedTiles(map, x, y, length, direction);
        for (int i = 1; i < tilesToBeDug.size(); i++) {
            if (tilesToBeDug.get(i).getBuilding() != null || tilesToBeDug.get(i).getUnits().size() != 0 ||
                    !tilesToBeDug.get(i).getTexture().isSuitableForPitch())
                return true;
        }
        return false;
    }

    private static ArrayList<Tile> getSelectedTiles(Map map, int x, int y, int length, String direction) {
        ArrayList<Tile> selectedTiles = new ArrayList<>();
        switch (direction) {
            case "left" -> {
                for (int i = 0; i <= length; i++)
                    selectedTiles.add(map.getTile(x, y - i));
            }
            case "right" -> {
                for (int i = 0; i <= length; i++)
                    selectedTiles.add(map.getTile(x, y + i));
            }
            case "up" -> {
                for (int i = 0; i <= length; i++)
                    selectedTiles.add(map.getTile(x - i, y));
            }
            case "down" -> {
                for (int i = 0; i <= length; i++)
                    selectedTiles.add(map.getTile(x + i, y));
            }
        }
        return selectedTiles;
    }

    private static void setDiggingPitchConceptsForUnits(ArrayList<Unit> selectedUnits, int currentX, int currentY, int length, String direction, int leftMoves) {
        for (Unit unit : selectedUnits) {
            ((Troop) unit).setDigging(true);
            ((Troop) unit).setDiggingDirection(direction);
            ((Troop) unit).setLeftDiggingLength(length - leftMoves);
            ((Troop) unit).setDiggingDestination(getDestinationForDigging(direction, currentX, currentY, length));

            if (((Troop) unit).getLeftDiggingLength() <= 0) ((Troop) unit).setDigging(false);
        }
    }

    private static void moveUnitsForDigging(Map map, int[] currentLocation, String unitType, String direction, int length, Texture texture) {
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];
        int destinationX = getDestinationForDigging(direction, currentX, currentY, length)[0];
        int destinationY = getDestinationForDigging(direction, currentX, currentY, length)[1];
        boolean isDigPitch = texture.equals(Texture.PITCH);
        ArrayList<Unit> selectedUnits = map.getTile(currentX, currentY).getUnitsByType(unitType);
        Path shortestPath = new Path();

        if (isDigPitch)
            shortestPath = findRootToDestination(map, unitType, currentX, currentY, destinationX, destinationY);
        else for (Tile selectedTile : getSelectedTiles(map, currentX, currentY, length, direction)) {
            System.out.println(length);
            System.out.println(Arrays.toString(map.getTileLocation(selectedTile)));
            shortestPath.addToPath(map.getTileLocation(selectedTile));
        }

        if (shortestPath != null) {
            setLeftMoves(shortestPath, selectedUnits);
            new DigAnimation(selectedUnits, shortestPath, texture);
        }
    }

    public static void digPitchInNextTurn(ArrayList<Unit> units) {
        Map map = Stronghold.getCurrentGame().getMap();
        Path shortestPath;
        ArrayList<Unit> selectedUnits = new ArrayList<>();
        ArrayList<Tile> selectedTiles = new ArrayList<>();
        Troop selected;
        String unitType;
        String direction;
        int[] currentLocation;
        int diggingLengthLeft;
        int currentX;
        int currentY;
        int destinationX;
        int destinationY;

        while (units.size() != 0) {
            selected = (Troop) units.get(0);
            unitType = selected.getName();
            for (Unit unit : units) {
                if (Arrays.equals(((Troop) unit).getDiggingDestination(), selected.getDiggingDestination()) &&
                        Arrays.equals(unit.getLocation(), selected.getLocation()))
                    selectedUnits.add(unit);
            }

            direction = selected.getDiggingDirection();
            diggingLengthLeft = selected.getLeftDiggingLength();
            currentLocation = selected.getLocation();
            currentX = currentLocation[0];
            currentY = currentLocation[1];
            destinationX = getDestinationForDigging(direction, currentX, currentY, Math.min(diggingLengthLeft, minimumSpeed(selectedUnits)))[0];
            destinationY = getDestinationForDigging(direction, currentX, currentY, Math.min(minimumSpeed(selectedUnits), diggingLengthLeft))[1];
            shortestPath = findRootToDestination(map, unitType, currentX, currentY, destinationX, destinationY);
            selectedTiles = getSelectedTiles(map, currentX, currentY, minimumSpeed(selectedUnits), direction);

            if (shortestPath != null) {
                setDiggingPitchConceptsForUnits(selectedUnits, currentX, currentY, diggingLengthLeft, direction, minimumSpeed(selectedUnits));
                moveUnitsForDigging(map, currentLocation, unitType, direction, Math.min(minimumSpeed(selectedUnits), diggingLengthLeft), Texture.PITCH);
//                digPitch(selectedTiles);
            } else {
                for (Unit unit : selectedUnits) ((Troop) unit).setDigging(false);
            }

            units.removeAll(selectedUnits);
            selectedUnits = new ArrayList<>();
        }

    }

    private static int[] getDestinationForDigging(String direction, int currentX, int currentY, int length) {
        int destinationX = currentX;
        int destinationY = currentY;

        switch (direction) {
            case "left" -> destinationY -= length;
            case "right" -> destinationY += length;
            case "up" -> destinationX -= length;
            case "down" -> destinationX += length;
        }

        return new int[]{destinationX, destinationY};
    }

    public static void stopDigging(int[] currentLocation, String unitType) {
        Map map = Stronghold.getCurrentGame().getMap();
        ArrayList<Unit> selectedUnits = map.getTile(currentLocation).getUnitsByType(unitType);

        for (Unit unit : selectedUnits) ((Troop) unit).setDigging(false);
    }

    public static boolean hasUnit(ArrayList<Tile> selectedTiles) {
        for (Tile selectedTile : selectedTiles) {
            ArrayList<Unit> units = selectedTile.getUnits();
            if (units.size() > 0 && units.get(0).isForCurrentGovernance())
                return true;
        }
        return false;
    }

    public static ArrayList<HBox> getTilesUnits(ArrayList<Tile> selectedTiles) {
        ArrayList<HBox> hBoxes = new ArrayList<>();
        HashMap<String, Integer> unitIntegerHashMap = new HashMap<>();

        for (Tile selectedTile : selectedTiles)
            for (Unit unit : selectedTile.getUnits())
                if (unit.isForCurrentGovernance())
                    unitIntegerHashMap.merge(unit.getName(), 1, Integer::sum);

        for (String unitName : unitIntegerHashMap.keySet()) {
            Unit unit = getUnitByType(unitName);
            hBoxes.add(new HBox(new ImageView(unit.getImage()), new Label(unitIntegerHashMap.get(unitName).toString())));
        }

        return hBoxes;
    }

    public static Unit getUnitByType(String unitName) {
        try {
            return new Troop(unitName);
        } catch (Exception e) {
            if (unitName.equals("engineer")) return new Engineer();
            else if (unitName.equals("lord")) return new Lord(null);
            else if (Utils.isValidMachineType(unitName)) return new Machine(unitName);
            else return null;
        }
    }

    public static ArrayList<Tile> getUnEmptyTiles(ArrayList<Tile> selectedTiles, String unitType) {
        ArrayList<Tile> unEmptyTiles = new ArrayList<>();
        for (Tile selectedTile : selectedTiles)
            if (selectedTile.getUnits().size() > 0 && tileHasUnitType(selectedTile, unitType))
                unEmptyTiles.add(selectedTile);
        return unEmptyTiles;
    }

    private static boolean tileHasUnitType(Tile selectedTile, String unitType) {
        for (Unit unit : selectedTile.getUnits())
            if (unit.getName().equals(unitType) && unit.isForCurrentGovernance()) return true;
        return false;
    }
}
