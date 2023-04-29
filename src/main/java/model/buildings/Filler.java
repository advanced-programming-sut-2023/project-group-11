package model.buildings;

import model.buildings.enums.FillerType;

public class Filler extends Building {
    // wall stair tent ...
    private boolean isWall,
            isClimable = false;

    public Filler(FillerType fillerType) {
        name = fillerType.getName();
        size = fillerType.getSize();
        hitPoint = fillerType.getHitPoint();
        goldCost = fillerType.getGoldCost();
        resourceCostType = fillerType.getResourceCostType();
        resourceCostNumber = fillerType.getResourceCostNumber();
        workersNumber = fillerType.getWorkersNumber();
        isActive = fillerType.isActive();
        areWorkersEngineer = fillerType.AreWorkersEngineer();
        popularityEffect = fillerType.getPopularityEffect();
        isWall = fillerType.isWall();
    }

}
