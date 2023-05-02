package model.buildings.enums;

import model.AllResource;

public enum WallType {
    SHORT_WALL("short wall", 1, 300, 0, null, 0,
            0, true, false, 0),
    TALL_WALL("tall wall", 1, 500, 0, null, 0,
            0, true, false, 0),
    STAIRS("stairs", 1, 300, 0, null, 0, 0,
            true, false, 0);

    private final String name;
    private final int size;
    private final int hitPoint;
    private final double goldCost;
    private final AllResource resourceCostType;
    private final int resourceCostNumber;
    private final int workersNumber;
    private final boolean isActive;
    private final boolean areWorkersEngineer;
    private final int popularityEffect;

    WallType(String name, int size, int hitPoint, double goldCost, AllResource resourceCostType, int resourceCostNumber,
             int workersNumber, boolean isActive, boolean areWorkersEngineer, int popularityEffect) {
        this.name = name;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.resourceCostType = resourceCostType;
        this.resourceCostNumber = resourceCostNumber;
        this.workersNumber = workersNumber;
        this.isActive = isActive;
        this.areWorkersEngineer = areWorkersEngineer;
        this.popularityEffect = popularityEffect;
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

    public boolean areWorkersEngineer() {
        return areWorkersEngineer;
    }

    public int getPopularityEffect() {
        return popularityEffect;
    }
}
