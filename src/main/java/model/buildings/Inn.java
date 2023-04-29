package model.buildings;

import model.people.Engineer;
import model.resources.Resource;

import java.util.ArrayList;

public class Inn extends Building {

    private int popularityRate;
    private int wineUsageRate;

    public Inn() {
        name = "inn";
        size = 5;
        hitPoint = 114;
        goldCost = 100;
        resourceCostType = Resource.WOOD;
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
