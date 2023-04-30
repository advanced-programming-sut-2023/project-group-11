package model.buildings.enums;

import model.resources.AllResource;


public enum FillerType {

    WALL("wall",1,72,0,null,0,0,
            true,false,0,true),
    STAIRS("stairs",1,72,0,null,0,0,
            true,false,0,false),
    SHOP("shop",5,114,0,AllResource.WOOD,5,1,
            true,false,0,false),
    TENT("tent",1,0,0,null,0,-1,
            true,true,0,false),
    STABLE("stable",6,114,400, AllResource.WOOD,20,0,
            true,false,0,false),
    OX_TETHER("ox tether",1,39,0,AllResource.WOOD,5,1,
            true,false,0,false);

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
    private boolean isWall;

    public int getSize() {
        return size;
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

    public boolean isAreWorkersEngineer() {
        return areWorkersEngineer;
    }

    public String getName() {
        return name;
    }

    public boolean isWall() {
        return isWall;
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

    FillerType(String name,int size, int hitPoint, double goldCost,
               AllResource resourceCostType, int resourceCostNumber, int workersNumber,
               boolean isActive, boolean areWorkersEngineer, int popularityEffect,boolean isWall) {
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
        this.isWall = isWall;
    }
    public static FillerType getFillerTypeByName(String name){
        for (FillerType fillerType : FillerType.values())
            if(fillerType.name.equals(name))
                return fillerType;
        return null;
    }
}