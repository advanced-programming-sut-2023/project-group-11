package model;

import javafx.scene.image.ImageView;
import model.buildings.Building;
import model.buildings.Storage;
import model.map.Territory;
import model.people.Attacker;
import model.people.Engineer;
import model.people.Unit;

import java.util.ArrayList;
import java.util.HashMap;

public class Governance {
    private final User owner;
    private Territory territory;
    private int score = 0;
    private double gold = 2000;
    private double totalGold = gold;
    private int maxPopulation = 10;
    private int currentPopulation = 10;
    private int unemployedPopulation = 10;
    private int popularity = 100;
    private int foodRate = 0;
    private int foodFactor = 0;
    private int totalFood;
    private int taxRate = 0;
    private int taxFactor = 0;
    private double taxGold = 0;
    private int fearFactor = 0;
    private int religiousFactor = 0;
    private int aleFactor = 0;
    private double troopDamageRatio = 1;
    private double workersEfficiency = 1;
    private final ArrayList<Trade> previousReceivedTrades = new ArrayList<>();
    private final ArrayList<Trade> previousSentTrades = new ArrayList<>();
    private final ArrayList<Trade> tradeNotification = new ArrayList<>();
    private final HashMap<AllResource, Integer> allResources = new HashMap<>();
    private final ArrayList<Storage> storages = new ArrayList<>();
    private final ArrayList<Building> buildings = new ArrayList<>();
    private final ArrayList<Unit> units = new ArrayList<>();

    {
        for (AllResource resource : AllResource.values()) allResources.put(resource, 0);
        allResources.put(AllResource.WOOD, 5);
    }

    public void initializeStorages() {
        addToStorage(AllResource.WOOD, 80);
        addToStorage(AllResource.STONE, 20);
        addToStorage(AllResource.BREAD, 100);
    }

