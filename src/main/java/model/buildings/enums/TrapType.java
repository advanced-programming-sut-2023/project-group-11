package model.buildings.enums;

import model.people.Engineer;
import model.resources.Resource;

import java.util.ArrayList;

public enum TrapType {
    KILLING_PIT("killing pit",1,0,0,Resource.WOOD,6,
            0,100,true),
    PITCH_DITCH("pitch ditch",1,0,0,Resource.PITCH,2,
            0,100,false),
    CAGED_WARDOGS("caged wardogs",3,0,100,Resource.WOOD,10,
            0,100,false);

    private String name;
    private int size;
    private int hitPoint;
    private int goldCost;
    private Resource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private final int damage;
    private boolean isActive;

    public int getDamage() {
        return damage;
    }

    public boolean isActive() {
        return isActive;
    }

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


    TrapType(String name, int size, int hitPoint, int goldCost, Resource resourceCostType, int resourceCostNumber,
             int workersNumber, int damage, boolean isActive) {
        this.name = name;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.resourceCostType = resourceCostType;
        this.resourceCostNumber = resourceCostNumber;
        this.workersNumber = workersNumber;
        this.damage = damage;
        this.isActive = isActive;
    }
}
