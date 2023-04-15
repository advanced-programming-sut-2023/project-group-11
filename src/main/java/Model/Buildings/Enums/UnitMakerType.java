package Model.Buildings.Enums;

import Model.Resources.Resource;
import Model.Resources.Utils;
import Model.people.Engineer;
import Model.people.Units;

import java.util.ArrayList;

public enum UnitMakerType {
    ;

    private final int unitCost;
    private Utils unitUtilType;
    private final int unitUtilNumber;
    private Units MadeUnit;
    private int size;
    private int hitPoint;
    private int goldCost;
    private Resource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private boolean isActive;
    public ArrayList<Model.people.Engineer> Engineer;

    UnitMakerType(int unitCost, Utils unitUtilType, int unitUtilNumber, Units madeUnit) {
        this.unitCost = unitCost;
        this.unitUtilType = unitUtilType;
        this.unitUtilNumber = unitUtilNumber;
        MadeUnit = madeUnit;
    }

    public int getUnitCost() {
        return unitCost;
    }

    public Utils getUnitUtilType() {
        return unitUtilType;
    }

    public int getUnitUtilNumber() {
        return unitUtilNumber;
    }

    public Units getMadeUnit() {
        return MadeUnit;
    }
}
