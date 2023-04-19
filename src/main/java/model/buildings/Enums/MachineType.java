package model.buildings.Enums;

import model.Governance;
import model.resources.Resource;
import model.people.Enums.Speed;

import java.util.ArrayList;

public enum MachineType {

    ;

    private Governance owner;
    private int size;
    private int hitPoint;
    private int goldCost;
    private Resource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private boolean isActive;
    public ArrayList<model.people.Engineer> Engineer;
    private Speed speed;
    private int damage;

    MachineType(Governance owner, int size, int hitPoint,
                int goldCost, Resource resourceCostType, int resourceCostNumber,
                int workersNumber, boolean isActive, ArrayList<model.people.Engineer> engineer,
                Speed speed, int damage) {
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
    }
}
