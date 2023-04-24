package model.buildings.enums;

import model.people.Engineer;
import model.resources.AllResource;
import model.resources.Resource;

import java.util.ArrayList;

public enum StorageType {
//    TODO: is it needed?
//    STOCKPILE(),ARMOURY(),GRANARY();
    ;
    private String name;
    private int size;
    private int hitPoint;
    private int goldCost;
    private Resource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private boolean isActive;
    private final AllResource resource;
    private final int capacity;
    private final int filledCapacity;

    public int getSize() {
        return size;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getGoldCost() {
        return goldCost;
    }

    public Resource getResourceCostType() {
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



    StorageType(String name, int size, int hitPoint, int goldCost, Resource resourceCostType,
                int resourceCostNumber, int workersNumber, boolean isActive,
                AllResource resource, int capacity, int filledCapacity) {
        this.name = name;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.resourceCostType = resourceCostType;
        this.resourceCostNumber = resourceCostNumber;
        this.workersNumber = workersNumber;
        this.isActive = isActive;
        this.resource = resource;
        this.capacity = capacity;
        this.filledCapacity = filledCapacity;
    }

    public AllResource getResource() {
        return resource;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFilledCapacity() {
        return filledCapacity;
    }
}
