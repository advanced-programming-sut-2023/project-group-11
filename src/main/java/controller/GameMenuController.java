package controller;

import model.Governance;
import model.Stronghold;
import model.buildings.Building;
import model.buildings.Trap;
import model.resources.AllResource;
import view.enums.messages.GameMenuMessages;

import java.util.ArrayList;
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
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));
        if (!Utils.isValidCoordinates(Stronghold.getCurrentGame().getMap(), x, y))
            return GameMenuMessages.INVALID_COORDINATE;
        Building building = Stronghold.getCurrentGame().getMap().getTile(x, y).getBuilding();
        Governance governance = Stronghold.getCurrentGame().getCurrentGovernance();
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
        Building building = Stronghold.getCurrentGame().getMap().getTile(x, y).getBuilding();
        return building.toString();
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

    private static void updateGold() {
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();

        double currentGold = currentGovernance.getGold();
        double taxGold = currentGovernance.getTaxGold();
        currentGovernance.setGold(Math.max(0, currentGold - taxGold));
    }

    private static void updateStorage() {//TODO: split into two methods
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();

        //increase foods, troop equipments, resources & decrease troop equipments, resources
        for (AllResource resource : AllResource.values()) {
            int productionRate = currentGovernance.getResourceProductionRate(resource);
            if (currentGovernance.hasStorageForItem(resource, productionRate))
                currentGovernance.addToStorage(resource, productionRate);

            if (!Utils.isFood(resource)) {
                int consumptionRate = currentGovernance.getResourceConsumptionRate(resource);
                if (currentGovernance.hasEnoughItem(resource, consumptionRate))
                    currentGovernance.removeFromStorage(resource, consumptionRate);
            }
        }

        //decrease foods
        int foodConsumption = currentGovernance.getFoodConsumption();
        int foodRatio = currentGovernance.getFoodRate() / 2 + 1;
        int totalFoodRemoved = 0;
        for (AllResource resource : AllResource.values())
            if (Utils.isFood(resource))
                while (totalFoodRemoved < foodConsumption * foodRatio) {//TODO: foodConsumption * foodRatio * population?
                    if (!currentGovernance.hasEnoughItem(resource, 1)) continue;
                    currentGovernance.removeFromStorage(resource, 1);
                    totalFoodRemoved++;
                }
        currentGovernance.updateFood();
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
        if (!Utils.isValidCommandTags(matcher, "xCoordinate", "yCoordinate"))
            return GameMenuMessages.INVALID_COMMAND;
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));
        if (!Utils.isValidCoordinates(Stronghold.getCurrentGame().getMap(), x, y))
            return GameMenuMessages.INVALID_COORDINATE;
        return GameMenuMessages.SUCCESS;
    }
}