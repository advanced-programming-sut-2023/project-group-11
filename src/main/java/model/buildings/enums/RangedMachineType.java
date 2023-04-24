package model.buildings.enums;

import model.Governance;
import model.resources.Resource;
import model.people.enums.Speed;

import java.util.ArrayList;

public enum RangedMachineType {

    ;

    private String name;
    private int size;
    private int hitPoint;
    private int goldCost;
    private Resource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private boolean isActive;
    private Speed speed;
    private int damage;
    private int range;

    RangedMachineType(String name, int size, int hitPoint, int goldCost,
                      Resource resourceCostType, int resourceCostNumber, int workersNumber,
                      boolean isActive,
                      Speed speed, int damage, int range) {

        this.name = name;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.resourceCostType = resourceCostType;
        this.resourceCostNumber = resourceCostNumber;
        this.workersNumber = workersNumber;
        this.isActive = isActive;
        this.speed = speed;
        this.damage = damage;
        this.range = range;
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
