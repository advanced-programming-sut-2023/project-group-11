package controller;

import javafx.scene.control.Alert;
import model.*;
import model.buildings.Building;
import model.buildings.Climbable;
import model.buildings.ProductiveBuilding;
import model.map.Map;
import model.map.Tile;
import model.map.Tree;
import model.people.*;
import model.people.enums.Speed;
import view.GameMenu;
import view.ViewUtils;
import view.enums.messages.GameMenuMessages;

import java.util.*;
import java.util.regex.Matcher;

import static controller.SelectUnitMenuController.*;

public class GameMenuController {
    private static Game currentGame;
    private static Governance currentGovernance;

    public static String nextTurn() {
        ArrayList<Governance> governances = currentGame.getGovernances();
        int turn = currentGame.getTurn();
        int totalGovernances = governances.size();
        currentGovernance = governances.get(turn % totalGovernances);
        currentGame.setCurrentGovernance(currentGovernance);
        currentGame.plusTurnCounter(1);
        if (currentGovernance == governances.get(0)) currentGame.plusCurrentTurnCounter();
        currentGovernance.setScore((int) ((getCurrentTurn() - 1) * currentGovernance.getTotalGold()));

        if (getCurrentTurn() > 1) {
            updateSoldiers();
            updateGold();
            updateStorages();
            updatePopulation();
            updatePopularityRate();
            updateBuildingStuff();
            if (getCurrentTurn() % 4 == 0) getSick();
        }

        return "Current Turn = " + getCurrentTurn() +
                "\nCurrent Governance = " + currentGovernance.getOwner().getNickname() +
                "\nScore = " + currentGovernance.getScore() +
                "\nArea = " + currentGovernance.getTerritory();
    }

    public static int showFactor(String factor) throws Exception {
        return (int) Governance.class.getDeclaredMethod("get" + factor + "Factor").invoke(currentGovernance);
    }

    public static void changeRate(String factor, int rateNumber) throws Exception {
        Governance.class.getDeclaredMethod("set" + factor + "Rate", int.class).invoke(currentGovernance, rateNumber);
    }

    //    public static String showPopularity() {
//        return "Food: " + currentGovernance.getFoodFactor() + '\n' +
//                "Tax: " + currentGovernance.getTaxFactor() + '\n' +
//                "Religious: " + currentGovernance.getReligiousFactor() + '\n' +
//                "Fear: " + currentGovernance.getFearFactor() + '\n';
//    }
//
//    public static String showFoodList() {
//        String output = "";
//
//        output += "Bread: " + currentGovernance.getAllResources().get(AllResource.BREAD) + '\n';
//        output += "Apple: " + currentGovernance.getAllResources().get(AllResource.APPLE) + '\n';
//        output += "Cheese: " + currentGovernance.getAllResources().get(AllResource.CHEESE) + '\n';
//        output += "Meat: " + currentGovernance.getAllResources().get(AllResource.MEAT) + '\n';
//
//        return output;
//    }
//
//    public static void changeFoodRate(int rateNumber) {
//        currentGovernance.setFoodRate(rateNumber);
//    }

    public static int showFoodRate() {
        return currentGovernance.getFoodRate();
    }

//    public static void changeTaxRate(int rateNumber) {
//        currentGovernance.setTaxRate(rateNumber);
//    }

    public static int showTaxRate() {
        return currentGovernance.getTaxRate();
    }

//    public static void changeFearRate(int rateNumber) {
//        currentGovernance.setFearRate(rateNumber);
//    }

    public static int showFearRate() {
        return currentGovernance.getFearFactor();
    }

