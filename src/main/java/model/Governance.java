package model;

import model.buildings.Storage;
import model.resources.AllResource;
import model.resources.Food;
import model.resources.Resource;
import model.resources.TroopEquipment;

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
    private final ArrayList<Trade> previousTrades = new ArrayList<>();
    private final ArrayList<Trade> tradeNotification = new ArrayList<>();
    //popularity = taxFactor + fearFactor + foodFactor + religiousFactor
    private final HashMap<Resource, Integer> resources = new HashMap<>();
    private final HashMap<Food, Integer> foods = new HashMap<>();
    private final HashMap<TroopEquipment, Integer> troopEquipments = new HashMap<>();
    //TODO: implement storages
    private final ArrayList<Storage> storages = new ArrayList<>();

    {
        resources.put(Resource.WOOD, 100);
        resources.put(Resource.STONE, 50);
        resources.put(Resource.IRON, 0);
        resources.put(Resource.WHEAT, 10);
        resources.put(Resource.FLOUR, 0);
        resources.put(Resource.HOP, 0);
        resources.put(Resource.ALE, 0);
        resources.put(Resource.PITCH, 0);
        foods.put(Food.BREAD, 100);
        foods.put(Food.APPLE, 0);
        foods.put(Food.MEAT, 0);
        foods.put(Food.CHEESE, 0);
        troopEquipments.put(TroopEquipment.CROSSBOW, 0);
        troopEquipments.put(TroopEquipment.SPEAR, 0);
        troopEquipments.put(TroopEquipment.BOW, 0);
        troopEquipments.put(TroopEquipment.MACE, 0);
        troopEquipments.put(TroopEquipment.PIKE, 0);
        troopEquipments.put(TroopEquipment.LEATHER_ARMOR, 0);
        troopEquipments.put(TroopEquipment.METAL_ARMOR, 0);
        troopEquipments.put(TroopEquipment.SWORD, 0);
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

    public void addTrade(Trade trade) {
        previousTrades.add(trade);
    }

    public void addResource(Resource resource, int count) {
        resources.put(resource, count);
    }

    public void addFood(Food food, int count) {
        foods.put(food, count);
    }

    public void addResource(TroopEquipment util, int count) {
        troopEquipments.put(util, count);
    }

    public void changeResourceAmount(Object object, int count) {
        if (object instanceof TroopEquipment)
            troopEquipments.put((TroopEquipment) object, troopEquipments.get((TroopEquipment) object) + count);

        if (object instanceof Food)
            foods.put((Food) object, foods.get((Food) object) + count);

        if (object instanceof Resource)
            resources.put((Resource) object, resources.get((Resource) object) + count);
    }

    public int getResourceCount(AllResource resource) {
        return resources.get(resource.getResource());
    }

    public int getFoodCount(Food food) {
        return 0;
    }

    public int getUtilCount(TroopEquipment util) {
        return 0;
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
            if (storage.getStorage().containsKey(item.getResource())) {
                capacity += storage.getCurrentCapacity();
                if(capacity >= amount)
                    return true;
            }
        }
        return false;
    }

    public boolean hasEnoughItem(AllResource item, int amount){
        int capacity = 0;
        for (Storage storage : storages) {
            if (storage.getStorage().containsKey(item.getResource())) {
                capacity += storage.getStorage().get(item.getResource());
                if(capacity >= amount)
                    return true;
            }
        }
        return false;
    }

    public void addToStorage(AllResource item,int amount){
        for (Storage storage:storages){
            if(storage.getStorage().containsKey(item.getResource())){
                if(amount == 0)
                    break;
                if(storage.getCurrentCapacity() <= amount){
                    amount -= storage.getCurrentCapacity();
                    storage.addToStorage(item, storage.getCurrentCapacity());
                }else{
                    storage.addToStorage(item,amount);
                    amount = 0;
                }
            }
        }
    }

    public void removeFromStorage(AllResource item,int amount){
        Object resource = item.getResource();

        for (Storage storage:storages){
            if(storage.getStorage().containsKey(resource)){
                if(amount == 0)
                    break;
                int storageRemaining = storage.getStorage().get(resource);
                if(storageRemaining <= amount){
                    amount -= storageRemaining;
                    storage.removeFromStorage(item, storageRemaining);
                }else{
                    storage.removeFromStorage(item,amount);
                    amount = 0;
                }
            }
        }
    }

}