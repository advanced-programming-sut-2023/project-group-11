package model.buildings.enums;

import model.AllResource;

public enum ChurchType {

    CHAPEL("chapel", 3, 150, 250, null, 0, 0,
            true, false, 2, false, 0),
    CATHEDRAL("cathedral", 6, 459, 1000, null, 0, 0,
            true, false, 2, true, 5);

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
    private final boolean isGeneral;
    private final int makeMonkCost;


    ChurchType(String name, int size, int hitPoint, double goldCost,
               AllResource resourceCostType, int resourceCostNumber, int workersNumber,
               boolean isActive, boolean areWorkersEngineer,
               int popularityEffect, boolean isGeneral, int makeMonkCost) {
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
        this.isGeneral = isGeneral;
        this.makeMonkCost = makeMonkCost;
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

    public boolean AreWorkersEngineer() {
        return areWorkersEngineer;
    }


    public int getPopularityEffect() {
        return popularityEffect;
    }

    public boolean isGeneral() {
        return isGeneral;
    }

    public int getMakeMonkCost() {
        return makeMonkCost;
    }

    public static ChurchType getChurchTypeByName(String name) {
        for (ChurchType churchType : ChurchType.values())
            if (churchType.name.equals(name))
                return churchType;
        return null;
    }
}
