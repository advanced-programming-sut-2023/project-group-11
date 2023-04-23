package model.buildings;

import model.buildings.enums.GateHouseType;
import model.Governance;

public class GateHouse extends Building{

    private int capacity,filledCapacity;
    private Governance gateController;

    public GateHouse(GateHouseType gateHouseType) {
        size = gateHouseType.getSize();
        hitPoint = gateHouseType.getHitPoint();
        goldCost = gateHouseType.getGoldCost();
        resourceCostType = gateHouseType.getResourceCostType();
        resourceCostNumber = gateHouseType.getResourceCostNumber();
        workersNumber = gateHouseType.getWorkersNumber();
        isActive = gateHouseType.isActive();
        areWorkersEngineer = gateHouseType.AreWorkersEngineer();
        engineers = gateHouseType.getEngineers();
        popularityEffect = gateHouseType.getPopularityEffect();
        capacity = gateHouseType.getCapacity();
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFilledCapacity() {
        return filledCapacity;
    }

    public void setFilledCapacity(int filledCapacity) {
        this.filledCapacity = filledCapacity;
    }
}
