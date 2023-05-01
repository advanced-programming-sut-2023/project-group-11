package model.buildings;

import model.AllResource;


public class Inn extends Building {

    private int popularityRate;
    private int wineUsageRate;

    public Inn() {
        name = "inn";
        size = 5;
        hitPoint = 114;
        maxHitPoint = 114;
        goldCost = 100;
        resourceCostType = AllResource.WOOD;
        resourceCostNumber = 20;
        workersNumber = 1;
        isActive = true;
        areWorkersEngineer = false;
        engineers = null;
        popularityEffect = 0;
        popularityRate = 2;
        wineUsageRate = 10;
    }

    public int getPopularityRate() {
        return popularityRate;
    }

    public int getWineUsageRate() {
        return wineUsageRate;
    }

    public void addToPopularity(){
        owner.setPopularity(owner.getPopularity() + popularityRate);
    }

    public void oneTurnPass(){

    }

}