    public Governance(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public double getGold() {
        return gold;
    }

    public void setGold(double gold) {
        if (gold - this.gold > 0)
            this.totalGold += gold - this.gold;
        this.gold = gold;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public void changeMaxPopulation(int maxPopulation) {
        this.maxPopulation += maxPopulation;
    }

    public int getCurrentPopulation() {
        return currentPopulation;
    }

    public void setCurrentPopulation(int currentPopulation) {
        this.currentPopulation = currentPopulation;
    }

    public void changeCurrentPopulation(int changePopulationRate) {
        this.currentPopulation += changePopulationRate;
        this.unemployedPopulation += changePopulationRate;
    }

    public int getUnemployedPopulation() {
        return unemployedPopulation;
    }

    public void setUnemployedPopulation(int unemployedPopulation) {
        this.unemployedPopulation = unemployedPopulation;
    }

    public boolean hasEnoughPopulation(int neededPopulation) {
        return neededPopulation <= unemployedPopulation;
    }

    public ArrayList<Trade> getTradeNotification() {
        return tradeNotification;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public int getTaxFactor() {
        return taxFactor;
    }

    public double getTaxGold() {
        return taxGold;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;

        taxGold = 0;
        if (taxRate != 0) taxGold = taxRate > 0 ? 0.2 * taxRate + 0.4 : 0.2 * taxRate - 0.4;

        if (taxRate <= 0) taxFactor = -2 * taxRate + 1;
        else if (taxRate <= 4) taxFactor = -2 * taxRate;
        else taxFactor = -4 * taxRate + 8;
    }

    public int getTotalFood() {
        return totalFood;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public int getFoodFactor() {
        return foodRate * 4 + foodTypesCount();
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public int getFearFactor() {
        return fearFactor;
    }

    public void setFearFactor(int fearFactor) {
        this.fearFactor = fearFactor;
        troopDamageRatio = 1 + 5 * (fearFactor) / 100.0;
        workersEfficiency = 1 + 5 * (fearFactor) / 100.0;
    }

    public int getReligiousFactor() {
        return religiousFactor;
    }

    public void changeReligiousFactor(int religiousFactor) {
        this.religiousFactor += religiousFactor;
    }

    public int getAleFactor() {
        return aleFactor;
    }

    public void increaseAleFactor() {
        aleFactor += Math.ceilDiv(10, currentPopulation);
    }

    public void resetAleFactor() {
        aleFactor = 0;
    }

    public double getTroopDamageRatio() {
        return troopDamageRatio;
    }

    public double getWorkersEfficiency() {
        return workersEfficiency;
    }

    public void addToSentTrades(Trade trade) {
        previousSentTrades.add(trade);
    }

    public void addToReceivedTrades(Trade trade) {
        previousReceivedTrades.add(trade);
    }

    public ArrayList<Trade> getPreviousReceivedTrades() {
        return previousReceivedTrades;
    }

    public ArrayList<Trade> getPreviousSentTrades() {
        return previousSentTrades;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
    }

    public void addStorage(Storage storage) {
        storages.add(storage);
    }

    private void changeResourceAmount(AllResource allResource, int count) {
        allResources.put(allResource, allResources.get(allResource) + count);
    }

    public ImageView getAvatar() {
        return owner.getAvatar();
    }

    public String getNickname() {
        return owner.getNickname();
    }

    public HashMap<AllResource, Integer> getAllResources() {
        return allResources;
    }

    public int getResourceCount(AllResource resource) {
        return allResources.get(resource);
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void addBuilding(Building building) {
        buildings.add(building);
    }

    public String tradeHistory() {
        String output = "";
        int i = 1;
        for (Trade previousTrade : previousReceivedTrades)
            output += (i++) + "-" + previousTrade;
        return output;
    }

    public boolean hasStorageForItem(AllResource item, int amount) {
        int capacity = 0;
        for (Storage storage : storages) {
            if (storage.getStorage().containsKey(item)) {
                capacity += storage.getCurrentCapacity();
                if (capacity >= amount) return true;
            }
        }
        return false;
    }

    public boolean hasEnoughItem(AllResource item, int amount) {
        if (item.equals(AllResource.NONE)) return true;
        return allResources.get(item) >= amount;
    }

    public void addToStorage(AllResource item, int amount) {
        if (item.equals(AllResource.NONE)) return;
        changeResourceAmount(item, amount);
        for (Storage storage : storages) {
            if (storage.getStorage().containsKey(item)) {
                if (amount == 0) break;
                if (storage.getCurrentCapacity() <= amount) {
                    amount -= storage.getCurrentCapacity();
                    storage.addToStorage(item, storage.getCurrentCapacity());
                } else {
                    storage.addToStorage(item, amount);
                    amount = 0;
                }
            }
        }
    }

    public void removeFromStorage(AllResource item, int amount) {
        if (item.equals(AllResource.NONE)) return;
        changeResourceAmount(item, -amount);
        for (Storage storage : storages) {
            if (storage.getStorage().containsKey(item)) {
                if (amount == 0) break;
                int storageRemaining = storage.getStorage().get(item);
                if (storageRemaining <= amount) {
                    amount -= storageRemaining;
                    storage.removeFromStorage(item, storageRemaining);
                } else {
                    storage.removeFromStorage(item, amount);
                    amount = 0;
                }
            }
        }
    }

    public void updateFood() {
        totalFood = allResources.get(AllResource.APPLE)
                + allResources.get(AllResource.BREAD)
                + allResources.get(AllResource.MEAT)
                + allResources.get(AllResource.CHEESE);
    }

    private int foodTypesCount() {
        int output = 0;

        if (allResources.get(AllResource.APPLE) > 0) output++;
        if (allResources.get(AllResource.BREAD) > 0) output++;
        if (allResources.get(AllResource.MEAT) > 0) output++;
        if (allResources.get(AllResource.CHEESE) > 0) output++;

        return Math.max(0, output - 1);
    }

    public void resetUnitAbilities() {
        for (Unit unit : units) {
            unit.setLeftMoves(unit.getSpeed());
            if (!(unit instanceof Engineer)) ((Attacker) unit).setAttacked(false);
        }
    }

    public Territory getTerritory() {
        return territory;
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getTotalGold() {
        return totalGold;
    }
}