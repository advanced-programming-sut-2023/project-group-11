package model.buildings.enums;

import model.AllResource;

public enum UnitMakerType {
    MERCENARY_TENT("mercenary tent", 3, 150, 0, AllResource.WOOD, 10,
            0, true, true, false),
    BARRACKS("barracks", 3, 250, 0, AllResource.STONE, 15,
            0, true, false, false),
    ENGINEER_GUILD("engineer guild", 3, 250, 100, AllResource.WOOD, 10,
            0, true, false, true);


    private final String name;
    private final int size;
    private final int hitPoint;
    private final int goldCost;
    private final AllResource resourceCostType;
    private final int resourceCostNumber;
    private final int workersNumber;
    private final boolean isActive;
    private final boolean isMercenaryMaker;
    private final boolean isEngineerMaker;

    UnitMakerType(String name, int size, int hitPoint, int goldCost, AllResource resourceCostType,
                  int resourceCostNumber, int workersNumber, boolean isActive,
                  boolean isMercenaryMaker, boolean isEngineerMaker) {
        this.name = name;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.resourceCostType = resourceCostType;
        this.resourceCostNumber = resourceCostNumber;
        this.workersNumber = workersNumber;
        this.isActive = isActive;
        this.isMercenaryMaker = isMercenaryMaker;
        this.isEngineerMaker = isEngineerMaker;
    }

    public boolean isMercenaryMaker() {
        return isMercenaryMaker;
    }

    public boolean isEngineerMaker() {
        return isEngineerMaker;
    }

    public int getSize() {
        return size;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getGoldCost() {
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

    public String getName() {
        return name;
    }

    public static UnitMakerType getUnitMakerTypeByName(String name) {
        for (UnitMakerType unitMakerType : UnitMakerType.values())
            if (unitMakerType.name.equals(name))
                return unitMakerType;
        return null;
    }
}
