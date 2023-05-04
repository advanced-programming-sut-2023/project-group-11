package controller;

import model.Game;
import model.Governance;
import model.Stronghold;
import model.buildings.Building;
import model.buildings.ProductiveBuilding;
import model.buildings.enums.FillerType;
import model.map.Map;
import model.map.Tile;
import model.AllResource;
import view.enums.messages.GameMenuMessages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;

public class GameMenuController {
    private static Game currentGame;

    private static void nextTurn() {
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

    public static GameMenuMessages checkChangeFearRate(Matcher matcher) {
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

        if (!BuildingUtils.isValidBuildingType(type)) return GameMenuMessages.INVALID_BUILDING_TYPE;

        int size = building.getSize();

        if (!BuildingUtils.isValidCoordinates(currentGame.getMap(), x, y, size))
            return GameMenuMessages.INVALID_COORDINATE;
        if (!BuildingUtils.isMapEmpty(x, y, size)) return GameMenuMessages.CANT_BUILD_HERE;
        if (!BuildingUtils.isTextureSuitable(type, x, y, size)) return GameMenuMessages.CANT_BUILD_HERE;
        if (building.getGoldCost() > currentGovernance.getGold()) return GameMenuMessages.NOT_ENOUGH_MONEY;
        if (building.getResourceCostType() != null)
            if (!currentGovernance.hasEnoughItem(building.getResourceCostType(), building.getResourceCostNumber()))
                return GameMenuMessages.NOT_ENOUGH_RESOURCE;

        BuildingUtils.build(building, x, y, size);

        return GameMenuMessages.SUCCESS;
    }

    public static GameMenuMessages checkSelectBuilding(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "xGroup", "yGroup")) return GameMenuMessages.INVALID_COMMAND;

        int x = Integer.parseInt(matcher.group("xGroup"));
        int y = Integer.parseInt(matcher.group("yGroup"));

        if (!Utils.isValidCoordinates(currentGame.getMap(), x, y)) return GameMenuMessages.INVALID_COORDINATE;

        Building building = currentGame.getMap().getTile(x, y).getBuilding();
        Governance governance = currentGame.getCurrentGovernance();

        if (!BuildingUtils.isBuildingInTile(building)) return GameMenuMessages.NO_BUILDING_HERE;
        if (!building.getOwner().equals(governance)) return GameMenuMessages.NOT_YOUR_BUILDING;
        return GameMenuMessages.SUCCESS;
    }

    public static Building selectBuildingDetails(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("xGroup"));
        int y = Integer.parseInt(matcher.group("yGroup"));
        return currentGame.getMap().getTile(x, y).getBuilding();
    }

    public static GameMenuMessages checkSelectUnit(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "xGroup", "yGroup", "typeGroup"))
            return GameMenuMessages.INVALID_COMMAND;

        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));
        Map map = currentGame.getMap();

        if (!Utils.isValidCoordinates(map, x, y))
            return GameMenuMessages.INVALID_COORDINATE;

        Tile tile = map.getTile(x, y);

        if (!Utils.isValidUnitType(Utils.removeDoubleQuotation(matcher.group("type"))))
            return GameMenuMessages.INVALID_UNIT_TYPE;
        else if (tile.getUnits().size() == 0)
            return GameMenuMessages.NO_UNIT_HERE;
        else if (!tile.getUnits().get(0).getOwner().equals(currentGame.getCurrentGovernance()))
            return GameMenuMessages.NOT_YOUR_UNIT;

        return GameMenuMessages.SUCCESS;
    }

    private static void updatePopulation() {
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        ArrayList<Building> buildings = currentGovernance.getBuildings();
        int currentPopulation = currentGovernance.getCurrentPopulation();
        int maxPopulation = currentGovernance.getMaxPopulation();
        int popularity = currentGovernance.getPopularity();
        int unemployedPopulation = currentGovernance.getUnemployedPopulation();

        currentGovernance.changeCurrentPopulation(Math.min(popularity / 10 - 5, maxPopulation - currentPopulation));

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

    private static void updateStorage() {
        Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();

        //increase foods, troop equipments, resources & decrease troop equipments, resources
        updateAllResources(currentGovernance);

        //decrease foods
        updateFood(currentGovernance);
    }

    private static void updateFood(Governance currentGovernance) {
        double foodRatio = currentGovernance.getFoodRate() / 2.0 + 1;
        int foodConsumption = (int) (currentGovernance.getCurrentPopulation() * foodRatio);
        int totalFoodRemoved = 0;
        for (AllResource resource : AllResource.values())
            if (Utils.isFood(resource))
                while (totalFoodRemoved < foodConsumption) {
                    if (!currentGovernance.hasEnoughItem(resource, 1)) continue;
                    currentGovernance.removeFromStorage(resource, 1);
                    totalFoodRemoved++;
                }
        currentGovernance.updateFood();
    }

    private static void updateAllResources(Governance currentGovernance) {
        ArrayList<Building> buildings = currentGovernance.getBuildings();
        int oxTetherNumber = Collections.frequency(buildings, FillerType.valueOf("ox tether"));

        for (Building building : buildings)
            if (building instanceof ProductiveBuilding productiveBuilding) {
                double workersEfficiency = currentGovernance.getWorkersEfficiency();
                int consumptionRate = (int) Math.ceil((productiveBuilding.getConsumptionRate() * workersEfficiency));
                int productionRate = (int) Math.ceil(productiveBuilding.getProductionRate() * workersEfficiency);

                if (building.getName().equals("quarry")) {
                    productionRate = Math.min(oxTetherNumber, 3) * productionRate;
                    oxTetherNumber -= Math.min(oxTetherNumber, 3);
                }

                int consumed = 0;
                AllResource requiredResource = productiveBuilding.getRequiredResource();
                ArrayList<AllResource> producedResources = productiveBuilding.getProducedResource();

                for (AllResource producedResource : producedResources) {
                    if (consumptionRate == 0 && currentGovernance.hasStorageForItem(producedResource, productionRate))
                        currentGovernance.addToStorage(producedResource, productionRate);
                    else while (consumed < consumptionRate
                            && currentGovernance.hasEnoughItem(requiredResource, 1)
                            && currentGovernance.hasStorageForItem(producedResource, productionRate)) {

                        currentGovernance.removeFromStorage(requiredResource, 1);
                        currentGovernance.addToStorage(producedResource, productionRate);
                        consumed++;
                    }
                    consumed = 0;
                }
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

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame() {
        GameMenuController.currentGame = Stronghold.getCurrentGame();
    }
}
