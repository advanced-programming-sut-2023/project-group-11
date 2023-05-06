package controller;

import model.AllResource;
import model.Governance;
import model.Stronghold;
import model.buildings.*;
import model.map.Map;
import model.map.Tile;
import model.people.Engineer;
import model.people.Troops;
import view.enums.messages.SelectBuildingMenuMessages;

import java.util.regex.Matcher;

public class SelectBuildingMenuController {
    private static Tile unitCreationTile = null;
    private static boolean unitCreationFlag = false;
    private static final int[] unitCreationCoordinates = new int[2];

    public static SelectBuildingMenuMessages checkCreateUnit(Matcher matcher, int x, int y) {
        unitCreationTile = null;
        unitCreationFlag = false;

        if (!Utils.isValidCommandTags(matcher, "typeGroup", "countGroup"))
            return SelectBuildingMenuMessages.INVALID_COMMAND;

        String type = Utils.removeDoubleQuotation(matcher.group("typeGroup"));
        int count = Integer.parseInt(matcher.group("countGroup"));
        Governance governance = Stronghold.getCurrentGame().getCurrentGovernance();
        Building building = BuildingUtils.getBuilding(x, y);

        if (!Utils.isValidUnitType(type)) return SelectBuildingMenuMessages.INVALID_TYPE;
        if (!isUnitMaker(building)) return SelectBuildingMenuMessages.CANT_CREATE_HERE;

        UnitMaker unitMaker = (UnitMaker) building;

        if (type.equals("engineer")) {
            Engineer engineer = new Engineer();
            if (!unitMaker.isEngineerMaker()) return SelectBuildingMenuMessages.CANT_CREATE_HERE;
            if (governance.getGold() < engineer.getCost() * count) return SelectBuildingMenuMessages.NOT_ENOUGH_GOLD;
            if (!createEngineer(unitMaker, engineer, count)) return SelectBuildingMenuMessages.BAD_UNIT_MAKER_PLACE;
            return SelectBuildingMenuMessages.SUCCESS;
        }

        if (type.equals("black monk") && !building.getName().equals("cathedral"))
            return SelectBuildingMenuMessages.CANT_CREATE_HERE;

        Troops troop = new Troops(type);

        if ((troop.isArab() && !unitMaker.isMercenaryMaker()) || !building.getName().equals("barracks"))
            return SelectBuildingMenuMessages.CANT_CREATE_HERE;
        if (governance.getGold() < count * troop.getCost()) return SelectBuildingMenuMessages.NOT_ENOUGH_GOLD;

        AllResource armor = troop.getArmorType();
        AllResource weapon = troop.getWeaponType();

        if (!governance.hasEnoughItem(armor, count) || !governance.hasEnoughItem(weapon, count))
            return SelectBuildingMenuMessages.NOT_ENOUGH_RESOURCE;
        if (!createUnit(unitMaker, troop, type, count)) return SelectBuildingMenuMessages.BAD_UNIT_MAKER_PLACE;
        return SelectBuildingMenuMessages.SUCCESS;
    }

    public static boolean hasCommand(int x, int y) {
        Building building = BuildingUtils.getBuilding(x, y);
        return building instanceof UnitMaker
                || building instanceof Tower
                || building instanceof GateHouse
                || building instanceof Trap;
    }

    public static boolean isUnitMaker(Building building) {
        return building instanceof UnitMaker || building.getName().equals("cathedral");
    }

    public static boolean isRepairable(Building building) {
        return building instanceof Tower
                || building instanceof GateHouse;
    }

    public static SelectBuildingMenuMessages checkRepair(int x, int y) {
        Building building = BuildingUtils.getBuilding(x, y);
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

    private static boolean createUnit(UnitMaker unitMaker, Troops troop, String unitType, int count) {
        Governance governance = Stronghold.getCurrentGame().getCurrentGovernance();
        setUnitCoordinates(unitMaker);

        if (unitCreationTile == null) return false;

        governance.setGold(governance.getGold() - troop.getCost() * count);
        governance.removeFromStorage(troop.getWeaponType(), count);
        governance.removeFromStorage(troop.getArmorType(), count);

        for (int i = 0; i < count; i++) unitCreationTile.getUnits().add(new Troops(unitType));

        return true;
    }

    private static boolean createEngineer(UnitMaker unitMaker, Engineer engineer, int count) {
        Governance governance = Stronghold.getCurrentGame().getCurrentGovernance();
        setUnitCoordinates(unitMaker);

        if (unitCreationTile == null) return false;

        governance.setGold(governance.getGold() - engineer.getCost() * count);

        for (int i = 0; i < count; i++) unitCreationTile.getUnits().add(new Engineer());

        return true;
    }

    private static void setUnitCoordinates(UnitMaker unitMaker) {
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
                if (!BuildingUtils.isBuildingInTile(unitCreationTile.getBuilding())) {
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
                if (!BuildingUtils.isBuildingInTile(unitCreationTile.getBuilding())) {
                    unitCreationFlag = true;
                    unitCreationCoordinates[0] = i;
                    unitCreationCoordinates[1] = constantCoordination;
                }
            }
    }

    public static int[] getUnitCreationCoordinates() {
        return unitCreationCoordinates;
    }
}