package model.buildings.enums;

import model.AllResource;

public enum ChurchType {
    CHAPEL("chapel", 3, 250, 250, AllResource.NONE,
            0, true, false),
    CATHEDRAL("cathedral", 6, 450, 1000, AllResource.NONE,
            0, true, true);

    private final String name;
    private final int size;
    private final int hitPoint;
    private final double goldCost;
    private final AllResource resourceCostType;
    private final int resourceCostNumber;
    private final boolean isActive;
    private final boolean isGeneral;

    ChurchType(String name, int size, int hitPoint, double goldCost,
               AllResource resourceCostType, int resourceCostNumber,
               boolean isActive, boolean isGeneral) {
        this.name = name;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.resourceCostType = resourceCostType;
        this.resourceCostNumber = resourceCostNumber;
        this.isActive = isActive;
        this.isGeneral = isGeneral;
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

    public boolean isActive() {
        return isActive;
    }

    public boolean isGeneral() {
        return isGeneral;
    }

    public static ChurchType getChurchTypeByName(String name) {
        for (ChurchType churchType : ChurchType.values())
            if (churchType.name.equals(name))
                return churchType;
        return null;
    }
}
