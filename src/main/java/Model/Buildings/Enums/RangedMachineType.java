package Model.Buildings.Enums;

import Model.Governance;
import Model.Resources.Resource;
import Model.people.Engineer;
import Model.people.Enums.Speed;

import java.util.ArrayList;

public enum RangedMachineType {

    ;

    private Governance owner;
    private int size;
    private int hitPoint;
    private int goldCost;
    private Resource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private boolean isActive;
    public ArrayList<Model.people.Engineer> Engineer;
    private Speed speed;
    private int damage;
    private int range;

    RangedMachineType(Governance owner, int size, int hitPoint, int goldCost,
                      Resource resourceCostType, int resourceCostNumber, int workersNumber,
                      boolean isActive, ArrayList<Model.people.Engineer> engineer,
                      Speed speed, int damage, int range) {

        this.owner = owner;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.resourceCostType = resourceCostType;
        this.resourceCostNumber = resourceCostNumber;
        this.workersNumber = workersNumber;
        this.isActive = isActive;
        Engineer = engineer;
        this.speed = speed;
        this.damage = damage;
        this.range = range;
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

    public ArrayList<Model.people.Engineer> getEngineer() {
        return Engineer;
    }

    public Speed getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }
}
