package model.buildings.enums;

import model.AllResource;


public enum TrapType {
    KILLING_PIT("killing pit", 1, 0, 0, AllResource.WOOD, 6,
            0, 100, true),
    PITCH_DITCH("pitch ditch", 1, 0, 0, AllResource.PITCH, 2,
            0, 100, false),
    CAGED_WARDOGS("caged wardogs", 1, 0, 100, AllResource.WOOD, 10,
            0, 100, false);//TODO:1 it's not a trap (update traps in move)!

    private final String name;
    private final int size;
    private final int hitPoint;
    private final int goldCost;
    private final AllResource resourceCostType;
    private final int resourceCostNumber;
    private final int workersNumber;
    private final int damage;
    private final boolean isActive;

    public int getDamage() {
        return damage;
    }

    public boolean isActive() {
        return isActive;
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


    TrapType(String name, int size, int hitPoint, int goldCost, AllResource resourceCostType, int resourceCostNumber,
             int workersNumber, int damage, boolean isActive) {
        this.name = name;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.resourceCostType = resourceCostType;
        this.resourceCostNumber = resourceCostNumber;
        this.workersNumber = workersNumber;
        this.damage = damage;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public static TrapType getTrapTypeByName(String name) {
        for (TrapType trapType : TrapType.values())
            if (trapType.name.equals(name))
                return trapType;
        return null;
    }

}
