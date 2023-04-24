package model.buildings;

import model.buildings.enums.TowerType;
import model.people.Units;

import java.util.ArrayList;

public class Tower extends DefensiveBuilding {

    private int fireRange;
    private int defendRange;
    private int unitCapacity;
    private boolean hasGate = false;

    private ArrayList<Units> units = new ArrayList<>();
    public void setHasGate(boolean hasGate) {
        this.hasGate = hasGate;
    }

    public Tower(TowerType towerType) {
        size = towerType.getSize();
        hitPoint = towerType.getHitPoint();
        goldCost = towerType.getGoldCost();
        resourceCostType = towerType.getResourceCostType();
        resourceCostNumber = towerType.getResourceCostNumber();
        workersNumber = towerType.getWorkersNumber();
        isActive = towerType.isActive();
        areWorkersEngineer = towerType.AreWorkersEngineer();
        popularityEffect = towerType.getPopularityEffect();
        fireRange = towerType.getFireRange();
        defendRange = towerType.getDefendRange();
        unitCapacity = towerType.getUnitCapacity();
    }


    public int getFireRange() {
        return fireRange;
    }

    public void setFireRange(int fireRange) {
        this.fireRange = fireRange;
    }

    public int getDefendRange() {
        return defendRange;
    }

    public void setDefendRange(int defendRange) {
        this.defendRange = defendRange;
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

    public void defend(){

    }

}
