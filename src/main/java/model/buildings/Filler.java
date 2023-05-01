package model.buildings;

import model.buildings.enums.FillerType;

public class Filler extends Climbable {
    // wall stair tent ...
    private final boolean isWall;

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
        isWall = fillerType.isWall();
        isClimbable = fillerType.isClimbable();
    }


    public boolean isWall() {
        return isWall;
    }
}
