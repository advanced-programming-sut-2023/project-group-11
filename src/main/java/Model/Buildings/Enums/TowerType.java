package Model.Buildings.Enums;

import Model.Resources.Resource;
import Model.people.Engineer;
import Model.people.Units;

import java.util.ArrayList;

public enum TowerType {
    ;

    private final int fireRange;
    private final int defendRange;
    private final int unitCapacity;
    private boolean hasStairs;
    private int size;
    private int hitPoint;
    private int goldCost;
    private Resource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private boolean isActive;
    public ArrayList<Engineer> Engineer;
    private ArrayList<Units> units = new ArrayList<>();

    public int getFireRange() {
        return fireRange;
    }

    public int getDefendRange() {
        return defendRange;
    }

    public int getUnitCapacity() {
        return unitCapacity;
    }

    public ArrayList<Units> getUnits() {
        return units;
    }

    TowerType(int fireRange, int defendRange, int unitCapacity, ArrayList<Units> units) {
        this.fireRange = fireRange;
        this.defendRange = defendRange;
        this.unitCapacity = unitCapacity;
        this.units = units;
    }
}
