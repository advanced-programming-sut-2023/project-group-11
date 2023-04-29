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

    private String name;
    private int size;
    private int hitPoint;
    private double goldCost;
    private AllResource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private boolean isActive;
    private boolean areWorkersEngineer;
    private int popularityEffect;
    private int capacity;
    private boolean horizontalDirection;


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
