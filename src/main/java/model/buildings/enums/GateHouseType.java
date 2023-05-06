package model.buildings.enums;

import model.AllResource;


public enum GateHouseType {

    SMALL_HORIZONTAL("small gatehouse", 2, 400, 0, AllResource.STONE, 10,
            0, true, 15, true),
    SMALL_VERTICAL("small gatehouse", 2, 400, 0, AllResource.STONE, 10,
            0, true, 15, false),
    LARGE_HORIZONTAL("large gatehouse", 3, 500, 0, AllResource.STONE, 20,
            0, true, 25, true),
    LARGE_VERTICAL("large gatehouse", 3, 500, 0, AllResource.STONE, 20,
            0, true, 25, false);

    private final String name;
    private final int size;
    private final int hitPoint;
    private final double goldCost;
    private final AllResource resourceCostType;
    private final int resourceCostNumber;
    private final int workersNumber;
    private final boolean isActive;
    private final int capacity;
    private final boolean horizontalDirection;

    GateHouseType(String name, int size, int hitPoint, double goldCost,
                  AllResource resourceCostType, int resourceCostNumber, int workersNumber,
                  boolean isActive, int capacity, boolean horizontalDirection) {
        this.name = name;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.resourceCostType = resourceCostType;
        this.resourceCostNumber = resourceCostNumber;
        this.workersNumber = workersNumber;
        this.isActive = isActive;
        this.capacity = capacity;
        this.horizontalDirection = horizontalDirection;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public double getGoldCost() {
        return goldCost;
    }

    public AllResource getResourceCostType() {
        return resourceCostType;
    }

    public int getResourceCostNumber() {
        return resourceCostNumber;
    }

    public int getWorkersNumber() {
        return workersNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean getHorizontalDirection() {
        return horizontalDirection;
    }

    public static GateHouseType getGateHouseTypeByName(String name) {
        for (GateHouseType gateHouseType : GateHouseType.values())
            if (gateHouseType.name.equals(name))
                return gateHouseType;
        return null;
    }
}
