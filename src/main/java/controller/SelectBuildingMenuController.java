package controller;

import model.Governance;
import model.Stronghold;
import model.buildings.*;
import model.map.Map;
import model.map.Tile;
import model.people.Engineer;
import model.people.Troops;
import model.people.enums.TroopTypes;
import model.AllResource;
import view.enums.messages.SelectBuildingMenuMessages;

import java.util.regex.Matcher;

public class SelectBuildingMenuController {
    private static Tile unitCreationTile = null;
    private static boolean unitCreationFlag = false;

    public static SelectBuildingMenuMessages checkCreateUnit(Matcher matcher, Matcher buildingMatcher) {
        unitCreationTile = null;
        unitCreationFlag = false;

        if (!Utils.isValidCommandTags(matcher, "typeGroup", "countGroup"))
            return SelectBuildingMenuMessages.INVALID_COMMAND;

        String type = matcher.group("type");
        int count = Integer.parseInt(matcher.group("count"));
        Governance governance = Stronghold.getCurrentGame().getCurrentGovernance();
        Building building = BuildingUtils.getBuilding(buildingMatcher);

        if (!isUnitMaker(building)) return SelectBuildingMenuMessages.CANT_CREATE_HERE;
        if (type.equals("engineer")) {
            if (!building.getName().equals("engineer guild")) return SelectBuildingMenuMessages.CANT_CREATE_HERE;
            if (governance.getGold() < count * 30) return SelectBuildingMenuMessages.NOT_ENOUGH_GOLD;
            if (!createEngineer((UnitMaker) building, count)) return SelectBuildingMenuMessages.BAD_UNIT_MAKER_PLACE;
            return SelectBuildingMenuMessages.SUCCESS;
        }
        type = type.replace(" ", "_");
        try {
            TroopTypes.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return SelectBuildingMenuMessages.INVALID_TYPE;
        }

        Troops troop = new Troops(type);

        if ((troop.isArab() && !building.getName().equals("mercenary tent")) || !building.getName().equals("barracks"))
            return SelectBuildingMenuMessages.CANT_CREATE_HERE;
        if (governance.getGold() < count * troop.getCost())
            return SelectBuildingMenuMessages.NOT_ENOUGH_GOLD;

        AllResource armor = troop.getArmorType();
        AllResource weapon = troop.getWeaponType();

        if (!governance.hasEnoughItem(armor, count) || !governance.hasEnoughItem(weapon, count))
            return SelectBuildingMenuMessages.NOT_ENOUGH_RESOURCE;
        if (!createUnit((UnitMaker) building, troop, count)) return SelectBuildingMenuMessages.BAD_UNIT_MAKER_PLACE;
        return SelectBuildingMenuMessages.SUCCESS;
    }

    public static boolean hasCommand(Matcher matcher) {
        Building building = BuildingUtils.getBuilding(matcher);
        return building instanceof UnitMaker
                || building instanceof Tower
                || building instanceof GateHouse
                || building instanceof Trap;
    }

    public static boolean isUnitMaker(Building building) {
        return building instanceof UnitMaker;
    }

    public static boolean isRepairable(Building building) {
        return building instanceof Tower
                || building instanceof GateHouse;
    }

    public static SelectBuildingMenuMessages checkRepair(Matcher buildingMatcher) {
        Building building = BuildingUtils.getBuilding(buildingMatcher);
        Governance governance = Stronghold.getCurrentGame().getCurrentGovernance();

        if (!isRepairable(building)) return SelectBuildingMenuMessages.CANT_REPAIR;
        if (building.getHitPoint() == building.getMaxHitPoint()) return SelectBuildingMenuMessages.NO_NEED_TO_REPAIR;
        if (isEnemyAround()) return SelectBuildingMenuMessages.ENEMY_AROUND;

        int stoneNeededForRepair = (building.getMaxHitPoint() - building.getHitPoint()) / 10;

        if (!governance.hasEnoughItem(AllResource.STONE, stoneNeededForRepair))
            return SelectBuildingMenuMessages.NOT_ENOUGH_RESOURCE;

        governance.removeFromStorage(AllResource.STONE, stoneNeededForRepair);
        building.repair();

        return SelectBuildingMenuMessages.SUCCESS;
    }

    private static boolean isEnemyAround() {
        //TODO: implement
        return false;
    }

    private static boolean createUnit(UnitMaker unitMaker, Troops troop, int count) {
        Governance governance = Stronghold.getCurrentGame().getCurrentGovernance();
        setUnitCoordinates(unitMaker);

        if (unitCreationTile == null) return false;

        governance.setGold(governance.getGold() - troop.getCost() * count);
        governance.removeFromStorage(troop.getWeaponType(), count);
        governance.removeFromStorage(troop.getArmorType(), count);

        unitCreationTile.getUnits().add(troop);

        return true;
    }

    private static boolean createEngineer(UnitMaker unitMaker, int count) {
        Governance governance = Stronghold.getCurrentGame().getCurrentGovernance();
        setUnitCoordinates(unitMaker);

        if (unitCreationTile == null) return false;

        Engineer engineer = new Engineer();

        governance.setGold(governance.getGold() - engineer.getCost() * count);
        unitCreationTile.getUnits().add(engineer);

        return true;
    }

    private static void setUnitCoordinates(UnitMaker unitMaker) {
        Map map = Stronghold.getCurrentGame().getMap();
        int unitMakerXCoordinate = unitMaker.getXCoordinate();
        int unitMakerYCoordinate = unitMaker.getYCoordinate();
        int unitMakerSize = unitMaker.getSize();

        findUnitCreationCoordinates(map, unitMakerXCoordinate, unitMakerYCoordinate, unitMakerSize);
        findUnitCreationCoordinates(map, unitMakerYCoordinate, unitMakerXCoordinate, unitMakerSize);
    }

    private static void findUnitCreationCoordinates(Map map, int constantCoordination, int changingCoordination, int unitMakerSize) {
        for (int i = changingCoordination - 1; i < changingCoordination + unitMakerSize && !unitCreationFlag; i++)
            if (Utils.isValidCoordinates(map, constantCoordination - 1, i)) {
                unitCreationTile = map.getTile(constantCoordination - 1, i);
                if (!BuildingUtils.isBuildingInTile(unitCreationTile.getBuilding())) unitCreationFlag = true;
            }

        for (int i = changingCoordination - 1; i < changingCoordination + unitMakerSize && !unitCreationFlag; i++)
            if (Utils.isValidCoordinates(map, constantCoordination + unitMakerSize, i)) {
                unitCreationTile = map.getTile(constantCoordination + unitMakerSize, i);
                if (!BuildingUtils.isBuildingInTile(unitCreationTile.getBuilding())) unitCreationFlag = true;
            }
    }
}
