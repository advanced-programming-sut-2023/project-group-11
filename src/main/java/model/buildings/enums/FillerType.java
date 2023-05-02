package model.buildings.enums;

import model.AllResource;


public enum FillerType {

    SHOP("shop", 2, 114, 0, AllResource.WOOD, 5, 1,
            true, false, 0),
    TENT("tent", 1, 1, 0, null, 0, -1,
            true, true, 0),
    STABLE("stable", 3, 114, 400, AllResource.WOOD, 20, 0,
            true, false, 0),
    OX_TETHER("ox tether", 1, 39, 0, AllResource.WOOD, 5, 1,
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

    FillerType(String name, int size, int hitPoint, double goldCost,
               AllResource resourceCostType, int resourceCostNumber, int workersNumber, boolean isActive,
               boolean areWorkersEngineer, int popularityEffect) {
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

    public static FillerType getFillerTypeByName(String name) {
        for (FillerType fillerType : FillerType.values())
            if (fillerType.name.equals(name))
                return fillerType;
        return null;
    }
}
