package model.buildings;

import model.people.Engineer;
import model.resources.Resource;

import java.util.ArrayList;

public class Inn extends Building {

    private int popularityRate;
    private int wineUsageRate;

    public Inn() {

    }

    public int getPopularityRate() {
        return popularityRate;
    }

    public int getWineUsageRate() {
        return wineUsageRate;
    }

    public void oneTurnPass(){

    }

}
