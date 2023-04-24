package model.buildings.enums;

import model.people.Engineer;
import model.resources.Resource;

import java.util.ArrayList;

public enum GateHouseType {

    SMALL("small gatehouse",5,114,0,null,0,0,
            true,false,0,8),
    LARGE("large gatehouse",7,150,0,Resource.STONE,20,0,
            true,false,0,10);

    private String name;
    private int size;
    private int hitPoint;
    private double goldCost;
    private Resource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private boolean isActive;
    private boolean areWorkersEngineer;
    private int popularityEffect;
    private int capacity;


    GateHouseType(String name,int size, int hitPoint, double goldCost,
                  Resource resourceCostType, int resourceCostNumber, int workersNumber,
                  boolean isActive, boolean areWorkersEngineer,
                  int popularityEffect, int capacity) {
        this.name = name;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.resourceCostType = resourceCostType;
        this.resourceCostNumber = resourceCostNumber;
        this.workersNumber = workersNumber;
        this.isActive = isActive;
        this.areWorkersEngineer = areWorkersEngineer;
        this.popularityEffect = popularityEffect;
        this.capacity = capacity;
    }

    public int getSize() {
        return size;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public double getGoldCost() {
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

    public boolean AreWorkersEngineer() {
        return areWorkersEngineer;
    }


    public int getPopularityEffect() {
        return popularityEffect;
    }

    public int getCapacity() {
        return capacity;
    }

    public static GateHouseType getGateHouseTypeByName(String name){
        for (GateHouseType gateHouseType : GateHouseType.values())
            if(gateHouseType.name.equals(name))
                return gateHouseType;
        return null;
    }
}
