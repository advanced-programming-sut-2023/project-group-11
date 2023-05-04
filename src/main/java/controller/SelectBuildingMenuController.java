package controller;

import model.Governance;
import model.Stronghold;
import model.buildings.*;
import model.people.Troops;
import model.AllResource;
import view.enums.messages.SelectBuildingMenuMessages;

import java.util.regex.Matcher;

public class SelectBuildingMenuController {
    public static SelectBuildingMenuMessages checkCreateUnit(Matcher matcher, Matcher buildingMatcher) {

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
            createEngineer((UnitMaker) building, count);
            return SelectBuildingMenuMessages.SUCCESS;
        }

        if (!Utils.isValidUnitType(type))
            return SelectBuildingMenuMessages.INVALID_TYPE;

        Troops troop = new Troops(type);

        if ((troop.isArab() && !building.getName().equals("mercenary tent")) || !building.getName().equals("barracks"))
            return SelectBuildingMenuMessages.CANT_CREATE_HERE;
        if (governance.getGold() < count * troop.getCost())
            return SelectBuildingMenuMessages.NOT_ENOUGH_GOLD;

        AllResource armor = troop.getArmorType();
        AllResource weapon = troop.getWeaponType();

        if ((armor.equals(AllResource.NONE) && !governance.hasEnoughItem(armor, count))
                || (weapon.equals(AllResource.NONE) && !governance.hasEnoughItem(weapon, count)))
            return SelectBuildingMenuMessages.NOT_ENOUGH_RESOURCE;
        createUnit((UnitMaker) building, troop, count);
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

    private static void createUnit(UnitMaker unitMaker, Troops troop, int count) {
        Governance governance = Stronghold.getCurrentGame().getCurrentGovernance();
        governance.setGold(governance.getGold() - troop.getCost() * count);

        if (troop.getArmorType().equals(AllResource.NONE))
            governance.changeResourceAmount(troop.getArmorType(), -count);
        if (troop.getWeaponType().equals(AllResource.NONE))
            governance.changeResourceAmount(troop.getWeaponType(), -count);
    }

    private static void createEngineer(UnitMaker unitMaker, int count) {
        Governance governance = Stronghold.getCurrentGame().getCurrentGovernance();
        governance.setGold(governance.getGold() - 30 * count);

    }
}
