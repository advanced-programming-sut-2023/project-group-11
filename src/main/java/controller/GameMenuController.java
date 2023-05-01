package controller;

import model.Game;
import model.Governance;
import model.Path;
import model.Stronghold;
import model.buildings.Building;
import model.buildings.Trap;
import model.map.Map;
import model.map.Texture;
import model.map.Tile;
import model.resources.AllResource;
import view.enums.messages.GameMenuMessages;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class GameMenuController {
    private static Game currentGame;

    public static String showPopularity(Matcher matcher) {
        Governance currentGovernance = currentGame.getCurrentGovernance();
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
        Governance currentGovernance = currentGame.getCurrentGovernance();
        String output = "";

        output += "Bread: " + currentGovernance.getAllResources().get(AllResource.BREAD) + '\n';
        output += "Apple: " + currentGovernance.getAllResources().get(AllResource.APPLE) + '\n';
        output += "Cheese: " + currentGovernance.getAllResources().get(AllResource.CHEESE) + '\n';
        output += "Meat: " + currentGovernance.getAllResources().get(AllResource.MEAT) + '\n';

        return output;
    }

    public static GameMenuMessages checkChangeFoodRate(Matcher matcher) {
        Governance currentGovernance = currentGame.getCurrentGovernance();
        int rateNumber = Integer.parseInt(matcher.group("rateNumber"));

        if (rateNumber < -2 || rateNumber > 2) return GameMenuMessages.INVALID_RATE;

        currentGovernance.setFoodRate(rateNumber);

        return GameMenuMessages.SUCCESS;
    }

    public static String showFoodRate() {
        Governance currentGovernance = currentGame.getCurrentGovernance();
        return "Food rate: " + currentGovernance.getFoodRate();
    }

    public static GameMenuMessages checkChangeTaxRate(Matcher matcher) {
        Governance currentGovernance = currentGame.getCurrentGovernance();
        int rateNumber = Integer.parseInt(matcher.group("rateNumber"));

        if (rateNumber < -3 || rateNumber > 8) return GameMenuMessages.INVALID_RATE;

        currentGovernance.setTaxRate(rateNumber);

        return GameMenuMessages.SUCCESS;
    }

    public static String showTaxRate() {
        Governance currentGovernance = currentGame.getCurrentGovernance();
        return "Tax rate: " + currentGovernance.getTaxRate();
    }

    public static GameMenuMessages checkChangeFearRate(Matcher matcher) { //TODO: not in the actual game!
        Governance currentGovernance = currentGame.getCurrentGovernance();
        int rateNumber = Integer.parseInt(matcher.group("rateNumber"));

        if (rateNumber < -5 || rateNumber > 5) return GameMenuMessages.INVALID_RATE;

        currentGovernance.setFearFactor(rateNumber);

        return GameMenuMessages.SUCCESS;
    }

    public static String showFearRate() {
        Governance currentGovernance = currentGame.getCurrentGovernance();
        return "Fear rate: " + currentGovernance.getFearFactor();
    }

    public static GameMenuMessages checkDropBuilding(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "xGroup", "yGroup", "typeGroup"))
            return GameMenuMessages.INVALID_COMMAND;
        int x = Integer.parseInt(matcher.group("xGroup"));
        int y = Integer.parseInt(matcher.group("yGroup"));
        String type = matcher.group("typeGroup");
        Governance currentGovernance = currentGame.getCurrentGovernance();
        Building building = BuildingUtils.getBuildingByType(type);
        if (!BuildingUtils.isValidBuildingType(type))
            return GameMenuMessages.INVALID_BUILDING_TYPE;
        int size = building.getSize();
        if (!BuildingUtils.isValidCoordinates(currentGame.getMap(), x, y, size))
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
        if (!Utils.isValidCoordinates(currentGame.getMap(), x, y))
            return GameMenuMessages.INVALID_COORDINATE;
        Building building = currentGame.getMap().getTile(x, y).getBuilding();
        Governance governance = currentGame.getCurrentGovernance();
        if (building == null)
            return GameMenuMessages.NO_BUILDING_HERE;
        if (!building.getOwner().equals(governance))
            if (building instanceof Trap)
                return GameMenuMessages.NO_BUILDING_HERE;
            else
                return GameMenuMessages.NOT_YOUR_BUILDING;
        return GameMenuMessages.SUCCESS;
        //TODO: select building after commands need a structure to implement...
    }

    public static String selectBuildingDetails(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("xGroup"));
        int y = Integer.parseInt(matcher.group("yGroup"));
        Building building = currentGame.getMap().getTile(x, y).getBuilding();
        return building.toString();
    }

    public static GameMenuMessages checkSelectUnit(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "xGroup", "yGroup"))
            return GameMenuMessages.INVALID_COMMAND;

        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));
        Map map = currentGame.getMap();

        if (!Utils.isValidCoordinates(map, x, y))
            return GameMenuMessages.INVALID_COORDINATE;

        Tile tile = map.getTile(x, y);

        if (tile.getUnits().size() == 0)
            return GameMenuMessages.NO_UNIT_HERE;
        else if (!tile.getUnits().get(0).getOwnerGovernance().equals(currentGame.getCurrentGovernance()))
            return GameMenuMessages.NOT_YOUR_UNIT;

        return GameMenuMessages.SUCCESS;
    }

    private static void nextTurn() {
    }

    private static void updatePopulation() {
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        int maxPopulation = currentGovernance.getMaxPopulation();
        int popularity = currentGovernance.getPopularity();

        currentGovernance.changeCurrentPopulation(Math.min(popularity / 10 - 5, maxPopulation));

        int unemployedPopulation = currentGovernance.getUnemployedPopulation();
        ArrayList<Building> buildings = currentGovernance.getBuildings();

        for (Building building : buildings) {
            if (building.isActive() && unemployedPopulation < 0) {
                building.setActive(false);
                unemployedPopulation++;
            }
            if (!building.isActive() && unemployedPopulation > 0) {
                building.setActive(true);
                unemployedPopulation--;
            }
        }
        currentGovernance.setUnemployedPopulation(Math.max(0, unemployedPopulation));
    }

    private static void updateSoldiers() {

    }

    private static void updateStorage() {
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();

        for (AllResource resource : AllResource.values()) {
            int productionRate = currentGovernance.getResourceProductionRate(resource);
            int consumptionRate = currentGovernance.getResourceConsumptionRate(resource);
            currentGovernance.addToStorage(resource, productionRate);
            currentGovernance.removeFromStorage(resource, consumptionRate);
        }
    }

    private static void updatePopularityRate() {
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();

        if (currentGovernance.getTotalFood() == 0) currentGovernance.setFoodRate(-2);
        if (currentGovernance.getGold() < currentGovernance.getTaxGold()) currentGovernance.setTaxRate(0);

        int currentFoodFactor = currentGovernance.getFoodFactor();
        int currentFearFactor = currentGovernance.getFearFactor();
        int currentTaxFactor = currentGovernance.getTaxFactor();
        int currentReligiousFactor = currentGovernance.getReligiousFactor();
        int totalFactor = currentReligiousFactor + currentFoodFactor + currentFearFactor + currentTaxFactor;

        totalFactor = Math.min(totalFactor + currentGovernance.getPopularity(), 100);
        totalFactor = Math.max(totalFactor, 0);

        currentGovernance.setPopularity(totalFactor);
    }

    private static void fight() {

    }

    public static GameMenuMessages checkShowMap(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "xGroup", "yGroup"))
            return GameMenuMessages.INVALID_COMMAND;
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));
        if (!Utils.isValidCoordinates(currentGame.getMap(), x, y))
            return GameMenuMessages.INVALID_COORDINATE;
        return GameMenuMessages.SUCCESS;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        GameMenuController.currentGame = currentGame;
    }
}