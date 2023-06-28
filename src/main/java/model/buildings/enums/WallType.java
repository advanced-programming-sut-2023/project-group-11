package model.buildings.enums;

import model.AllResource;

public enum WallType {
    SHORT_WALL("short wall", 1, 300, 0, AllResource.STONE, 1,
            0, true, 3),
    TALL_WALL("tall wall", 1, 500, 0, AllResource.STONE, 2,
            0, true, 5),
    STAIRS("stairs", 1, 300, 0, AllResource.STONE, 1,
            0, true, 2);

    private final String name;
    private final int size;
    private final int hitPoint;
    private final double goldCost;
    private final AllResource resourceCostType;
    private final int resourceCostNumber;
    private final int workersNumber;
    private final boolean isActive;
    private final int rangeIncrement;

    WallType(String name, int size, int hitPoint, double goldCost, AllResource resourceCostType, int resourceCostNumber,
             int workersNumber, boolean isActive, int rangeIncrement) {
        this.name = name;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.resourceCostType = resourceCostType;
        this.resourceCostNumber = resourceCostNumber;
        this.workersNumber = workersNumber;
        this.isActive = isActive;
        this.rangeIncrement = rangeIncrement;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
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

    public int getRangeIncrement() {
        return rangeIncrement;
    }

    public static WallType getWallTypeByName(String name) {
        for (WallType wallType : WallType.values())
            if (wallType.name.equals(name))
                return wallType;
        return null;
    }
}
