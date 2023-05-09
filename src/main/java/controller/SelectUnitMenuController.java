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
import model.people.enums.UnitState;
import view.enums.messages.SelectUnitMenuMessages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;

public class SelectUnitMenuController {
    public static SelectUnitMenuMessages checkMoveUnit(Matcher matcher, int[] currentLocation, String unitType) {
        if (!Utils.isValidCommandTags(matcher, "xCoordinate", "yCoordinate"))
            return SelectUnitMenuMessages.INVALID_COMMAND;

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
                (!(map.getTile(destinationX, destinationY).getBuilding() instanceof Climbable) ||
                        !((Climbable) map.getTile(destinationX, destinationY).getBuilding()).isClimbable()))
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

        //TODO:1 set damaging concepts (Fire - building damaging - multi-unit damaging - tower increasing range)
        attack(selectedUnits, unitType, targetTile);

        return SelectUnitMenuMessages.SUCCESS;
    }

    public static void setPatrolUnit(Matcher matcher, int[] currentLocation, String unitType) {
        Map map = Stronghold.getCurrentGame().getMap();
        int destinationX = Integer.parseInt(matcher.group("xCoordinate"));
        int destinationY = Integer.parseInt(matcher.group("yCoordinate"));
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];
        ArrayList<Units> selectedUnits = map.getTile(destinationX, destinationY).getUnitsByType(unitType);
        //TODO:2 units in destination of the same type should not patrol

        for (Units unit : selectedUnits) {
            unit.setPatrolOrigin(new int[]{destinationX, destinationY});
            unit.setPatrolDestination(new int[]{currentX, currentY});
        }
    }

    public static void patrolUnit(ArrayList<Units> units) {
        Map map = Stronghold.getCurrentGame().getMap();
        Units unit = units.get(0);
        String unitType = unit.getName();
        Path shortestPath;
        int currentX = unit.getPatrolOrigin()[0];
        int currentY = unit.getPatrolOrigin()[1];
        int destinationX = unit.getPatrolDestination()[0];
        int destinationY = unit.getPatrolDestination()[1];

        if (!isValidForMoving(map, currentX, currentY, destinationX, destinationY)) stopPatrol(units);
        if ((shortestPath = findRootToDestination(map, unitType, currentX, currentY, destinationX, destinationY)) != null) {
            for (Units unit1 : units) {
                unit1.setPatrolOrigin(new int[]{destinationX, destinationY});
                unit1.setPatrolDestination(new int[]{currentX, currentY});
            }
            moveUnits(map, unitType, shortestPath, unit.getPatrolOrigin(), destinationX, destinationY);
        } else stopPatrol(units);
    }

    public static SelectUnitMenuMessages checkStopPatrol(int[] currentLocation, String unitType) {
        Map map = Stronghold.getCurrentGame().getMap();
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];
        ArrayList<Units> selectedUnits = map.getTile(currentX, currentY).getUnitsByType(unitType);

        if (!selectedUnits.get(0).isPatrolling()) return SelectUnitMenuMessages.NOT_PATROLLING;

        stopPatrol(selectedUnits);
        return SelectUnitMenuMessages.SUCCESS;
    }

    private static void stopPatrol(ArrayList<Units> units) {
        for (Units unit : units) unit.stopPatrol();
    }

    public static SelectUnitMenuMessages checkSetUnitState(Matcher matcher, int[] currentLocation, String unitType) {
        Map map = Stronghold.getCurrentGame().getMap();
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];
        ArrayList<Units> selectedUnits = map.getTile(currentX, currentY).getUnitsByType(unitType);
        String state = matcher.group("state");

        if (!state.matches("standing|defensive|offensive")) return SelectUnitMenuMessages.INVALID_STATE;
        for (Units unit : selectedUnits) unit.setUnitState(UnitState.valueOf(state.toUpperCase()));
        return SelectUnitMenuMessages.SUCCESS;
    }

    public static SelectUnitMenuMessages checkPourOil(Matcher matcher) {
        return null;
    }

    public static SelectUnitMenuMessages checkDigTunnel(Matcher matcher, int[] currentLocation, String unitType) {
        Map map = Stronghold.getCurrentGame().getMap();
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];
        Units unit = map.getTile(currentX, currentY).getUnitsByType(unitType).get(0);

        String direction = matcher.group("direction");
        if (!unit.getName().equals("tunneler")) return SelectUnitMenuMessages.INVALID_UNIT_TYPE_TO_DIG_TUNNEL;
        if (!(direction.equals("right") ||
                direction.equals("up") ||
                direction.equals("left") ||
                direction.equals("down"))) return SelectUnitMenuMessages.INVALID_DIRECTION;


        digTunnel(map, unit, currentX, currentY, direction);
        return SelectUnitMenuMessages.SUCCESS;
    }

    private static void digTunnel(Map map, Units tunneler, int currentX, int currentY, String direction) {
        Tile currentTile = map.getTile(currentX, currentY);
        tunneler.removeFromGame(currentTile, tunneler.getOwner());

        //TODO:1 make the function cleaner
        //up
        for (int i = 1; i <= 10 && Utils.isValidCoordinates(map, currentX + i, currentY); i++) {
            currentTile = map.getTile(currentX + i, currentY);
            Building building = currentTile.getBuilding();
            if (!building.getOwner().equals(tunneler.getOwner()))
                destroyBuilding(map, building);
        }
        //down
        for (int i = 1; i <= 10 && Utils.isValidCoordinates(map, currentX - i, currentY); i++) {
            currentTile = map.getTile(currentX - i, currentY);
            Building building = currentTile.getBuilding();
            if (!building.getOwner().equals(tunneler.getOwner()))
                destroyBuilding(map, building);
        }
        //right
        for (int i = 1; i <= 10 && Utils.isValidCoordinates(map, currentX, currentY + i); i++) {
            currentTile = map.getTile(currentX, currentY + i);
            Building building = currentTile.getBuilding();
            if (!building.getOwner().equals(tunneler.getOwner()))
                destroyBuilding(map, building);
        }
        //left
        for (int i = 1; i <= 10 && Utils.isValidCoordinates(map, currentX, currentY - i); i++) {
            currentTile = map.getTile(currentX, currentY - i);
            Building building = currentTile.getBuilding();
            if (!building.getOwner().equals(tunneler.getOwner()))
                destroyBuilding(map, building);
        }
    }

    public static SelectUnitMenuMessages checkBuildMachine(Matcher matcher, int[] currentLocation, String unitType) {
        if (!unitType.equals("engineer"))
            return SelectUnitMenuMessages.INVALID_COMMAND;

        String machineType = Utils.removeDoubleQuotation(matcher.group("machineType"));
        Governance governance = Stronghold.getCurrentGame().getCurrentGovernance();

        if (!Utils.isValidMachineType(machineType)) return SelectUnitMenuMessages.INVALID_MACHINE_TYPE;

        Machine machine = new Machine(machineType);
        machine.setLocation(currentLocation);

        if (governance.getGold() < machine.getCost()) return SelectUnitMenuMessages.NOT_ENOUGH_GOLD;

        Tile tile = Stronghold.getCurrentGame().getMap().getTile(currentLocation[0], currentLocation[1]);

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
        ArrayList<Units> selectedUnits = tile.getUnitsByType(unitType);
        Governance governance = selectedUnits.get(0).getOwner();
        int currentPopulation = governance.getCurrentPopulation();
        int maxPopulation = governance.getMaxPopulation();

        for (Units unit : selectedUnits) unit.removeFromGame(tile, governance);

        governance.changeCurrentPopulation(Math.min(selectedUnits.size(), maxPopulation - currentPopulation));
    }

    private static void moveUnits(Map map, String unitType, Path shortestPath, int[] currentLocation, int destinationX, int destinationY) {
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];
        ArrayList<Units> selectedUnits = map.getTile(currentX, currentY).getUnitsByType(unitType);
        map.getTile(currentX, currentY).clearUnitsByType(selectedUnits);

        setLeftMoves(shortestPath, selectedUnits);
        setLocation(selectedUnits, destinationX, destinationY);
        applyPathEffects(map, shortestPath, selectedUnits);

        map.getTile(destinationX, destinationY).getUnits().addAll(selectedUnits);
        currentLocation[0] = destinationX;
        currentLocation[1] = destinationY;
    }

    private static Path findRootToDestination(Map map, String unitType, int currentX, int currentY, int destinationX, int destinationY) {
        int speed = minimumSpeed(map.getTile(currentX, currentY).getUnitsByType(unitType));
        ArrayList<Path> paths = new ArrayList<>();
        Path path = new Path();

        path.addToPath(new int[]{currentX, currentY});

        pathDFS(map, unitType, currentX + 1, currentY, destinationX, destinationY, speed, paths, path);
        pathDFS(map, unitType, currentX - 1, currentY, destinationX, destinationY, speed, paths, path);
        pathDFS(map, unitType, currentX, currentY + 1, destinationX, destinationY, speed, paths, path);
        pathDFS(map, unitType, currentX, currentY - 1, destinationX, destinationY, speed, paths, path);

        if (paths.size() == 1) return null;
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

        if (currentBuilding instanceof Trap || currentBuilding instanceof Keep)
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

        if (previousTile.getBuilding().getName().equals("stairs"))
            return true;
        else if (previousTile.getBuilding() instanceof GateHouse)
            return true;
        else if (previousTile.getBuilding() instanceof Keep)
            return true;
        else if (currentTile.getUnitsByType("ladderman").size() != 0)
            return true;
        else if (currentTile.getUnitsByType("siege tower").size() != 0)
            return true;
        else
            return unitType.equals("assassin");
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

    private static void setLocation(ArrayList<Units> selectedUnits, int destinationX, int destinationY) {
        for (Units unit : selectedUnits)
            unit.setLocation(new int[]{destinationX, destinationY});
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
        Map map = Stronghold.getCurrentGame().getMap();

        for (Units unit : selectedUnits) {
            Tile tile = map.getTile(currentBuilding.getXCoordinate(), currentBuilding.getYCoordinate());
            unit.setHp(unit.getHp() - currentBuilding.getDamage());
            if (unit.getHp() <= 0) {
                unit.removeFromGame(tile, unit.getOwner());
                selectedUnits.remove(unit);
            }
        }
        currentBuilding.removeFromGame(map, currentBuilding.getOwner());
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
        for (Units unit : selectedUnits) {
            if (((Attacker) unit).hasAttacked())
                removings.add(unit);
        }
        selectedUnits.removeAll(removings);

        return selectedUnits.size() == 0;
    }

    private static void attack(ArrayList<Units> selectedUnits, String unitType, Tile targetTile) {
        boolean onlyBuilding = false;
        boolean onlyUnits = false;

        if (isValidUnitForGroundAttack(unitType)) {
            if (targetTile.getBuilding() != null) onlyBuilding = true;
            else if (!unitType.equals("battle ram")) onlyUnits = true;
        } else {
            if (unitType.equals("trebuchets") || unitType.equals("catapults")) {
                if (targetTile.getBuilding() != null) onlyBuilding = true;
                else onlyUnits = true;
            } else {
                if (targetTile.getUnits().size() != 0) onlyUnits = true;
            }
        }

        if (onlyBuilding) attackBuilding(selectedUnits, targetTile);
        else if (onlyUnits) attackUnits(selectedUnits, targetTile);
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
            //TODO:2 rearrange the climbablity
        }
    }

    private static void destroyBuilding(Map map, Building building) {
        building.removeFromGame(map, building.getOwner());
    }

    private static void attackUnits(ArrayList<Units> selectedUnits, Tile targetTile) {
        int attackerDamage = selectedUnits.size() * ((Attacker) selectedUnits.get(0)).getDamage();

        for (Units unit : targetTile.getUnits())
            unit.setHp(unit.getHp() - attackerDamage);
        setAttackedTrue(selectedUnits);
        removeDeadUnits(targetTile);
        //TODO:1 set reacting to attacks
    }

    private static void removeDeadUnits(Tile targetTile) {
        for (Units unit : targetTile.getUnits())
            if (unit.getHp() <= 0) {
                if (unit instanceof Machine machine)
                    for (Engineer engineer : machine.getEngineers())
                        engineer.getOwner().removeUnit(engineer);
                unit.removeFromGame(targetTile, unit.getOwner());
            }
    }

    private static void setAttackedTrue(ArrayList<Units> selectedUnits) {
        for (Units unit : selectedUnits) ((Attacker) unit).setAttacked(true);
    }

    private static void buildMachine(Machine machine, Tile tile, Governance governance) {
        governance.setGold(governance.getGold() - machine.getCost());
        machine.setActive(true);

        for (int i = 0; i < machine.getEngineersNeededToActivate(); i++) {
            Engineer engineer = (Engineer) tile.getUnitsByType("engineer").get(0);
            machine.addEngineer(engineer);
            tile.getUnits().remove(engineer);
        }
        tile.getUnits().add(machine);
    }

    private static boolean isValidForMoving(Map map, int currentX, int currentY, int destinationX, int destinationY) {
        return (!notValidTextureForMoving(map.getTile(destinationX, destinationY)) &&
                isValidDestinationSameOwnerUnits(map.getTile(currentX, currentY), map.getTile(destinationX, destinationY)));
    }
}
