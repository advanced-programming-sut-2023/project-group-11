package model.buildings;

import model.buildings.enums.UnitMakerType;

public class UnitMaker extends Building {
    private final boolean isMercenaryMaker;
    private final boolean isEngineerMaker;

    public UnitMaker(UnitMakerType unitMakerType) {
        name = unitMakerType.getName();
        size = unitMakerType.getSize();
        hitPoint = unitMakerType.getHitPoint();
        maxHitPoint = unitMakerType.getHitPoint();
        goldCost = unitMakerType.getGoldCost();
        resourceCostType = unitMakerType.getResourceCostType();
        resourceCostNumber = unitMakerType.getResourceCostNumber();
        workersNumber = unitMakerType.getWorkersNumber();
        isActive = unitMakerType.isActive();
        isMercenaryMaker = unitMakerType.isMercenaryMaker();
        isEngineerMaker = unitMakerType.isEngineerMaker();
    }

    public void makeUnit(String unitName) {
        //TODO: implementation
    }

    public boolean isMercenaryMaker() {
        return isMercenaryMaker;
    }

    public boolean isEngineerMaker() {
        return isEngineerMaker;
    }
}
