package model.buildings.enums;

import model.AllResource;


public enum TowerType {
    LOOKOUT_TOWER("lookout tower",3,1000,0, AllResource.STONE,10,
            0,true,false,0,10,10,10,false),
    PERIMETER_TURRET("perimeter turret",4,1000,0,AllResource.STONE,10,
            0,true,false,0,10,10,10,false),
    DEFENCE_TURRET("defence turret",5,1000,0,AllResource.STONE,15,
            0,true,false,0,10,10,10,false),
    SQUARE_TOWER("square tower",6,1000,0,AllResource.STONE,35,
            0,true,false,0,10,10,10,true),
    ROUND_TOWER("round tower",3,1000,0,AllResource.STONE,40,
            0,true,false,0,10,10,10,true);


    private final String name;
    private final int size;
    private final int hitPoint;
    private final int goldCost;
    private final AllResource resourceCostType;
    private final int resourceCostNumber;
    private final int workersNumber;
    private final boolean isActive;
    private final boolean areWorkersEngineer;
    private final int popularityEffect;
    private final int fireRange;
    private final int defendRange;
    private final int unitCapacity;
    private final boolean isTower;


    public int getFireRange() {
        return fireRange;
    }

    public int getDefendRange() {
        return defendRange;
    }

    public int getUnitCapacity() {
        return unitCapacity;
    }


    TowerType(String name,int size, int hitPoint, int goldCost, AllResource resourceCostType, int resourceCostNumber,
              int workersNumber, boolean isActive, boolean areWorkersEngineer, int popularityEffect,
              int fireRange, int defendRange, int unitCapacity,boolean isTower) {
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
        this.fireRange = fireRange;
        this.defendRange = defendRange;
        this.unitCapacity = unitCapacity;
        this.isTower = isTower;
    }

    public boolean isTower() {
        return isTower;
    }

    public String getName() {
        return name;
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

    public static TowerType getTowerTypeByName(String name){
        for (TowerType towerType : TowerType.values())
            if(towerType.name.equals(name))
                return towerType;
        return null;
    }

}
