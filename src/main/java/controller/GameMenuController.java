package controller;

import model.Governance;
import model.Stronghold;
import model.buildings.Building;
import model.buildings.Trap;
import view.enums.messages.GameMenuMessages;

import java.util.regex.Matcher;

public class GameMenuController {
    private static String showPopularity() {
        //show popularity factors and show popularity
        return null;
    }

    private static String showFoodList() {
        return null;
    }

    private static GameMenuMessages checkChangeFoodRate(Matcher matcher) {
        return null;
    }

    private static String showFoodRate() {
        return null;
    }

    private static GameMenuMessages checkChangeTaxRate(Matcher matcher) {
        return null;
    }

    private static String showTaxRate() {
        return null;
    }

    private static GameMenuMessages checkChangeFearRate(Matcher matcher) {
        return null;
    }

    private static String showFearRate() {
        return null;
    }

    public static GameMenuMessages checkDropBuilding(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "xGroup", "yGroup", "typeGroup"))
            return GameMenuMessages.INVALID_COMMAND;
        int x = Integer.parseInt(matcher.group("xGroup"));
        int y = Integer.parseInt(matcher.group("yGroup"));
        String type = matcher.group("typeGroup");
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        if (!BuildingUtils.isValidBuildingType(type))
            return GameMenuMessages.INVALID_BUILDING_TYPE;
        int size = BuildingUtils.getBuildingSizeByName(type);
        if (!BuildingUtils.isValidCoordinates(Stronghold.getCurrentGame().getMap(), x, y, size))
            return GameMenuMessages.INVALID_COORDINATE;
        if (!BuildingUtils.isMapEmpty(x, y, size))
            return GameMenuMessages.CANT_BUILD_HERE;
        if (!BuildingUtils.isTextureSuitable(type, x, y, size))
            return GameMenuMessages.CANT_BUILD_HERE;
        if (BuildingUtils.getBuildingGoldCostByName(type) > currentGovernance.getGold())
            return GameMenuMessages.NOT_ENOUGH_MONEY;
        if (BuildingUtils.getBuildingResourceCostTypeByName(type) != null) {
            if (!currentGovernance.hasEnoughItem(BuildingUtils.getBuildingResourceCostTypeByName(type),
                    BuildingUtils.getBuildingResourceCostByName(type))) {
                return GameMenuMessages.NOT_ENOUGH_RESOURCE;
            }
        }
        BuildingUtils.build(type, x, y, size);
        return GameMenuMessages.SUCCESS;
    }

    public static GameMenuMessages checkSelectBuilding(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "xGroup", "yGroup"))
            return GameMenuMessages.INVALID_COMMAND;
        int x = Integer.parseInt(matcher.group("xGroup"));
        int y = Integer.parseInt(matcher.group("yGroup"));
        if (!Utils.isValidCoordinates(Stronghold.getCurrentGame().getMap(), x, y))
            return GameMenuMessages.INVALID_COORDINATE;
        Building building = Stronghold.getCurrentGame().getMap().getTile(x,y).getBuilding();
        Governance governance = Stronghold.getCurrentGame().getCurrentGovernance();
        if(building == null)
            return GameMenuMessages.NO_BUILDING_HERE;
        if(!building.getOwner().equals(governance))
            if(building instanceof Trap)
                return GameMenuMessages.NO_BUILDING_HERE;
            else
                return GameMenuMessages.NOT_YOUR_BUILDING;
        return GameMenuMessages.SUCCESS;
        //TODO: select building after commands need a structure to implement...
    }

    private static void nextTurn() {
    }

    private static void updatePopulation() {

    }

    private static void updateSoldiers() {

    }

    private static void updateStorage() {

    }

    private static void updatePopularityRate() {

    }

    private static void fight() {

    }

    public static GameMenuMessages checkShowMap(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "xCoordinate", "yCoordinate"))
            return GameMenuMessages.INVALID_COMMAND;
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));
        if (!Utils.isValidCoordinates(Stronghold.getCurrentGame().getMap(), x, y))
            return GameMenuMessages.INVALID_COORDINATE;
        return GameMenuMessages.SUCCESS;
    }
}
