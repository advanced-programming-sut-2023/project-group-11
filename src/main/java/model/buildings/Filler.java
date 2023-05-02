package model.buildings;

import model.buildings.enums.FillerType;

public class Filler extends Building {

    public Filler(FillerType fillerType) {
        name = fillerType.getName();
        size = fillerType.getSize();
        hitPoint = fillerType.getHitPoint();
        maxHitPoint = fillerType.getHitPoint();
        goldCost = fillerType.getGoldCost();
        resourceCostType = fillerType.getResourceCostType();
        resourceCostNumber = fillerType.getResourceCostNumber();
        workersNumber = fillerType.getWorkersNumber();
        isActive = fillerType.isActive();
        areWorkersEngineer = fillerType.areWorkersEngineer();
        popularityEffect = fillerType.getPopularityEffect();
    }
}
