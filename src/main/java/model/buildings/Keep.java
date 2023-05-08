package model.buildings;

import model.AllResource;
import model.Governance;

public class Keep extends Climbable {
    public Keep() {
        this.name = "keep";
        this.size = 2;
        this.hitPoint = 1000;
        this.maxHitPoint = 1000;
        this.goldCost = 0;
        this.resourceCostType = AllResource.NONE;
        this.resourceCostNumber = 0;
        this.workersNumber = 0;
        this.isActive = true;
        this.isClimbable = true;
    }
}
