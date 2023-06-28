package model.buildings;

import model.AllResource;
import model.Governance;

public class Keep extends Climbable {
    public Keep(Governance currentGovernance) {
        this.name = "keep";
        this.size = 3;
        this.hitPoint = 5000;
        this.maxHitPoint = 5000;
        this.goldCost = 0;
        this.resourceCostType = AllResource.NONE;
        this.resourceCostNumber = 0;
        this.workersNumber = 0;
        this.isActive = true;
        this.rangeIncrement = 3;
        this.owner = currentGovernance;
    }
}
