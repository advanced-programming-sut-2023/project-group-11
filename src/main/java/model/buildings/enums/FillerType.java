package model.buildings.enums;

import model.people.Engineer;
import model.resources.Resource;

import java.util.ArrayList;

public enum FillerType {

    WALL(1,72,0,null,0,0,
            true,false,null,0),
    STAIRS(1,72,0,null,0,0,
            true,false,null,0),
    SHOP(5,114,0,Resource.WOOD,5,1,
            true,false,null,0),
    TENT(1,0,0,null,0,-1,
            true,true,null,0);

    private int size;
    private int hitPoint;
    private double goldCost;
    private Resource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private boolean isActive;
    private boolean areWorkersEngineer;
    private ArrayList<model.people.Engineer> engineers;
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

    public ArrayList<Engineer> getEngineers() {
        return engineers;
    }

    public int getPopularityEffect() {
        return popularityEffect;
    }

    FillerType(int size, int hitPoint, double goldCost,
               Resource resourceCostType, int resourceCostNumber, int workersNumber,
               boolean isActive, boolean areWorkersEngineer, ArrayList<Engineer> engineers, int popularityEffect) {
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.resourceCostType = resourceCostType;
        this.resourceCostNumber = resourceCostNumber;
        this.workersNumber = workersNumber;
        this.isActive = isActive;
        this.areWorkersEngineer = areWorkersEngineer;
        this.engineers = engineers;
        this.popularityEffect = popularityEffect;
    }
}
