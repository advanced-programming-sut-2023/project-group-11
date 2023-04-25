package model.buildings;

import model.Governance;
import model.resources.Resource;
import model.people.Engineer;

import java.util.ArrayList;

public abstract class Building {

    protected Governance owner;
    protected int size;
    protected int hitPoint;
    protected double goldCost;
    protected Resource resourceCostType;
    protected int resourceCostNumber;
    protected int workersNumber;
    protected boolean isActive;
    protected boolean areWorkersEngineer;
    protected ArrayList<Engineer> engineers;
    protected int popularityEffect;
    protected String name;

    public void setOwner(Governance owner) {
        this.owner = owner;
    }

    public ArrayList<model.people.Engineer> getEngineer() {
        return engineers;
    }


    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }


    public Governance getOwner() {
        return owner;
    }

    public int getSize() {
        return size;
    }

    public boolean isAreWorkersEngineer() {
        return areWorkersEngineer;
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

    public int getPopularityEffect() {
        return popularityEffect;
    }

    public String getName() {
        return name;
    }
}
