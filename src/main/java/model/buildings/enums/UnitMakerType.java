package model.buildings.enums;

import model.resources.Resource;
import model.resources.TroopEquipment;
import model.people.Units;

import java.util.ArrayList;

public enum UnitMakerType {
    ;

    private final int unitCost;
    private TroopEquipment unitUtilType;
    private final int unitUtilNumber;
    private Units MadeUnit;
    private int size;
    private int hitPoint;
    private int goldCost;
    private Resource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private boolean isActive;
    public ArrayList<model.people.Engineer> Engineer;

    UnitMakerType(int unitCost, TroopEquipment unitUtilType, int unitUtilNumber, Units madeUnit) {
        this.unitCost = unitCost;
        this.unitUtilType = unitUtilType;
        this.unitUtilNumber = unitUtilNumber;
        MadeUnit = madeUnit;
    }

    public int getUnitCost() {
        return unitCost;
    }

    public TroopEquipment getUnitUtilType() {
        return unitUtilType;
    }

    public int getUnitUtilNumber() {
        return unitUtilNumber;
    }

    public Units getMadeUnit() {
        return MadeUnit;
    }
}
