package model.buildings.enums;

import model.AllResource;


public enum StorageType {

    STOCKPILE("stockpile", 2, 114, 0, AllResource.NONE, 0,
            0, true, 100),
    ARMOURY("armoury", 2, 196, 0, AllResource.WOOD, 5,
            0, true, 50),
    GRANARY("granary", 2, 114, 0, AllResource.WOOD, 5,
            0, true, 250);
    private final String name;
    private final int size;
    private final int hitPoint;
    private final int goldCost;
    private final AllResource resourceCostType;
    private final int resourceCostNumber;
    private final int workersNumber;
    private final boolean isActive;
    private final int capacity;

    public int getSize() {
        return size;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getGoldCost() {
        return goldCost;
    }

    public AllResource getResourceCostType() {
        return resourceCostType;
    }

    public int getResourceCostNumber() {
        return resourceCostNumber;
    }

    public int getWorkersNumber() {
        return workersNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    StorageType(String name, int size, int hitPoint, int goldCost, AllResource resourceCostType,
                int resourceCostNumber, int workersNumber, boolean isActive, int capacity) {
        this.name = name;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.resourceCostType = resourceCostType;
        this.resourceCostNumber = resourceCostNumber;
        this.workersNumber = workersNumber;
        this.isActive = isActive;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public static StorageType getStorageTypeByName(String name) {
        for (StorageType storageType : StorageType.values())
            if (storageType.name.equals(name))
                return storageType;
        return null;
    }

}
