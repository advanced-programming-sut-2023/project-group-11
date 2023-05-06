package model.buildings;

import model.buildings.enums.TowerType;
import model.people.Units;

import java.util.ArrayList;

public class Tower extends Climbable {

    private int rangeIncrement;
    private int unitCapacity;
    private final boolean canHaveSiegeEquipment;
    private ArrayList<Units> units = new ArrayList<>();

    public Tower(TowerType towerType) {
        name = towerType.getName();
        size = towerType.getSize();
        hitPoint = towerType.getHitPoint();
        maxHitPoint = towerType.getHitPoint();
        goldCost = towerType.getGoldCost();
        resourceCostType = towerType.getResourceCostType();
        resourceCostNumber = towerType.getResourceCostNumber();
        workersNumber = towerType.getWorkersNumber();
        isActive = towerType.isActive();
        rangeIncrement = towerType.getRangeIncrement();
        unitCapacity = towerType.getUnitCapacity();
        canHaveSiegeEquipment = towerType.canHaveSiegeEquipment();
        isClimbable = false;
    }

    public int getRangeIncrement() {
        return rangeIncrement;
    }

    public void setRangeIncrement(int rangeIncrement) {
        this.rangeIncrement = rangeIncrement;
    }

    public int getUnitCapacity() {
        return unitCapacity;
    }

    public void setUnitCapacity(int unitCapacity) {
        this.unitCapacity = unitCapacity;
    }

    public ArrayList<Units> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Units> units) {
        this.units = units;
    }

    public boolean canHaveSiegeEquipment() {
        return canHaveSiegeEquipment;
    }

}
