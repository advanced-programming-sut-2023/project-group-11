package model.buildings.enums;

import model.resources.Resource;
import model.people.Engineer;
import model.people.Units;

import java.util.ArrayList;

public enum TowerType {
    LOOKOUT_TOWER("lookout tower",3,1000,0,Resource.STONE,10,
            0,true,false,0,10,10,10),
    PERIMETER_TURRET("perimeter turret",4,1000,0,Resource.STONE,10,
            0,true,false,0,10,10,10),
    DEFENCE_TURRET("defence turret",5,1000,0,Resource.STONE,15,
            0,true,false,0,10,10,10),
    SQUARE_TOWER("square tower",6,1000,0,Resource.STONE,35,
            0,true,false,0,10,10,10),
    ROUND_TOWER("round tower",3,1000,0,Resource.STONE,40,
            0,true,false,0,10,10,10);


    private String name;
    private int size;
    private int hitPoint;
    private int goldCost;
    private Resource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private boolean isActive;
    private boolean areWorkersEngineer;
    private int popularityEffect;
    private final int fireRange;
    private final int defendRange;
    private final int unitCapacity;


    public int getFireRange() {
        return fireRange;
    }

    public int getDefendRange() {
        return defendRange;
    }

    public int getUnitCapacity() {
        return unitCapacity;
    }


    TowerType(String name,int size, int hitPoint, int goldCost, Resource resourceCostType, int resourceCostNumber,
              int workersNumber, boolean isActive, boolean areWorkersEngineer, int popularityEffect,
              int fireRange, int defendRange, int unitCapacity) {
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

    public boolean isActive() {
        return isActive;
    }

    public boolean AreWorkersEngineer() {
        return areWorkersEngineer;
    }

    public int getPopularityEffect() {
        return popularityEffect;
    }


}
