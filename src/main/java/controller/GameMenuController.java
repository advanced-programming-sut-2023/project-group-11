package controller;

import model.Governance;
import model.Stronghold;
import model.buildings.Building;
import model.buildings.Trap;
import model.resources.AllResource;
import view.enums.messages.GameMenuMessages;

import java.util.regex.Matcher;

public class GameMenuController {
    public static String showPopularity(Matcher matcher) {
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        String output = "";

        if (matcher.group("factors") == null) output += currentGovernance.getPopularity();
        else {
            output += "Food: " + currentGovernance.getFoodFactor() + '\n';
            output += "Tax: " + currentGovernance.getTaxFactor() + '\n';
            output += "Religious: " + currentGovernance.getReligiousFactor() + '\n';
            output += "Fear: " + currentGovernance.getFearFactor() + '\n';
        }

        return output;
    }

    public static String showFoodList() {
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        String output = "";

        output += "Bread: " + currentGovernance.getAllResources().get(AllResource.BREAD) + '\n';
        output += "Apple: " + currentGovernance.getAllResources().get(AllResource.APPLE) + '\n';
        output += "Cheese: " + currentGovernance.getAllResources().get(AllResource.CHEESE) + '\n';
        output += "Meat: " + currentGovernance.getAllResources().get(AllResource.MEAT) + '\n';

        return output;
    }

    public static GameMenuMessages checkChangeFoodRate(Matcher matcher) {
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        int rateNumber = Integer.parseInt(matcher.group("rateNumber"));

        if (rateNumber < -2 || rateNumber > 2) return GameMenuMessages.INVALID_RATE;

        currentGovernance.setFoodRate(rateNumber);
        currentGovernance.setFoodFactor(rateNumber * 4);

        return GameMenuMessages.SUCCESS;
    }

    public static String showFoodRate() {
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        return "Food rate: " + currentGovernance.getFoodRate();
    }

    public static GameMenuMessages checkChangeTaxRate(Matcher matcher) {
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        int rateNumber = Integer.parseInt(matcher.group("rateNumber"));

        if (rateNumber < -3 || rateNumber > 8) return GameMenuMessages.INVALID_RATE;

        currentGovernance.setTaxRate(rateNumber);

        if (rateNumber <= 0) currentGovernance.setTaxFactor(-2 * rateNumber + 1);
        else if (rateNumber <= 4) currentGovernance.setTaxFactor(-2 * rateNumber);
        else currentGovernance.setTaxFactor(-4 * rateNumber + 8);

        return GameMenuMessages.SUCCESS;
    }

    public static String showTaxRate() {
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        return "Tax rate: " + currentGovernance.getTaxRate();
    }

    public static GameMenuMessages checkChangeFearRate(Matcher matcher) { //TODO: not in the actual game!
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        int rateNumber = Integer.parseInt(matcher.group("rateNumber"));

        if (rateNumber < -5 || rateNumber > 5) return GameMenuMessages.INVALID_RATE;

        currentGovernance.setFearFactor(rateNumber);
        return GameMenuMessages.SUCCESS;
    }

    public static GameMenuMessages checkDropBuilding(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "xGroup", "yGroup", "typeGroup"))
            return GameMenuMessages.INVALID_COMMAND;
        int x = Integer.parseInt(matcher.group("xGroup"));
        int y = Integer.parseInt(matcher.group("yGroup"));
        String type = matcher.group("typeGroup");
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        Building building = BuildingUtils.getBuildingByType(type);
        if (!BuildingUtils.isValidBuildingType(type))
            return GameMenuMessages.INVALID_BUILDING_TYPE;
        int size = building.getSize();
        if (!BuildingUtils.isValidCoordinates(Stronghold.getCurrentGame().getMap(), x, y, size))
            return GameMenuMessages.INVALID_COORDINATE;
        if (!BuildingUtils.isMapEmpty(x, y, size))
            return GameMenuMessages.CANT_BUILD_HERE;
        if (!BuildingUtils.isTextureSuitable(type, x, y, size))
            return GameMenuMessages.CANT_BUILD_HERE;
        if (building.getGoldCost() > currentGovernance.getGold())
            return GameMenuMessages.NOT_ENOUGH_MONEY;
        if (building.getResourceCostType() != null) {
            if (!currentGovernance.hasEnoughItem(building.getResourceCostType(), building.getResourceCostNumber()))
                return GameMenuMessages.NOT_ENOUGH_RESOURCE;
        }
        BuildingUtils.build(building, x, y, size);
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

    public static String selectBuildingDetails(Matcher matcher){
        int x = Integer.parseInt(matcher.group("xGroup"));
        int y = Integer.parseInt(matcher.group("yGroup"));
        Building building = Stronghold.getCurrentGame().getMap().getTile(x,y).getBuilding();
        return building.toString();
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
