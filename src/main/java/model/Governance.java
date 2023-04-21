package model;

import model.resources.Food;
import model.resources.Resource;
import model.resources.TroopEquipment;

import java.util.HashMap;

public class Governance {
    private final User owner;
    private int gold = 2000;
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
    //popularity = taxFactor + fearFactor + foodFactor + religiousFactor
    private final HashMap<Resource, Integer> resources = new HashMap<>();
    private final HashMap<Food, Integer> foods = new HashMap<>();
    private final HashMap<TroopEquipment, Integer> utils = new HashMap<>();

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
        utils.put(TroopEquipment.CROSSBOW, 0);
        utils.put(TroopEquipment.SPEAR, 0);
        utils.put(TroopEquipment.BOW, 0);
        utils.put(TroopEquipment.MACE, 0);
        utils.put(TroopEquipment.PIKE, 0);
        utils.put(TroopEquipment.LEATHER_ARMOR, 0);
        utils.put(TroopEquipment.METAL_ARMOR, 0);
        utils.put(TroopEquipment.SWORD, 0);
    }

    public User getOwner() {
        return owner;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
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

    public void addResource(Resource resource, int count) {
        resources.put(resource, count);
    }

    public void addFood(Food food, int count) {
        foods.put(food, count);
    }

    public void addResource(TroopEquipment util, int count) {
        utils.put(util, count);
    }

    public int getResourceCount(Resource resource) {
        return 0;
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
}