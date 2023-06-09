package controller;

import model.AllResource;
import model.Governance;
import model.Stronghold;
import model.buildings.*;
import model.map.Map;
import model.map.Tile;
import model.people.Engineer;
import model.people.Troop;
import model.people.Unit;
import view.enums.messages.SelectBuildingMenuMessages;

public class SelectBuildingMenuController {
    private static Tile unitCreationTile = null;
    private static boolean unitCreationFlag = false;
    private static final int[] unitCreationCoordinates = new int[2];

    public static SelectBuildingMenuMessages checkCreateUnit(Building building, String type) {
        unitCreationTile = null;
        unitCreationFlag = false;

        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        int x = building.getXCoordinate(), y = building.getYCoordinate();
        int count = 1;
        if (!currentGovernance.hasEnoughPopulation(count)) return SelectBuildingMenuMessages.NOT_ENOUGH_POPULATION;

        if (type.equals("engineer")) {
            Engineer engineer = new Engineer();
            if (currentGovernance.getGold() < engineer.getCost() * count)
                return SelectBuildingMenuMessages.NOT_ENOUGH_GOLD;
            if (!createUnit(building, engineer, null, count)) return SelectBuildingMenuMessages.BAD_UNIT_MAKER_PLACE;
            return SelectBuildingMenuMessages.SUCCESS;
        }

        Troop troop = new Troop(type);

        if (currentGovernance.getGold() < count * troop.getCost()) return SelectBuildingMenuMessages.NOT_ENOUGH_GOLD;

        AllResource armor = troop.getArmorType();
        AllResource weapon = troop.getWeaponType();

        if (!currentGovernance.hasEnoughItem(armor, count) || !currentGovernance.hasEnoughItem(weapon, count))
            return SelectBuildingMenuMessages.NOT_ENOUGH_RESOURCE;
        if (!createUnit(building, troop, type, count)) return SelectBuildingMenuMessages.BAD_UNIT_MAKER_PLACE;
        return SelectBuildingMenuMessages.SUCCESS;
    }

    public static Boolean isUnitMakerSuitable(String unitType, Building building) {
        if (unitType.equals("lord"))
            return false;
        if (building.getName().equals("cathedral"))
            return unitType.equals("black monk");
        if (unitType.equals("black monk"))
            return building.getName().equals("cathedral");
        UnitMaker unitMaker = (UnitMaker) building;
        Troop unit;
        if ((unitType.equals("engineer")))
            return unitMaker.isEngineerMaker();
        if (Utils.isValidMachineType(unitType))
            return false;
        if (Utils.isValidUnitType(unitType)) {
            unit = new Troop(unitType);
            if (unit.isArab())
                return unitMaker.isMercenaryMaker();
            else
                return unitMaker.getName().equals("barracks");
        }
        return true;
    }

    public static boolean hasCommand(int x, int y) {
        Building building = BuildingUtils.getBuilding(x, y);
        return building instanceof UnitMaker
                || building instanceof Tower
                || building instanceof GateHouse;
    }

    public static boolean hasCommand(Building building) {
        return building instanceof UnitMaker
                || building instanceof Tower
                || building instanceof GateHouse || building.getName().equals("cathedral");
    }

    public static boolean isUnitMaker(Building building) {
        return building instanceof UnitMaker || building.getName().equals("cathedral");
    }

    public static boolean isRepairable(Building building) {
        return building instanceof Tower
                || building instanceof GateHouse;
    }

    public static SelectBuildingMenuMessages checkRepair(Building building) {
        Governance governance = Stronghold.getCurrentGame().getCurrentGovernance();

        if (!isRepairable(building)) return SelectBuildingMenuMessages.CANT_REPAIR;
        if (building.getHitPoint() == building.getMaxHitPoint()) return SelectBuildingMenuMessages.NO_NEED_TO_REPAIR;
        if (isEnemyAround(governance, building.getXCoordinate(), building.getYCoordinate(), building.getSize()))
            return SelectBuildingMenuMessages.ENEMY_AROUND;

        int stoneNeededForRepair = (building.getMaxHitPoint() - building.getHitPoint()) / 10;

        if (!governance.hasEnoughItem(AllResource.STONE, stoneNeededForRepair))
            return SelectBuildingMenuMessages.NOT_ENOUGH_RESOURCE;

        governance.removeFromStorage(AllResource.STONE, stoneNeededForRepair);
        building.repair();

        return SelectBuildingMenuMessages.SUCCESS;
    }

