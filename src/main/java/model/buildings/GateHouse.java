package model.buildings;

import model.buildings.enums.GateHouseType;
import model.Governance;

public class GateHouse extends Building {

    private final int capacity;
    private int filledCapacity;
    private Governance gateController;
    private boolean isOpen = false;
    private final boolean horizontalDirection;


    public GateHouse(GateHouseType gateHouseType) {
        name = gateHouseType.getName();
        size = gateHouseType.getSize();
        hitPoint = gateHouseType.getHitPoint();
        maxHitPoint = gateHouseType.getHitPoint();
        goldCost = gateHouseType.getGoldCost();
        resourceCostType = gateHouseType.getResourceCostType();
        resourceCostNumber = gateHouseType.getResourceCostNumber();
        workersNumber = gateHouseType.getWorkersNumber();
        isActive = gateHouseType.isActive();
        areWorkersEngineer = gateHouseType.areWorkersEngineer();
        popularityEffect = gateHouseType.getPopularityEffect();
        capacity = gateHouseType.getCapacity();
        horizontalDirection = gateHouseType.getHorizontalDirection();
    }

    public Governance getGateController() {
        return gateController;
    }

    public void setGateController(Governance gateController) {
        this.gateController = gateController;
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

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isHorizontalDirection() {
        return horizontalDirection;
    }
}
