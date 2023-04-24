package model.buildings.enums;

import model.people.Engineer;
import model.resources.Resource;

import java.util.ArrayList;

public enum FillerType {

    WALL("wall",1,72,0,null,0,0,
            true,false,0),
    STAIRS("stairs",1,72,0,null,0,0,
            true,false,0),
    SHOP("shop",5,114,0,Resource.WOOD,5,1,
            true,false,0),
    TENT("tent",1,0,0,null,0,-1,
            true,true,0);

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

    FillerType(String name,int size, int hitPoint, double goldCost,
               Resource resourceCostType, int resourceCostNumber, int workersNumber,
               boolean isActive, boolean areWorkersEngineer, int popularityEffect) {
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
    }
    public FillerType getFillerTypeByName(String name){
        for (FillerType fillerType : FillerType.values())
            if(fillerType.name.equals(name))
                return fillerType;
        return null;
    }
}