    private static boolean isEnemyAround(Governance governance, int X, int Y, int size) {
        Tile[][] tiles = Stronghold.getCurrentGame().getMap().getTiles();
        int range = 7;
        for (int x = X; x < X + size; x++)
            for (int y = Y; y < Y + size; y++)
                for (int i = -range; i <= range; i++)
                    for (int j = -Math.abs(range - i); j <= Math.abs(range - i); j++)
                        if (Utils.isValidCoordinates(Stronghold.getCurrentGame().getMap(), x + i, y + j))
                            if (tiles[x + i][y + j].hasEnemy(governance))
                                return true;
        return false;
    }

    private static boolean createUnit(Building unitMaker, Unit unit, String unitType, int count) {
        Governance governance = Stronghold.getCurrentGame().getCurrentGovernance();
        setUnitCoordinates(unitMaker);

        if (unitCreationTile == null) return false;

        governance.setGold(governance.getGold() - unit.getCost() * count);
        if (unit instanceof Troop troop) createTroop(troop, unitType, count, governance);
        else createEngineer(count);

        return true;
    }

    private static void createTroop(Troop troop, String unitType, int count, Governance governance) {
        governance.removeFromStorage(troop.getWeaponType(), count);
        governance.removeFromStorage(troop.getArmorType(), count);

        for (int i = 0; i < count; i++) new Troop(unitType).initializeUnit(unitCreationTile);
    }

    private static void createEngineer(int count) {
        for (int i = 0; i < count; i++) new Engineer().initializeUnit(unitCreationTile);
    }

    private static void setUnitCoordinates(Building unitMaker) {
        Map map = Stronghold.getCurrentGame().getMap();
        int unitMakerXCoordinate = unitMaker.getXCoordinate();
        int unitMakerYCoordinate = unitMaker.getYCoordinate();
        int unitMakerSize = unitMaker.getSize();

        findUnitCreationCoordinatesXConstant(map, unitMakerXCoordinate, unitMakerYCoordinate, unitMakerSize);
        findUnitCreationCoordinatesXConstant(map, unitMakerXCoordinate + unitMakerSize + 1,
                unitMakerYCoordinate, unitMakerSize);

        findUnitCreationCoordinatesYConstant(map, unitMakerYCoordinate, unitMakerXCoordinate, unitMakerSize);
        findUnitCreationCoordinatesYConstant(map, unitMakerYCoordinate + unitMakerSize + 1,
                unitMakerXCoordinate, unitMakerSize);
    }

    private static void findUnitCreationCoordinatesXConstant(Map map, int constantCoordination, int changingCoordination, int unitMakerSize) {
        for (int i = changingCoordination - 1; i <= changingCoordination + unitMakerSize + 1 && !unitCreationFlag; i++)
            if (Utils.isValidCoordinates(map, constantCoordination - 1, i)) {
                unitCreationTile = map.getTile(constantCoordination - 1, i);
                if (canCreateUnit()) {
                    unitCreationFlag = true;
                    unitCreationCoordinates[0] = constantCoordination;
                    unitCreationCoordinates[1] = i;
                }
            }
    }

    private static void findUnitCreationCoordinatesYConstant(Map map, int constantCoordination, int changingCoordination, int unitMakerSize) {
        for (int i = changingCoordination - 1; i <= changingCoordination + unitMakerSize + 1 && !unitCreationFlag; i++)
            if (Utils.isValidCoordinates(map, i, constantCoordination - 1)) {
                unitCreationTile = map.getTile(i, constantCoordination - 1);
                if (canCreateUnit()) {
                    unitCreationFlag = true;
                    unitCreationCoordinates[0] = i;
                    unitCreationCoordinates[1] = constantCoordination;
                }
            }
    }

    private static boolean canCreateUnit() {
        return unitCreationTile.getTexture().isSuitableForUnit() &&
                !unitCreationTile.hasBuilding() &&
                (unitCreationTile.getUnits().size() == 0 ||
                        unitCreationTile.getUnits().get(0).getOwner().equals(Stronghold.getCurrentGame().getCurrentGovernance()));
    }

    public static boolean isShop(Building building) {
        return building.getName().equals("shop");
    }

    public static boolean isKeep(Building building) {
        return building instanceof Keep;
    }
}