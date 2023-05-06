package model.buildings;

import model.Governance;
import model.buildings.enums.GateHouseType;

public class GateHouse extends Climbable {
    private final int capacity;
    private int filledCapacity;
    private Governance gateController;
    private final boolean horizontalDirection;
    private boolean isOpen;

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
        capacity = gateHouseType.getCapacity();
        horizontalDirection = gateHouseType.getHorizontalDirection();
        isClimbable = true;
        isOpen = true;
    }

    public Governance getGateController() {
        return gateController;
    }

    public void setGateController(Governance gateController) {
        this.gateController = gateController;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
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

    public boolean isHorizontalDirection() {
        return horizontalDirection;
    }
}
