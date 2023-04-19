package model.buildings;

import model.resources.Utils;
import model.people.Units;

public class UnitMaker extends Building {

    private int unitCost;
    private Utils unitUtilType;
    private int unitUtilNumber;
    private Units MadeUnit;

    public int getUnitCost() {
        return unitCost;
    }

    public int getUnitUtilNumber() {
        return unitUtilNumber;
    }

    public Utils getUnitUtilType() {
        return unitUtilType;
    }

    public Units getMadeUnit() {
        return MadeUnit;
    }

    public void makeEngineer(){

    }

    public void makeLadderMan(){

    }

    public void makeMilitaryUnit(){

    }

}
