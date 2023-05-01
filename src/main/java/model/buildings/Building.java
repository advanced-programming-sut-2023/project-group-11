package model.buildings;

import model.Governance;
import model.AllResource;
import model.people.Engineer;

import java.util.ArrayList;

public abstract class Building {

    protected Governance owner;
    protected int size;
    protected int hitPoint;
    protected int maxHitPoint;
    protected double goldCost;
    protected AllResource resourceCostType;
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

    public int getMaxHitPoint() {
        return maxHitPoint;
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

    public AllResource getResourceCostType() {
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

    public void repair(){
        hitPoint = maxHitPoint;
    }

    @Override
    public String toString() {
        return "Building Type: " + name + " HP: " + hitPoint;
    }
}
