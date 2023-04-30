package model;

import model.buildings.Storage;
import model.resources.AllResource;

import java.util.ArrayList;
import java.util.HashMap;

public class Governance {
    private final User owner;
    private double gold = 2000;
    private int maxPopulation;
    private int currentPopulation;
    private int unemployedPopulation;
    private int popularity;
    private int taxRate = 0;
    private int foodRate = 0;
    private int taxFactor = 0;
    private int fearFactor = 0;
    private int foodFactor = 0;
    private int religiousFactor = 0;
    private double troopDamageRatio = 1;
    private double workersEfficiency = 1;
    private final ArrayList<Trade> previousTrades = new ArrayList<>();
    private final ArrayList<Trade> tradeNotification = new ArrayList<>();
    //popularity = taxFactor + fearFactor + foodFactor + religiousFactor
    private final HashMap<AllResource, Integer> allResources = new HashMap<>();
    private final HashMap<AllResource, Integer> resourceProductionRate = new HashMap<>();
    private final HashMap<AllResource, Integer> resourceConsumptionRate = new HashMap<>();
    private final ArrayList<Storage> storages = new ArrayList<>();

    {
        allResources.put(AllResource.WOOD, 100);
        allResources.put(AllResource.STONE, 50);
        allResources.put(AllResource.IRON, 0);
        allResources.put(AllResource.WHEAT, 10);
        allResources.put(AllResource.FLOUR, 0);
        allResources.put(AllResource.HOP, 0);
        allResources.put(AllResource.ALE, 0);
        allResources.put(AllResource.PITCH, 0);
        allResources.put(AllResource.BREAD, 100);
        allResources.put(AllResource.APPLE, 0);
        allResources.put(AllResource.MEAT, 0);
        allResources.put(AllResource.CHEESE, 0);
        allResources.put(AllResource.CROSSBOW, 0);
        allResources.put(AllResource.SPEAR, 0);
        allResources.put(AllResource.BOW, 0);
        allResources.put(AllResource.MACE, 0);
        allResources.put(AllResource.PIKE, 0);
        allResources.put(AllResource.LEATHER_ARMOR, 0);
        allResources.put(AllResource.METAL_ARMOR, 0);
        allResources.put(AllResource.SWORD, 0);
    }

    public User getOwner() {
        return owner;
    }

    public double getGold() {
        return gold;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public void setMaxPopulation(int maxPopulation) {
        this.maxPopulation = maxPopulation;
    }

    public int getCurrentPopulation() {
        return currentPopulation;
    }

    public void setCurrentPopulation(int currentPopulation) {
        this.currentPopulation = currentPopulation;
    }

    public int getUnemployedPopulation() {
        return unemployedPopulation;
    }

    public ArrayList<Trade> getTradeNotification() {
        return tradeNotification;
    }

    public void setUnemployedPopulation(int unemployedPopulation) {
        this.unemployedPopulation = unemployedPopulation;
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

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public int getTaxFactor() {
        return taxFactor;
    }

    public void setTaxFactor(int taxFactor) {
        this.taxFactor = taxFactor;
    }

    public int getFearFactor() {
        return fearFactor;
    }

    public void setFearFactor(int fearFactor) {
        this.fearFactor = fearFactor;
    }

    public int getFoodFactor() {
        return foodFactor;
    }

    public void setFoodFactor(int foodFactor) {
        this.foodFactor = foodFactor;
    }

    public int getReligiousFactor() {
        return religiousFactor;
    }

    public void setReligiousFactor(int religiousFactor) {
        this.religiousFactor = religiousFactor;
    }

    public double getTroopDamageRatio() {
        return troopDamageRatio;
    }

    public void setTroopDamageRatio(double troopDamageRatio) {
        this.troopDamageRatio = troopDamageRatio;
    }

    public double getWorkersEfficiency() {
        return workersEfficiency;
    }

    public void setWorkersEfficiency(double workersEfficiency) {
        this.workersEfficiency = workersEfficiency;
    }

    public void addTrade(Trade trade) {
        previousTrades.add(trade);
    }

    public ArrayList<Storage> getStorages() {
        return storages;
    }

    public void changeResourceAmount(AllResource allResource, int count) {
        allResources.put(allResource, allResources.get(allResource) + count);
    }

    public HashMap<AllResource, Integer> getAllResources() {
        return allResources;
    }

    public Integer getResourceProductionRate(AllResource resource) {
        return resourceProductionRate.get(resource);
    }

    public Integer getResourceConsumptionRate(AllResource resource) {
        return resourceConsumptionRate.get(resource);
    }

    public int getResourceCount(AllResource resource) {
        return allResources.get(resource);
    }


    public Governance(User owner) {
        this.owner = owner;
    }

    public String tradeHistory() {
        String output = "";
        int i = 1;
        for (Trade previousTrade : previousTrades)
            output += (i++) + "-" + previousTrade;
        return output;
    }

    public boolean hasStorageForItem(AllResource item, int amount) {
        int capacity = 0;
        for (Storage storage : storages) {
            if (storage.getStorage().containsKey(item)) {
                capacity += storage.getCurrentCapacity();
                if (capacity >= amount)
                    return true;
            }
        }
        return false;
    }

    public boolean hasEnoughItem(AllResource item, int amount) {
        if (allResources.get(item) >= amount)
            return true;
        return false;
    }

    public void addToStorage(AllResource item, int amount) {
        changeResourceAmount(item, amount);
        for (Storage storage : storages) {
            if (storage.getStorage().containsKey(item)) {
                if (amount == 0)
                    break;
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
        changeResourceAmount(item, -amount);
        for (Storage storage : storages) {
            if (storage.getStorage().containsKey(item)) {
                if (amount == 0)
                    break;
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

}