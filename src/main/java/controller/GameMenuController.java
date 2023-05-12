package controller;

import model.*;
import model.buildings.Building;
import model.buildings.Climbable;
import model.buildings.ProductiveBuilding;
import model.buildings.enums.FillerType;
import model.map.Map;
import model.map.Tile;
import model.map.Tree;
import model.people.*;
import model.people.enums.Speed;
import model.people.enums.UnitState;
import view.enums.messages.GameMenuMessages;

import java.util.*;
import java.util.regex.Matcher;

import static controller.SelectUnitMenuController.*;

public class GameMenuController {
    private static Game currentGame;
    private static Governance currentGovernance;

    public static String nextTurn() {
        ArrayList<Governance> governances = Stronghold.getCurrentGame().getGovernances();
        int turn = currentGame.getTurn();
        int totalGovernances = governances.size();
        currentGovernance = governances.get(turn % totalGovernances);
        currentGame.setCurrentGovernance(currentGovernance);
        currentGame.plusTurnCounter();
        currentGovernance.setScore((int) ((getCurrentTurn() - 1) * currentGovernance.getTotalGold()));
        if (currentGovernance == governances.get(0)) currentGame.plusCurrentTurnCounter();

        if (getCurrentTurn() > 1) {
            updateSoldiers();
            updateGold();
            updateStorages();
            updatePopulation();
            updatePopularityRate();
            updateBuildingStuff();
        }

        return "Current Turn = " + getCurrentTurn() +
                "\nCurrent Governance = " + currentGovernance.getOwner().getUsername() +
                "\nScore = " + currentGovernance.getScore() +
                "\nArea = " + currentGovernance.getTerritory();
    }

