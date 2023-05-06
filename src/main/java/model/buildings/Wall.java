package model.buildings;

import model.buildings.enums.WallType;

public class Wall extends Climbable {
    public Wall(WallType wallType) {
        name = wallType.getName();
        size = wallType.getSize();
        hitPoint = wallType.getHitPoint();
        maxHitPoint = wallType.getHitPoint();
        goldCost = wallType.getGoldCost();
        resourceCostType = wallType.getResourceCostType();
        resourceCostNumber = wallType.getResourceCostNumber();
        workersNumber = wallType.getWorkersNumber();
        isActive = wallType.isActive();
        isClimbable = name.equals("stairs");
    }
}
