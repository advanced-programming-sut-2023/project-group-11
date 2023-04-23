package model.buildings;

import model.buildings.enums.FillerType;

public class Filler extends Building{
// wall stair tent ...

    public Filler(FillerType fillerType) {
        size = fillerType.getSize();
        hitPoint = fillerType.getHitPoint();
        goldCost = fillerType.getGoldCost();
        resourceCostType = fillerType.getResourceCostType();
        resourceCostNumber = fillerType.getResourceCostNumber();
        workersNumber = fillerType.getWorkersNumber();
        isActive = fillerType.isActive();
        areWorkersEngineer = fillerType.AreWorkersEngineer();
        engineers = fillerType.getEngineers();
        popularityEffect = fillerType.getPopularityEffect();
    }

}
