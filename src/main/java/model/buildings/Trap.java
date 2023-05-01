package model.buildings;

import model.buildings.enums.TrapType;

public class Trap extends DefensiveBuilding {

    private final int damage;


    public Trap(TrapType trapType) {
        name = trapType.getName();
        size = trapType.getSize();
        hitPoint = trapType.getHitPoint();
        maxHitPoint = trapType.getHitPoint();
        goldCost = trapType.getGoldCost();
        resourceCostType = trapType.getResourceCostType();
        resourceCostNumber = trapType.getResourceCostNumber();
        workersNumber = trapType.getWorkersNumber();
        isActive = trapType.isActive();
        damage = trapType.getDamage();
    }

    public int getDamage() {
        return damage;
    }

    public void defend() {

    }

}
