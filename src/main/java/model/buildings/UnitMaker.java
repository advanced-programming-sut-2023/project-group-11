package model.buildings;

import model.resources.TroopEquipment;
import model.people.Units;

public class UnitMaker extends Building {

    private int unitCost;
    private TroopEquipment unitUtilType;
    private int unitUtilNumber;
    private Units MadeUnit;

    public int getUnitCost() {
        return unitCost;
    }

    public int getUnitUtilNumber() {
        return unitUtilNumber;
    }

    public TroopEquipment getUnitUtilType() {
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
