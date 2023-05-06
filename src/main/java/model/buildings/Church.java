package model.buildings;

import model.buildings.enums.ChurchType;

public class Church extends Building {

    private final boolean isGeneral;

    public Church(ChurchType churchType) {
        name = churchType.getName();
        size = churchType.getSize();
        hitPoint = churchType.getHitPoint();
        maxHitPoint = churchType.getHitPoint();
        goldCost = churchType.getGoldCost();
        resourceCostType = churchType.getResourceCostType();
        resourceCostNumber = churchType.getResourceCostNumber();
        workersNumber = churchType.getWorkersNumber();
        isActive = churchType.isActive();
        areWorkersEngineer = churchType.AreWorkersEngineer();
        popularityEffect = churchType.getPopularityEffect();
        isGeneral = churchType.isGeneral();
    }

    public boolean isGeneral() {
        return isGeneral;
    }
}
