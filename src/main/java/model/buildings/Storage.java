package model.buildings;

import model.buildings.enums.StorageType;
import model.resources.AllResource;
import model.resources.Food;
import model.resources.Resource;
import model.resources.TroopEquipment;

import java.util.HashMap;

public class Storage extends Building{

    private HashMap<Object,Integer> storage = new HashMap<>();
    private int capacity,currentCapacity;
    private StorageType storageType;

    public Storage(StorageType storageType) {
        this.storageType = storageType;
        switch (storageType){
            case ARMOURY -> {
                storage.put(TroopEquipment.CROSSBOW, 0);
                storage.put(TroopEquipment.SPEAR, 0);
                storage.put(TroopEquipment.BOW, 0);
                storage.put(TroopEquipment.MACE, 0);
                storage.put(TroopEquipment.PIKE, 0);
                storage.put(TroopEquipment.LEATHER_ARMOR, 0);
                storage.put(TroopEquipment.METAL_ARMOR, 0);
                storage.put(TroopEquipment.SWORD, 0);
            }
            case GRANARY -> {
                storage.put(Food.BREAD, 100);
                storage.put(Food.APPLE, 0);
                storage.put(Food.MEAT, 0);
                storage.put(Food.CHEESE, 0);
            }
            case STOCKPILE -> {
                storage.put(Resource.WOOD, 100);
                storage.put(Resource.STONE, 50);
                storage.put(Resource.IRON, 0);
                storage.put(Resource.WHEAT, 10);
                storage.put(Resource.FLOUR, 0);
                storage.put(Resource.HOP, 0);
                storage.put(Resource.ALE, 0);
                storage.put(Resource.PITCH, 0);
            }
        }
        size = storageType.getSize();
        hitPoint = storageType.getHitPoint();
        goldCost = storageType.getGoldCost();
        resourceCostType = storageType.getResourceCostType();
        resourceCostNumber = storageType.getResourceCostNumber();
        workersNumber = storageType.getWorkersNumber();
        isActive = storageType.isActive();
        capacity = storageType.getCapacity();
    }

    public HashMap<Object, Integer> getStorage() {
        return storage;
    }

    public int getCapacity() {
        return capacity;
    }

    public void addToStorage(AllResource resource,int amount){
        Object object = resource.getResource();
        storage.put(object, storage.get(object) + amount);
        currentCapacity += amount;
    }

}
