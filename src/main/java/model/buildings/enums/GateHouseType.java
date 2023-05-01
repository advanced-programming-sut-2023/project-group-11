package model.buildings.enums;

import model.resources.AllResource;


public enum GateHouseType {

    SMALL_HORIZONTAL("small gatehouse",5,114,0,null,0,0,
            true,false,0,8,true),
    LARGE_HORIZONTAL("large gatehouse",7,150,0, AllResource.STONE,20,0,
            true,false,0,10,true),
    SMALL_VERTICAL("small gatehouse",5,114,0,null,0,0,
                             true,false,0,8,false),
    LARGE_VERTICAL("large gatehouse",7,150,0,AllResource.STONE,20,0,
            true,false,0,10,false);

    private final String name;
    private final int size;
    private final int hitPoint;
    private final double goldCost;
    private final AllResource resourceCostType;
    private final int resourceCostNumber;
    private final int workersNumber;
    private final boolean isActive;
    private final boolean areWorkersEngineer;
    private final int popularityEffect;
    private final int capacity;
    private final boolean horizontalDirection;


    GateHouseType(String name,int size, int hitPoint, double goldCost,
                  AllResource resourceCostType, int resourceCostNumber, int workersNumber,
                  boolean isActive, boolean areWorkersEngineer,
                  int popularityEffect, int capacity,boolean horizontalDirection) {
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
        this.horizontalDirection = horizontalDirection;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public double getGoldCost() {
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

    public boolean areWorkersEngineer() {
        return areWorkersEngineer;
    }


    public int getPopularityEffect() {
        return popularityEffect;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean getHorizontalDirection() {
        return horizontalDirection;
    }

    public static GateHouseType getGateHouseTypeByName(String name){
        for (GateHouseType gateHouseType : GateHouseType.values())
            if(gateHouseType.name.equals(name))
                return gateHouseType;
        return null;
    }
}
