package model.buildings.enums;

import model.resources.AllResource;


public enum FillerType {

    SHORT_WALL("short wall", 1, 72, 0, null, 0,
            0, true, false, 0, true),
    TALL_WALL("tall wall", 1, 144, 0, null, 0,
            0, true, false, 0, true),
    STAIRS("stairs", 1, 72, 0, null, 0, 0,
            true, false, 0, false),
    SHOP("shop", 5, 114, 0, AllResource.WOOD, 5, 1,
            true, false, 0, false),
    TENT("tent", 1, 0, 0, null, 0, -1,
            true, true, 0, false),
    STABLE("stable", 6, 114, 400, AllResource.WOOD, 20, 0,
            true, false, 0, false),
    OX_TETHER("ox tether", 1, 39, 0, AllResource.WOOD, 5, 1,
            true, false, 0, false);

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
    private final boolean isWall;

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

    public String getName() {
        return name;
    }

    public boolean isWall() {
        return isWall;
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

    FillerType(String name, int size, int hitPoint, double goldCost,
               AllResource resourceCostType, int resourceCostNumber, int workersNumber,
               boolean isActive, boolean areWorkersEngineer, int popularityEffect, boolean isWall) {
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
        this.isWall = isWall;
    }

    public static FillerType getFillerTypeByName(String name) {
        for (FillerType fillerType : FillerType.values())
            if (fillerType.name.equals(name))
                return fillerType;
        return null;
    }
}
