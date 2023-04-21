package model.buildings;

import model.buildings.enums.ChurchType;

public class Church extends Building{

    private boolean isGeneral;
    private int makeMonkCost;

    public Church(ChurchType churchType) {
        this.isGeneral = churchType.isGeneral();
    }

    public boolean isGeneral() {
        return isGeneral;
    }

    public int getMakeMonkCost() {
        return makeMonkCost;
    }

    public void makeMonk(){

    }

}
