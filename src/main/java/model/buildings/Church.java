package model.buildings;

import model.buildings.enums.ChurchType;

public class Church extends Building {

    private final boolean isGeneral;
    private final int makeMonkCost;

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
        makeMonkCost = churchType.getMakeMonkCost();
    }

    public boolean isGeneral() {
        return isGeneral;
    }

    public int getMakeMonkCost() {
        return makeMonkCost;
    }

    public void makeMonk() {
        if (!isGeneral) return;
        owner.setGold(owner.getGold() - makeMonkCost);
        //TODO : more implementations
    }

}