    public static GameMenuMessages checkShowMap(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "xCoordinate", "yCoordinate"))
            return GameMenuMessages.INVALID_COMMAND;

        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));

        if (!Utils.isValidCoordinates(currentGame.getMap(), x, y))
            return GameMenuMessages.INVALID_COORDINATE;
        return GameMenuMessages.SUCCESS;
    }

    public static String showPopularity(Matcher matcher) {
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
        String output = "";

        output += "Bread: " + currentGovernance.getAllResources().get(AllResource.BREAD) + '\n';
        output += "Apple: " + currentGovernance.getAllResources().get(AllResource.APPLE) + '\n';
        output += "Cheese: " + currentGovernance.getAllResources().get(AllResource.CHEESE) + '\n';
        output += "Meat: " + currentGovernance.getAllResources().get(AllResource.MEAT) + '\n';

        return output;
    }

    public static GameMenuMessages checkChangeFoodRate(Matcher matcher) {
        int rateNumber = Integer.parseInt(matcher.group("rateNumber"));

        if (rateNumber < -2 || rateNumber > 2) return GameMenuMessages.INVALID_RATE;

        currentGovernance.setFoodRate(rateNumber);

        return GameMenuMessages.SUCCESS;
    }

    public static String showFoodRate() {
        return "Food rate: " + currentGovernance.getFoodRate();
    }

    public static GameMenuMessages checkChangeTaxRate(Matcher matcher) {
        int rateNumber = Integer.parseInt(matcher.group("rateNumber"));

        if (rateNumber < -3 || rateNumber > 8) return GameMenuMessages.INVALID_RATE;

        currentGovernance.setTaxRate(rateNumber);

        return GameMenuMessages.SUCCESS;
    }

    public static String showTaxRate() {
        return "Tax rate: " + currentGovernance.getTaxRate();
    }

    public static GameMenuMessages checkChangeFearRate(Matcher matcher) {
        int rateNumber = Integer.parseInt(matcher.group("rateNumber"));

        if (rateNumber < -5 || rateNumber > 5) return GameMenuMessages.INVALID_RATE;

        currentGovernance.setFearFactor(rateNumber);

        return GameMenuMessages.SUCCESS;
    }

    public static String showFearRate() {
        return "Fear rate: " + currentGovernance.getFearFactor();
    }

    public static GameMenuMessages checkDropBuilding(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "xGroup", "yGroup", "typeGroup"))
            return GameMenuMessages.INVALID_COMMAND;

        int x = Integer.parseInt(matcher.group("xGroup"));
        int y = Integer.parseInt(matcher.group("yGroup"));
        String type = Utils.removeDoubleQuotation(matcher.group("typeGroup"));
        Building building = BuildingUtils.getBuildingByType(type);

        if (!BuildingUtils.isValidBuildingType(type)) return GameMenuMessages.INVALID_BUILDING_TYPE;

        int size = building.getSize();

        if (!BuildingUtils.isValidCoordinates(currentGame.getMap(), x, y, size))
            return GameMenuMessages.INVALID_COORDINATE;
        if (!BuildingUtils.isMapEmpty(x, y, size)) return GameMenuMessages.CANT_BUILD_HERE;
        if (!BuildingUtils.isTextureSuitable(type, x, y, size)) return GameMenuMessages.CANT_BUILD_HERE;
        if (building.getGoldCost() > currentGovernance.getGold()) return GameMenuMessages.NOT_ENOUGH_MONEY;
        if (!currentGovernance.hasEnoughItem(building.getResourceCostType(), building.getResourceCostNumber()))
            return GameMenuMessages.NOT_ENOUGH_RESOURCE;
        if (!currentGovernance.hasEnoughPopulation(building.getWorkersNumber()))
            return GameMenuMessages.NOT_ENOUGH_POPULATION;

        BuildingUtils.build(currentGovernance, building, x, y, size);
        return GameMenuMessages.SUCCESS;
    }

    public static GameMenuMessages checkSelectBuilding(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "xGroup", "yGroup")) return GameMenuMessages.INVALID_COMMAND;

        int xCoordinate = Integer.parseInt(matcher.group("xCoordinate"));
        int yCoordinate = Integer.parseInt(matcher.group("yCoordinate"));

        if (!Utils.isValidCoordinates(currentGame.getMap(), xCoordinate, yCoordinate))
            return GameMenuMessages.INVALID_COORDINATE;

        Building building = currentGame.getMap().getTile(xCoordinate, yCoordinate).getBuilding();

        if (!BuildingUtils.isBuildingInTile(building)) return GameMenuMessages.NO_BUILDING_HERE;
        if (!building.getOwner().equals(currentGovernance)) return GameMenuMessages.NOT_YOUR_BUILDING;
        return GameMenuMessages.SUCCESS;
    }

    public static GameMenuMessages checkSelectUnit(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "xGroup", "yGroup", "typeGroup"))
            return GameMenuMessages.INVALID_COMMAND;

        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));
        String type = Utils.removeDoubleQuotation(matcher.group("type"));
        Map map = currentGame.getMap();

        if (!Utils.isValidCoordinates(map, x, y)) return GameMenuMessages.INVALID_COORDINATE;

        Tile tile = map.getTile(x, y);

        if (!(Utils.isValidUnitType(type) || Utils.isValidMachineType(type)))
            return GameMenuMessages.INVALID_UNIT_TYPE;
        else if (tile.getUnits().size() == 0)
            return GameMenuMessages.NO_UNIT_HERE;
        else if (!tile.getUnits().get(0).getOwner().equals(currentGame.getCurrentGovernance()))
            return GameMenuMessages.NOT_YOUR_UNIT;
        else if (tile.getUnitsByType(type).size() == 0)
            return GameMenuMessages.NO_UNIT_HERE_WITH_THIS_TYPE;

        return GameMenuMessages.SUCCESS;
    }

    public static GameMenuMessages checkDropUnit(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "xCoordinate", "yCoordinate", "type", "count"))
            return GameMenuMessages.INVALID_COMMAND;

        currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));
        int count = Integer.parseInt(matcher.group("count"));
        String type = Utils.removeDoubleQuotation(matcher.group("type"));

        if (!Utils.isValidCoordinates(currentGame.getMap(), x, y))
            return GameMenuMessages.INVALID_COORDINATE;

        Tile tile = currentGame.getMap().getTile(x, y);

        if (tile.getUnits().size() + count > 12)
            return GameMenuMessages.NOT_ENOUGH_SPACE;
        if ((!Utils.isValidUnitType(type) && !Utils.isValidMachineType(type)) || type.equals("lord"))
            return GameMenuMessages.INVALID_UNIT_TYPE;
        if (!tile.getTexture().isSuitableForUnit())
            return GameMenuMessages.INVALID_TEXTURE;
        if (tile.hasBuilding() && !(tile.getBuilding() instanceof Climbable))
            return GameMenuMessages.CANT_DROP_IN_BUILDING;
        if (tile.getUnits().size() > 0 && !currentGovernance.equals(tile.getUnits().get(0).getOwner()))
            return GameMenuMessages.INVALID_LOCATION_DIFFERENT_OWNER_UNIT;

        dropUnit(x, y, count, type);
        return GameMenuMessages.SUCCESS;
    }

    public static GameMenuMessages checkShowResource(Matcher matcher) {
        String resourceName = Utils.removeDoubleQuotation(matcher.group("resource"));

        if (!resourceName.equals("all") && !isValidResourceName(resourceName))
            return GameMenuMessages.INVALID_RESOURCE_TYPE;
        return GameMenuMessages.SUCCESS;
    }

    public static String showResource(String resourceName) {
        final String[] output = {""};
        if (resourceName.equals("all"))
            currentGovernance.getAllResources().forEach((allResource, integer) ->
                    output[0] += allResource.getName() + '=' + integer + '\n');
        else output[0] += resourceName + '=' +
                currentGovernance.getAllResources().get(AllResource.valueOf(resourceName.toUpperCase())) + '\n';

        return output[0];
    }

    private static void dropUnit(int x, int y, int count, String type) {
        Tile tile = currentGame.getMap().getTile(x, y);

        for (int i = 0; i < count; i++) {
            Unit unit;

            if (Utils.isValidMachineType(type)) {
                unit = new Machine(type);
                for (int j = 0; j < ((Machine) unit).getEngineersNeededToActivate(); j++) {
                    Engineer engineer = new Engineer();
                    ((Machine) unit).addEngineer(engineer);
                    engineer.setLocation(new int[]{x, y});
                }
            } else if (type.equals("engineer")) unit = new Engineer();
            else unit = new Troop(type);

            unit.setLocation(new int[]{x, y});
            tile.getUnits().add(unit);
        }
    }

    private static void updatePopulation() {
        ArrayList<Building> buildings = currentGovernance.getBuildings();
        int currentPopulation = currentGovernance.getCurrentPopulation();
        int maxPopulation = currentGovernance.getMaxPopulation();
        int popularity = currentGovernance.getPopularity();

        currentGovernance.changeCurrentPopulation(Math.min(popularity / 10 - 5, maxPopulation - currentPopulation));

        int unemployedPopulation = currentGovernance.getUnemployedPopulation();

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
        ArrayList<Unit> units = currentGovernance.getUnits();
        ArrayList<Unit> patrollingUnits = new ArrayList<>();
        for (Unit unit : units)
            if (unit.isPatrolling())
                patrollingUnits.add(unit);
        SelectUnitMenuController.patrolUnit(patrollingUnits);
        currentGovernance.resetUnitAbilities();
        updateState();
    }

    private static void updateGold() {
        double currentGold = currentGovernance.getGold();
        double taxGold = currentGovernance.getTaxGold();
        currentGovernance.setGold(Math.max(0, currentGold - taxGold));
    }

    private static void updateStorages() {
        //increase foods, troop equipments, resources & decrease troop equipments, resources
        updateAllResources();

        //decrease foods
        updateFood();
    }

    private static void updateFood() {
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

    private static void updateBuildingStuff() {
        for (Building building : currentGovernance.getBuildings()) {
            cagedWardog(building);
        }
    }

    private static void cagedWardog(Building building) {
        if (building.getName().equals("caged_wardogs")) {
            int x = building.getXCoordinate();
            int y = building.getYCoordinate();
            int range = 3, damage = 20;
            for (int i = -range; i <= range; i++) {
                for (int j = -range; j <= range; j++) {
                    if (!Utils.isValidCoordinates(currentGame.getMap(), x, y))
                        continue;
                    Tile tile = currentGame.getMap().getTile(x,y);
                    if(tile.hasEnemy(currentGovernance)) {
                        for (Unit unit : tile.getUnits())
                            unit.setHp(unit.getHp() - damage);
                        building.removeFromGame(currentGame.getMap(),currentGovernance);
                    }
                    removeDeadUnits(tile);
                }
            }
        }
    }

    private static void updateAllResources() {
        ArrayList<Building> buildings = currentGovernance.getBuildings();
        int oxTetherNumber = Collections.frequency(buildings, FillerType.valueOf("ox tether"));
        currentGovernance.resetAleFactor();

        for (Building building : buildings) {
            if (building.getName().equals("wood cutter")) {
                building.setActive(cutTree(building));
            }
            if (building instanceof ProductiveBuilding productiveBuilding && productiveBuilding.isActive()) {
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

                        if (productiveBuilding.getName().equals("inn")) currentGovernance.increaseAleFactor();

                        consumed++;
                    }
                    consumed = 0;
                }
            }
        }
    }

    private static boolean cutTree(Building building) {
        int x = building.getXCoordinate();
        int y = building.getYCoordinate();
        int range = 10, cutRate = 20;
        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                if (!Utils.isValidCoordinates(currentGame.getMap(), x, y))
                    continue;
                Tile tile = currentGame.getMap().getTile(x + i, y + j);
                Tree tree = tile.getTree();
                if (tree != null && tree.getLeftWood() >= cutRate) {
                    tree.setLeftWood(tree.getLeftWood() - cutRate);
                    if (tree.getLeftWood() <= 0)
                        tile.setTree(null);
                    return true;
                }
            }
        }
        return false;
    }

    private static void updatePopularityRate() {
        if (currentGovernance.getTotalFood() == 0) currentGovernance.setFoodRate(-2);
        if (currentGovernance.getGold() < currentGovernance.getTaxGold()) currentGovernance.setTaxRate(0);

        int currentFoodFactor = currentGovernance.getFoodFactor();
        int currentFearFactor = currentGovernance.getFearFactor();
        int currentTaxFactor = currentGovernance.getTaxFactor();
        int currentReligiousFactor = currentGovernance.getReligiousFactor();
        int currentAleFactor = currentGovernance.getAleFactor();
        int totalFactor = currentFoodFactor + currentFearFactor
                + currentTaxFactor + currentReligiousFactor + currentAleFactor;

        totalFactor = Math.min(totalFactor + currentGovernance.getPopularity(), 100);
        totalFactor = Math.max(totalFactor, 0);

        currentGovernance.setPopularity(totalFactor);
    }

    public static void setCurrentGame() {
        currentGame = Stronghold.getCurrentGame();
        currentGovernance = currentGame.getCurrentGovernance();
    }

    public static int getCurrentTurn() {
        return currentGame.getCurrentTurn();
    }

    private static void updateState() {
        for (Unit unit : currentGovernance.getUnits()) {
            switch (unit.getUnitState()) {
                case STANDING -> {
                    if (unit instanceof Engineer)
                        continue;
                    if (isValidUnitForAirAttack(unit.getName()) && !((Attacker) unit).hasAttacked())
                        unitUpdate(unit, 0);
                }
                case DEFENSIVE -> {
                    if (unit instanceof Engineer)
                        updateEngineer((Engineer) unit, 3);
                    else if (!((Attacker) unit).hasAttacked())
                        unitUpdate(unit, Speed.MEDIUM.getMovesInEachTurn());
                }
                case OFFENSIVE -> {
                    if (unit instanceof Engineer)
                        updateEngineer((Engineer) unit, 1);
                    else if (!((Attacker) unit).hasAttacked())
                        unitUpdate(unit, Speed.VERY_HIGH.getMovesInEachTurn());
                }
            }
        }
    }

    private static void unitUpdate(Unit unit, int range) {
        ArrayList<Unit> units = getAllSameUnitsOfTile(unit);
        Map map = currentGame.getMap();
        setUnitUpdateState(units);
        int currentX = unit.getLocation()[0];
        int currentY = unit.getLocation()[1];
        int finalRange = isValidUnitForGroundAttack(unit.getName()) ? ((Attacker) unit).getRange(map.getTile(unit.getLocation())) : range;
        attackNearestEnemy(currentX, currentY, currentX, currentY, 0, finalRange, currentGame.getMap(), units);
        if (unit.getUnitState() == UnitState.OFFENSIVE) {
            for (Unit unit1 : units)
                unit1.setUnitState(UnitState.DEFENSIVE);
        } else {
            for (Unit unit1 : units)
                unit1.setUnitState(UnitState.STANDING);
        }
    }

    private static void updateEngineer(Engineer engineer, int enemyNeededToPourOil) {
        if (!engineer.hasOilPail() || engineer.isEmptyPail())
            return;
        int range = 2;
        int enemyNumberUp = 0;
        int enemyNumberDown = 0;
        int enemyNumberLeft = 0;
        int enemyNumberRight = 0;
        ArrayList<Unit> engineers = new ArrayList<>(List.of(engineer));
        int x = engineer.getLocation()[0];
        int y = engineer.getLocation()[1];
        for (int i = -range; i <= range; i++) {
            for (int j = range; j <= range; j++) {
                if (!Utils.isValidCoordinates(currentGame.getMap(), x + i, y + j))
                    continue;
                if (i != 0 && j != 0)
                    continue;
                Tile tile = currentGame.getMap().getTile(x + i, x + j);
//                Tile tile = currentGame.getMap().getTile(x+i,y+j);
                if (tile.hasEnemy(currentGovernance)) {
                    if (j > 0)
                        enemyNumberRight += tile.getUnits().size();
                    if (j < 0)
                        enemyNumberLeft += tile.getUnits().size();
                    if (i > 0)
                        enemyNumberDown += tile.getUnits().size();
                    if (i < 0)
                        enemyNumberUp += tile.getUnits().size();
                }
            }
        }
        if (enemyNumberUp >= enemyNeededToPourOil)
            pourOil(engineers, "up", engineer.getLocation());
        if (enemyNumberDown >= enemyNeededToPourOil)
            pourOil(engineers, "down", engineer.getLocation());
        if (enemyNumberLeft >= enemyNeededToPourOil)
            pourOil(engineers, "left", engineer.getLocation());
        if (enemyNumberRight >= enemyNeededToPourOil)
            pourOil(engineers, "right", engineer.getLocation());
    }

    private static Boolean canUnitMove(int destinationX, int destinationY, int currentX, int currentY, String unitType) {
        Map map = currentGame.getMap();

        if (!Utils.isValidCoordinates(map, destinationX, destinationY))
            return false;
        else if (notValidTextureForMoving(map.getTile(destinationX, destinationY)))
            return false;
        else if (map.getTile(destinationX, destinationY).getUnits().size() != 0 &&
                !isValidDestinationSameOwnerUnits(map.getTile(currentX, currentY), map.getTile(destinationX, destinationY)))
            return false;
        else return !BuildingUtils.isBuildingInTile(map.getTile(destinationX, destinationY).getBuilding()) ||
                    (map.getTile(destinationX, destinationY).getBuilding() instanceof Climbable);
    }

    private static ArrayList<Unit> getAllSameUnitsOfTile(Unit unit) {
        return currentGame.getMap().getTile(unit.getLocation()).getUnitsByType(unit.getName());
    }

    private static void setUnitUpdateState(ArrayList<Unit> units) {
        for (Unit unit : units)
            ((Attacker) unit).setAttacked(true);
    }

    private static boolean attackNearestEnemy(int destinationX, int destinationY, int currentX, int currentY,
                                              int currentRange, int maxRange, Map map, ArrayList<Unit> units) {
        if (currentRange > maxRange)
            return false;
        if (!Utils.isValidCoordinates(map, destinationX, destinationY))
            return false;
        if (canUnitMove(destinationX, destinationY, currentX, currentY, units.get(0).getName())
                || isValidUnitForAirAttack(units.get(0).getName())) {
            if (map.getTile(destinationX + 1, destinationY).hasEnemy(currentGovernance)) {
                if (!isValidUnitForAirAttack(units.get(0).getName()))
                    moveUnits(map, units, currentX, currentY, destinationX, destinationY);
                if (units.size() > 0) {
                    attack(units, units.get(0).getName(), map.getTile(destinationX + 1, destinationY), map.getTile(destinationX, destinationY));
                    return true;
                }
            }
            if (map.getTile(destinationX - 1, destinationY).hasEnemy(currentGovernance)) {
                if (!isValidUnitForAirAttack(units.get(0).getName()))
                    moveUnits(map, units, currentX, currentY, destinationX, destinationY);
                if (units.size() > 0) {
                    attack(units, units.get(0).getName(), map.getTile(destinationX - 1, destinationY), map.getTile(destinationX, destinationY));
                    return true;
                }
            }
            if (map.getTile(destinationX, destinationY + 1).hasEnemy(currentGovernance)) {
                if (!isValidUnitForAirAttack(units.get(0).getName()))
                    moveUnits(map, units, currentX, currentY, destinationX, destinationY);
                if (units.size() > 0) {
                    attack(units, units.get(0).getName(), map.getTile(destinationX, destinationY + 1), map.getTile(destinationX, destinationY));
                    return true;
                }
            }
            if (map.getTile(destinationX, destinationY - 1).hasEnemy(currentGovernance)) {
                if (!isValidUnitForAirAttack(units.get(0).getName()))
                    moveUnits(map, units, currentX, currentY, destinationX, destinationY);
                if (units.size() > 0) {
                    attack(units, units.get(0).getName(), map.getTile(destinationX - 1, destinationY), map.getTile(destinationX, destinationY));
                    return true;
                }
            }
        } else return false;
        if (attackNearestEnemy(destinationX + 1, destinationY, currentX, currentY, currentRange + 1, maxRange, map, units))
            return true;
        if (attackNearestEnemy(destinationX - 1, destinationY, currentX, currentY, currentRange + 1, maxRange, map, units))
            return true;
        if (attackNearestEnemy(destinationX, destinationY + 1, currentX, currentY, currentRange + 1, maxRange, map, units))
            return true;
        return attackNearestEnemy(destinationX, destinationY - 1, currentX, currentY, currentRange + 1, maxRange, map, units);
    }

    private static void moveUnits(Map map, ArrayList<Unit> units, int currentX, int currentY, int destinationX, int destinationY) {
        Path shortestPath = findRootToDestination(map, units.get(0).getName(), currentX, currentY, destinationX, destinationY);
        map.getTile(currentX, currentY).clearUnitsByType(units);

        setLocation(units, destinationX, destinationY);
        applyPathEffects(map, shortestPath, units);

        map.getTile(destinationX, destinationY).getUnits().addAll(units);
    }

    private static boolean isValidResourceName(String resourceName) {
        for (AllResource resource : AllResource.values())
            if (resourceName.equals(resource.getName())) return true;
        return false;
    }

    public static boolean gameHasEnded() {
        return currentGame.getGovernances().size() == 1;
    }

    public static String getWinnerName() {
        return currentGame.getGovernances().get(0).getOwner().getUsername();
    }

    public static String scores() {
        StringBuilder result = new StringBuilder();
        ArrayList<Governance> sortedGovernances = sortGovernances(currentGame.getScores());
        for (Governance governance : sortedGovernances) {
            result.append(governance.getOwner().getUsername()).append(" -> ")
                    .append(currentGame.getScores().get(governance)).append('\n');
        }
        return result.toString();
    }

    private static ArrayList<Governance> sortGovernances(HashMap<Governance, Integer> scores) {
        Set<java.util.Map.Entry<Governance, Integer>> entries = scores.entrySet();
        List<java.util.Map.Entry<Governance, Integer>> sortedEntries = new ArrayList<>(entries);
        ArrayList<Governance> result = new ArrayList<>();
        Comparator<java.util.Map.Entry<Governance, Integer>> comparator = new Comparator<java.util.Map.Entry<Governance, Integer>>() {
            @Override
            public int compare(java.util.Map.Entry<Governance, Integer> o1, java.util.Map.Entry<Governance, Integer> o2) {
                Integer v1 = o1.getValue();
                Integer v2 = o2.getValue();
                return v1.compareTo(v2);
            }
        };

        sortedEntries.sort(comparator);
        sortedEntries.forEach(entry -> result.add(entry.getKey()));

        return result;
    }

    public static void endGame() {
        addScoresForPlayers();
        EntryMenuController.parseMaps();
        nullTheCurrentGames();
    }

    private static void addScoresForPlayers() {
        HashMap<Governance, Integer> scores = currentGame.getScores();
        Governance winner = currentGame.getGovernances().get(0);
        ArrayList<Governance> losers = new ArrayList<>(scores.keySet());

        winner.getOwner().addScore(scores.get(winner));
        losers.forEach(loser -> loser.getOwner().addScore(scores.get(loser)));
    }

    private static void nullTheCurrentGames() {
        currentGame = null;
        currentGovernance = null;
        Stronghold.setCurrentGame(null);
    }

    public static String showMapDetails(int x, int y) {
        String output = "";
        Map map = Stronghold.getCurrentGame().getMap();
        Tile tile = map.getTile(x, y);
        output += tile.toString();
        return output;
    }
}
