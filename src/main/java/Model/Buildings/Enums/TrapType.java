package Model.Buildings.Enums;

import Model.Resources.Resource;
import Model.people.Engineer;

import java.util.ArrayList;

public enum TrapType {
    ;

    private final int damage;
    private boolean isActive;
    private int size;
    private int hitPoint;
    private int goldCost;
    private Resource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    public ArrayList<Model.people.Engineer> Engineer;

    public int getDamage() {
        return damage;
    }

    public boolean isActive() {
        return isActive;
    }

    TrapType(int damage, boolean isActive) {
        this.damage = damage;
        this.isActive = isActive;
    }
}
