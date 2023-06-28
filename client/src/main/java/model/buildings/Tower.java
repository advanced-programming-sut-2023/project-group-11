package model.buildings;

import model.buildings.enums.TowerType;
import model.people.Unit;

import java.util.ArrayList;

public class Tower extends Climbable {

    private final int unitCapacity;
    private final boolean canHaveSiegeEquipment;
    private ArrayList<Unit> units = new ArrayList<>();

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
    }

    public int getUnitCapacity() {
        return unitCapacity;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    public boolean canHaveSiegeEquipment() {
        return canHaveSiegeEquipment;
    }

}
