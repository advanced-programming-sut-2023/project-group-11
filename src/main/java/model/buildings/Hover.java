package model.buildings;

import model.resources.AllResource;

public class Hover extends Building{

    private final int capacity;

    public Hover() {
        capacity = 8;
        name = "hovel";
        size = 5;
        hitPoint = 39;
        maxHitPoint = 39;
        goldCost = 0;
        resourceCostType = AllResource.WOOD;
        resourceCostNumber = 6;
        workersNumber = 0;
        areWorkersEngineer = false;
        engineers = null;
        popularityEffect = 0;
    }

    public int getCapacity() {
        return capacity;
    }
}