    public static GameMenuMessages checkDropBuilding(int x, int y, String buildingType) {
        Building building = BuildingUtils.getBuildingByType(buildingType);

        int size = building.getSize();
        if (!BuildingUtils.isValidCoordinates(currentGame.getMap(), x, y, size))
            return GameMenuMessages.INVALID_COORDINATE;
        if (!BuildingUtils.isMapEmpty(x, y, size)) return GameMenuMessages.CANT_BUILD_HERE;
        if (!BuildingUtils.isTextureSuitable(buildingType, x, y, size)) return GameMenuMessages.CANT_BUILD_HERE;
        if (building.getGoldCost() > currentGovernance.getGold()) return GameMenuMessages.NOT_ENOUGH_MONEY;
        if (!currentGovernance.hasEnoughItem(building.getResourceCostType(), building.getResourceCostNumber()))
            return GameMenuMessages.NOT_ENOUGH_RESOURCE;
        if (!currentGovernance.hasEnoughPopulation(building.getWorkersNumber()))
            return GameMenuMessages.NOT_ENOUGH_POPULATION;

        BuildingUtils.build(currentGovernance, building, x, y);
        return GameMenuMessages.SUCCESS;
    }

    public static GameMenuMessages checkSelectBuilding(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "xCoordinate", "yCoordinate")) return GameMenuMessages.INVALID_COMMAND;

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
        if (!Utils.isValidCommandTags(matcher, "xCoordinate", "yCoordinate", "type"))
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

        currentGovernance = currentGame.getCurrentGovernance();
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

        if (!resourceName.equals("all") && (!resourceName.equals("gold")) && !isValidResourceName(resourceName))
            return GameMenuMessages.INVALID_RESOURCE_TYPE;
        return GameMenuMessages.SUCCESS;
    }

    public static String showResource(String resourceName) {
        final String[] output = {""};
        if (resourceName.equals("all")) {
            currentGovernance.getAllResources().forEach((allResource, integer) ->
                    output[0] += allResource.getName() + '=' + integer + '\n');
            output[0] += "gold=" + currentGovernance.getGold() + "\n";
        } else if (resourceName.equals("gold")) output[0] += "gold=" + currentGovernance.getGold() + "\n";
        else output[0] += resourceName + '=' +
                    currentGovernance.getAllResources().get(AllResource.valueOf(resourceName.toUpperCase())) + '\n';

        return output[0];
    }

    public static void dropUnit(int x, int y, int count, String type) {
        Tile tile = currentGame.getMap().getTile(x, y);

        for (int i = 0; i < count; i++) {
            Unit unit;

            if (Utils.isValidMachineType(type)) {
                unit = new Machine(type);
                for (int j = 0; j < ((Machine) unit).getEngineersNeededToActivate(); j++) {
                    Engineer engineer = new Engineer();
                    ((Machine) unit).addEngineer(engineer);
                }
            } else if (type.equals("engineer")) unit = new Engineer();
            else unit = new Troop(type);

            unit.initializeUnit(tile, true);
        }
    }

    private static void updatePopulation() {
        ArrayList<Building> buildings = currentGovernance.getBuildings();
        int currentPopulation = currentGovernance.getCurrentPopulation();
        int maxPopulation = currentGovernance.getMaxPopulation();
        int popularity = currentGovernance.getPopularity();

        currentGovernance.changeCurrentPopulation(Math.min(popularity / 10 - 5, maxPopulation - currentPopulation));

        for (Building building : buildings) {
            if (building.isActive() && building.getWorkersNumber() > 0 && currentGovernance.getUnemployedPopulation() < 0) {
                building.setActive(false);
                currentGovernance.changeCurrentPopulation(building.getWorkersNumber());
            }
            if (!building.isActive() && currentGovernance.getUnemployedPopulation() - building.getWorkersNumber() > 0) {
                building.setActive(true);
                currentGovernance.changeCurrentPopulation(-building.getWorkersNumber());
            }
        }
        currentGovernance.setUnemployedPopulation(Math.max(0, currentGovernance.getUnemployedPopulation()));
        currentGovernance.setCurrentPopulation(Math.max(0, currentGovernance.getCurrentPopulation()));
    }

    private static void updateSoldiers() {
        ArrayList<Unit> units = currentGovernance.getUnits();
        ArrayList<Unit> diggingUnits = new ArrayList<>();

        for (Unit unit : units) {
            if (unit instanceof Troop troop && troop.isDigging())
                diggingUnits.add(troop);
        }

        currentGovernance.resetUnitAbilities();
        updatePatrol();
        updateState();
        if (diggingUnits.size() != 0)
            SelectUnitMenuController.digPitchInNextTurn(diggingUnits);
    }

    private static void updateGold() {
        double currentGold = currentGovernance.getGold();
        double taxGold = currentGovernance.getTaxGold();
        int currentPopulation = currentGovernance.getCurrentPopulation();
        currentGovernance.setGold(Math.max(0, currentGold + taxGold * currentPopulation));
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
                    if (!currentGovernance.hasEnoughItem(resource, 1)) break;
                    currentGovernance.removeFromStorage(resource, 1);
                    totalFoodRemoved++;
                }

        currentGovernance.updateFood();
    }

    private static void updateBuildingStuff() {
        for (Building building : currentGovernance.getBuildings()) {
            cagedWardog(building);
            fireBuilding(building);
            if (building.isSick()) {
                if (cureSickness(building)) {
                    building.setSick(false);
                    ViewUtils.alert(Alert.AlertType.INFORMATION, "Cure sickness",
                            "Sickness has been solved in x:" + building.getXCoordinate() +
                                    " y:" + building.getYCoordinate());
                } else currentGovernance.setSickFactor(currentGovernance.getSickFactor() - 2);
            }
        }
    }

    private static void getSick() {
        ArrayList<Building> buildings = currentGovernance.getBuildings();
        if (allBuildingAreSick(buildings)) return;

        Building randomBuilding;
        while (true) {
            randomBuilding = buildings.get(new Random().nextInt(buildings.size()));
            if (!randomBuilding.isSick()) break;
        }
        randomBuilding.setSick(true);
        ViewUtils.alert(Alert.AlertType.INFORMATION, "Sick Building",
                currentGovernance.getNickname() + " have got sick in x:" + randomBuilding.getXCoordinate() +
                        " y:" + randomBuilding.getYCoordinate());
    }

    private static boolean allBuildingAreSick(ArrayList<Building> buildings) {
        for (Building building : buildings)
            if (!building.isSick()) return false;
        return true;
    }

    private static boolean cureSickness(Building building) {
        ArrayList<Tile> tiles = getSurroundingBuildingTiles(building);
        for (Tile tile : tiles) {
            for (Unit unit : tile.getUnits()) {
                if (unit instanceof Engineer) return true;
            }
        }
        return false;
    }

    private static ArrayList<Tile> getSurroundingBuildingTiles(Building building) {
        int x = building.getXCoordinate();
        int y = building.getYCoordinate();
        int rows = building.getSize();
        int columns = building.getSize();
        Map currentMap = currentGame.getMap();
        ArrayList<Tile> result = new ArrayList<>();

        if (Utils.isValidCoordinates(currentMap, x + columns, y)) columns += 1;
        if (Utils.isValidCoordinates(currentMap, x, y + rows)) rows += 1;
        if (Utils.isValidCoordinates(currentMap, x, y - 1)) {
            y -= 1;
            rows += 1;
        }
        if (Utils.isValidCoordinates(currentMap, x - 1, y)) {
            x -= 1;
            columns += 1;
        }

        for (int i = 0; i < columns; i++)
            for (int j = 0; j < rows; j++)
                result.add(currentMap.getTile(x + i, y + j));

        return result;
    }

    private static void fireBuilding(Building building) {
        if (building.isFiring()) {
            if (building.getFiringLeft() == 0) building.setFiring(false);
            building.setHitPoint(building.getHitPoint() - 50);
            building.setFiringLeft(building.getFiringLeft() - 1);
            if (building.getHitPoint() <= 0) building.removeFromGame(currentGame.getMap());
        }
    }

    private static void cagedWardog(Building building) {
        if (building.getName().equals("caged wardogs")) {
            int x = building.getXCoordinate();
            int y = building.getYCoordinate();
            int range = 3, damage = 20;
            for (int i = -range; i <= range; i++) {
                for (int j = -range; j <= range; j++) {
                    if (!Utils.isValidCoordinates(currentGame.getMap(), x, y))
                        continue;
                    Tile tile = currentGame.getMap().getTile(x, y);
                    if (tile.hasEnemy(currentGovernance)) {
                        for (Unit unit : tile.getUnits())
                            unit.setHp(unit.getHp() - damage);
                        building.removeFromGame(currentGame.getMap());
                    }
                    removeDeadUnits(tile);
                }
            }
        }
    }

    private static void updateAllResources() {
        ArrayList<Building> buildings = currentGovernance.getBuildings();
        int oxTetherNumber = countBuilding(buildings, "ox tether");
        currentGovernance.resetAleFactor();

        for (Building building : buildings) {
            if (building instanceof ProductiveBuilding productiveBuilding && productiveBuilding.isActive()) {
                double workersEfficiency = currentGovernance.getWorkersEfficiency();
                int consumptionRate = (int) Math.ceil((productiveBuilding.getConsumptionRate() * workersEfficiency));
                int productionRate = (int) Math.ceil(productiveBuilding.getProductionRate() * workersEfficiency);

                if (building.getName().equals("quarry")) {
                    productionRate = Math.min(oxTetherNumber, 3) * productionRate;
                    oxTetherNumber -= Math.min(oxTetherNumber, 3);
                }
                if (building.getName().equals("wood cutter")) building.setActive(cutTree(building));

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

    private static int countBuilding(ArrayList<Building> buildings, String buildingName) {
        int i = 0;
        for (Building building : buildings) if (building.getName().equals(buildingName) && building.isActive()) i++;
        return i;
    }

    private static boolean cutTree(Building building) {
        int x = building.getXCoordinate();
        int y = building.getYCoordinate();
        int range = 10, cutRate = 20;
        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                if (!Utils.isValidCoordinates(currentGame.getMap(), i, j))
                    continue;
                Tile tile = currentGame.getMap().getTile(x + i, y + j);
                Tree tree = tile.getTree();
                if (tree != null && tree.getLeftWood() >= cutRate && currentGovernance.hasStorageForItem(AllResource.WOOD, 20)) {
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
        else currentGovernance.setFoodRate(currentGovernance.getFoodRate());
        if (currentGovernance.getGold() < currentGovernance.getTaxGold()) currentGovernance.setTaxRate(0);

        int totalFactor = currentGovernance.getTotalFactor();
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
        int currentX = unit.getLocation()[0];
        int currentY = unit.getLocation()[1];
        int finalRange = isValidUnitForAirAttack(unit.getName()) ? ((Attacker) unit).getRange(map.getTile(unit.getLocation())) : range;
        setUnitUpdateState(units, attackNearestEnemy(currentX, currentY, finalRange, currentGame.getMap(), units));
//        if (unit.getUnitState() == UnitState.OFFENSIVE)
//            for (Unit unit1 : units)
//                unit1.setUnitState(UnitState.DEFENSIVE);
//        else
//            for (Unit unit1 : units)
//                unit1.setUnitState(UnitState.STANDING);
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
                if (tile.hasEnemy(currentGovernance)) {
                    if (j > 0)
                        enemyNumberDown += tile.getUnits().size();
                    if (j < 0)
                        enemyNumberUp += tile.getUnits().size();
                    if (i > 0)
                        enemyNumberRight += tile.getUnits().size();
                    if (i < 0)
                        enemyNumberLeft += tile.getUnits().size();
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

        if (currentX == destinationX && currentY == destinationY)
            return true;
        if (!Utils.isValidCoordinates(map, destinationX, destinationY))
            return false;
        else if (notValidTextureForMoving(map.getTile(destinationX, destinationY)))
            return false;
        else if (map.getTile(destinationX, destinationY).getUnits().size() != 0 &&
                !isValidDestinationSameOwnerUnits(map.getTile(currentX, currentY), map.getTile(destinationX, destinationY)))
            return false;
        else if (findRootToDestination(map, unitType, currentX, currentY, destinationX, destinationY) == null)
            return false;
        else return !BuildingUtils.isBuildingInTile(map.getTile(destinationX, destinationY).getBuilding()) ||
                    (map.getTile(destinationX, destinationY).getBuilding() instanceof Climbable);
    }

    private static ArrayList<Unit> getAllSameUnitsOfTile(Unit unit) {
        return currentGame.getMap().getTile(unit.getLocation()).getUnitsByType(unit.getName());
    }

    private static void updatePatrol() {
        ArrayList<Unit> units = new ArrayList<>(currentGovernance.getUnits());
        ArrayList<Unit> patrollingUnits = new ArrayList<>();

        for (int i = 0; i < units.size(); i++) {
            Unit firstUnit = units.get(i);
            if (firstUnit.isPatrolling()) {
                patrollingUnits.add(firstUnit);

                for (int j = i + 1; j < units.size(); j++) {
                    Unit unit = units.get(j);
                    if (hasSameOriginAndDestinationForPatrol(firstUnit, unit)) patrollingUnits.add(unit);
                }
                patrolUnit(patrollingUnits);
            }
            units.removeAll(patrollingUnits);
            patrollingUnits.clear();
        }
    }

    private static boolean hasSameOriginAndDestinationForPatrol(Unit firstUnit, Unit unit) {
        return Arrays.equals(firstUnit.getPatrolOrigin(), unit.getPatrolOrigin()) &&
                Arrays.equals(firstUnit.getPatrolDestination(), unit.getPatrolDestination());
    }

    private static void setUnitUpdateState(ArrayList<Unit> units, Boolean attacked) {
        for (Unit unit : units)
            ((Attacker) unit).setAttacked(attacked);
    }

    private static boolean attackNearestEnemy(int currentX, int currentY,
                                              int range, Map map, ArrayList<Unit> units) {
        String unitName = units.get(0).getName();
        int minDistance = 100;
        boolean airAttack = isValidUnitForAirAttack(unitName);
        Tile attackTargetTile = null, moveTargetTile = map.getTile(currentX, currentY);
        for (int i = -range; i < range; i++) {
            for (int j = -range; j < range; j++) {
                if (!Utils.isValidCoordinates(map, currentX, currentY))
                    continue;
                int distance = Math.abs(i) + Math.abs(j);
                if (distance > range || distance > minDistance)
                    continue;
                int destinationX = currentX + i;
                int destinationY = currentY + j;
                if (airAttack) {
                    if (map.getTile(destinationX, destinationY).hasEnemy(currentGovernance)) {
                        attackTargetTile = map.getTile(destinationX, destinationY);
                        minDistance = distance;
                    }
                } else {
                    if (canUnitMove(destinationX, destinationY, currentX, currentY, unitName)) {
                        if (Utils.isValidCoordinates(map, destinationX + 1, destinationY) && map.getTile(destinationX + 1, destinationY).hasEnemy(currentGovernance)) {
                            moveTargetTile = map.getTile(destinationX, destinationY);
                            attackTargetTile = map.getTile(destinationX + 1, destinationY);
                            minDistance = distance;
                            continue;
                        }
                        if (Utils.isValidCoordinates(map, destinationX - 1, destinationY) && map.getTile(destinationX - 1, destinationY).hasEnemy(currentGovernance)) {
                            moveTargetTile = map.getTile(destinationX, destinationY);
                            attackTargetTile = map.getTile(destinationX - 1, destinationY);
                            minDistance = distance;
                            continue;
                        }
                        if (Utils.isValidCoordinates(map, destinationX, destinationY + 1) && map.getTile(destinationX, destinationY + 1).hasEnemy(currentGovernance)) {
                            moveTargetTile = map.getTile(destinationX, destinationY);
                            attackTargetTile = map.getTile(destinationX, destinationY + 1);
                            minDistance = distance;
                            continue;
                        }
                        if (Utils.isValidCoordinates(map, destinationX, destinationY - 1) && map.getTile(destinationX, destinationY - 1).hasEnemy(currentGovernance)) {
                            moveTargetTile = map.getTile(destinationX, destinationY);
                            attackTargetTile = map.getTile(destinationX, destinationY - 1);
                            minDistance = distance;
                        }
                    }
                }
            }
        }
        if (attackTargetTile == null)
            return false;
        if (!airAttack) {
            System.out.println();
            int destinationX = map.getTileLocation(moveTargetTile)[0];
            int destinationY = map.getTileLocation(moveTargetTile)[1];
            System.out.println(destinationX + " " + destinationY);
            System.out.println(currentX + " " + currentY);
            if (!(currentX == destinationX) || !(currentY == destinationY))
                moveUnits(map, units, currentX, currentY, destinationX, destinationY);
        }
        if (units.size() > 0)
            attack(units, unitName, attackTargetTile, moveTargetTile);
        return true;

    }

    private static void moveUnits(Map map, ArrayList<Unit> units, int currentX, int currentY, int destinationX, int destinationY) {
        Path shortestPath = findRootToDestination(map, units.get(0).getName(), currentX, currentY, destinationX, destinationY);
        map.getTile(currentX, currentY).clearUnitsByType(units);

        setLocation(units, shortestPath);
//        applyPathEffects( shortestPath, units);TODO apply this later

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
        currentGame.getCurrentGovernance().setScore(currentGame.getCurrentGovernance().getScore() + 1000);
        return currentGame.getCurrentGovernance().getOwner().getNickname();
    }

    public static String scores() {
        StringBuilder result = new StringBuilder();
        result.append(currentGame.getCurrentGovernance().getOwner().getNickname()).append(" -> ")
                .append(currentGame.getCurrentGovernance().getScore()).append('\n');
        ArrayList<Governance> sortedGovernances = sortGovernances(currentGame.getScores());
        for (Governance governance : sortedGovernances) {
            result.append(governance.getOwner().getNickname()).append(" -> ")
                    .append(currentGame.getScores().get(governance)).append('\n');
        }
        return result.toString();
    }

    private static ArrayList<Governance> sortGovernances(HashMap<Governance, Integer> scores) {
        Set<java.util.Map.Entry<Governance, Integer>> entries = scores.entrySet();
        List<java.util.Map.Entry<Governance, Integer>> sortedEntries = new ArrayList<>(entries);
        ArrayList<Governance> result = new ArrayList<>();
        Comparator<java.util.Map.Entry<Governance, Integer>> comparator = (o1, o2) -> {
            Integer v1 = o1.getValue();
            Integer v2 = o2.getValue();
            return v2.compareTo(v1);
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

        winner.getOwner().addScore(winner.getScore());
        losers.forEach(loser -> loser.getOwner().addScore(scores.get(loser)));
    }

    private static void nullTheCurrentGames() {
        currentGame = null;
        currentGovernance = null;
        Stronghold.setCurrentGame(null);
    }

    public static String showMapDetails(int x, int y) {
        String output = "";
        Map map = currentGame.getMap();
        Tile tile = map.getTile(x, y);
        output += tile.toString();
        return output;
    }

    public static double getGold() {
        return currentGame.getCurrentGovernance().getGold();
    }


    public static double[] getCoordinateWithTile(double[] mapLocation) {
        GameMenu gameMenu = Utils.getGameMenu();
        int tileSize = gameMenu.getTileSize();
        double mapX = mapLocation[0] - gameMenu.getFirstTileXInMap();
        double mapY = mapLocation[1] - gameMenu.getFirstTileYInMap();
        return new double[]{mapX * tileSize, mapY * tileSize};
    }

    public static double getArrowAngle(double[] currentLocation, double[] destinationLocation) {
        return Math.toDegrees(Math.atan((destinationLocation[1] - currentLocation[1]) / (destinationLocation[0] - currentLocation[0])));
    }

    public static boolean hasReachedDestination(double[] currentLocation, double[] destinationLocation) {
        return Math.abs(currentLocation[0] - destinationLocation[0]) < 0.05 ||
                Math.abs(currentLocation[1] - destinationLocation[1]) < 0.05;
    }
}
