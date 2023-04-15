package Model.Buildings;

import Model.Buildings.Building;
import Model.Resources.Utils;
import Model.people.Units;

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
