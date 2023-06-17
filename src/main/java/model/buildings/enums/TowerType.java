package model.buildings.enums;

import model.AllResource;


public enum TowerType {
    LOOKOUT_TOWER("lookout tower", 2, 250, 0, AllResource.STONE, 10,
            0, true, 10, 5, false),
    PERIMETER_TURRET("perimeter turret", 2, 600, 0, AllResource.STONE, 10,
            0, true, 7, 10, false),
    DEFENCE_TURRET("defence turret", 2, 1000, 0, AllResource.STONE, 15,
            0, true, 7, 15, false),
    SQUARE_TOWER("square tower", 3, 1500, 0, AllResource.STONE, 35,
            0, true, 8, 35, true),
    ROUND_TOWER("round tower", 2, 2000, 0, AllResource.STONE, 40,
            0, true, 8, 40, true);


    private final String name;
    private final int size;
    private final int hitPoint;
    private final int goldCost;
    private final AllResource resourceCostType;
    private final int resourceCostNumber;
    private final int workersNumber;
    private final boolean isActive;
    private final int rangeIncrement;
    private final int unitCapacity;
    private final boolean canHaveSiegeEquipment;

    TowerType(String name, int size, int hitPoint, int goldCost, AllResource resourceCostType,
              int resourceCostNumber, int workersNumber, boolean isActive,
              int rangeIncrement, int unitCapacity, boolean canHaveSiegeEquipment) {
        this.name = name;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.resourceCostType = resourceCostType;
        this.resourceCostNumber = resourceCostNumber;
        this.workersNumber = workersNumber;
        this.isActive = isActive;
        this.rangeIncrement = rangeIncrement;
        this.unitCapacity = unitCapacity;
        this.canHaveSiegeEquipment = canHaveSiegeEquipment;
    }

    public boolean canHaveSiegeEquipment() {
        return canHaveSiegeEquipment;
    }


    public int getRangeIncrement() {
        return rangeIncrement;
    }

    public int getUnitCapacity() {
        return unitCapacity;
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

    public static TowerType getTowerTypeByName(String name) {
        for (TowerType towerType : TowerType.values())
            if (towerType.name.equals(name))
                return towerType;
        return null;
    }
}
