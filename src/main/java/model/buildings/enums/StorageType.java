package model.buildings.enums;

import model.people.Engineer;
import model.resources.AllResource;
import model.resources.Resource;

import java.util.ArrayList;

public enum StorageType {

    STOCKPILE("stockpile",5,114,0,null,0,
            0,true,100),
    ARMOURY("armoury",4,196,0,Resource.WOOD,5,
            0,true,100),
    GRANARY("granary",4,114,0,Resource.WOOD,5,
            0,true,100);
    ;
    private String name;
    private int size;
    private int hitPoint;
    private int goldCost;
    private Resource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private boolean isActive;
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

    public int getCapacity() {
        return capacity;
    }

}
