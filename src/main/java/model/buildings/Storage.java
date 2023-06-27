package model.buildings;

import model.AllResource;
import model.buildings.enums.StorageType;

import java.util.HashMap;
import java.util.Map;

public class Storage extends Building {

    private final HashMap<AllResource, Integer> storage = new HashMap<>();
    private final int capacity;
    private int currentCapacity;

    public Storage(StorageType storageType) {
        name = storageType.getName();
        switch (storageType) {
            case ARMOURY -> {
                storage.put(AllResource.CROSSBOW, 0);
                storage.put(AllResource.SPEAR, 0);
                storage.put(AllResource.BOW, 0);
                storage.put(AllResource.MACE, 0);
                storage.put(AllResource.PIKE, 0);
                storage.put(AllResource.LEATHER_ARMOR, 0);
                storage.put(AllResource.METAL_ARMOR, 0);
                storage.put(AllResource.SWORD, 0);
            }
            case GRANARY -> {
                storage.put(AllResource.BREAD, 0);
                storage.put(AllResource.APPLE, 0);
                storage.put(AllResource.MEAT, 0);
                storage.put(AllResource.CHEESE, 0);
            }
            case STOCKPILE -> {
                storage.put(AllResource.WOOD, 0);
                storage.put(AllResource.STONE, 0);
                storage.put(AllResource.IRON, 0);
                storage.put(AllResource.WHEAT, 0);
                storage.put(AllResource.FLOUR, 0);
                storage.put(AllResource.HOP, 0);
                storage.put(AllResource.ALE, 0);
                storage.put(AllResource.PITCH, 0);
            }
        }
        size = storageType.getSize();
        hitPoint = storageType.getHitPoint();
        maxHitPoint = storageType.getHitPoint();
        goldCost = storageType.getGoldCost();
        resourceCostType = storageType.getResourceCostType();
        resourceCostNumber = storageType.getResourceCostNumber();
        workersNumber = storageType.getWorkersNumber();
        isActive = storageType.isActive();
        capacity = storageType.getCapacity();
        currentCapacity = capacity;
    }

    public HashMap<AllResource, Integer> getStorage() {
        return storage;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void addToStorage(AllResource resource, int amount) {
        storage.put(resource, storage.get(resource) + amount);
        currentCapacity -= amount;
    }

    public void removeFromStorage(AllResource resource, int amount) {
        storage.put(resource, storage.get(resource) - amount);
        currentCapacity += amount;
    }

    @Override
    public void removeFromGame() {
        super.removeFromGame();
        storage.forEach(((resource, integer) -> owner.changeResourceAmount(resource, -integer)));
        owner.removeStorage(this);
    }

    @Override
    public String toString() {
        String output = "";
        for (Map.Entry<AllResource, Integer> entry : storage.entrySet()) {
            output += "\n" + entry.getKey().getName() + " " + entry.getValue();
        }
        return super.toString() + output;
    }

}
