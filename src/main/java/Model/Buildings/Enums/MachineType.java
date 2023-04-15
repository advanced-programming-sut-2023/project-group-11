package Model.Buildings.Enums;

import Model.Governance;
import Model.Resources.Resource;
import Model.people.Engineer;
import Model.people.Enums.Speed;

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
    public ArrayList<Model.people.Engineer> Engineer;
    private Speed speed;
    private int damage;

    MachineType(Governance owner, int size, int hitPoint,
                int goldCost, Resource resourceCostType, int resourceCostNumber,
                int workersNumber, boolean isActive, ArrayList<Model.people.Engineer> engineer,
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
