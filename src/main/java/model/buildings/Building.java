package model.buildings;

import model.AllResource;
import model.Governance;

public abstract class Building {
    protected int xCoordinate, yCoordinate;
    protected Governance owner;
    protected int size;
    protected int hitPoint;
    protected int maxHitPoint;
    protected double goldCost;
    protected AllResource resourceCostType;
    protected int resourceCostNumber;
    protected int workersNumber;
    protected boolean isActive;
    protected String name;

    public void setOwner(Governance owner) {
        this.owner = owner;
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

    public String getName() {
        return name;
    }

    public void repair() {
        hitPoint = maxHitPoint;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    @Override
    public String toString() {
        return "Building Type: " + name + " HP: " + hitPoint;
    }
}
