package model.buildings;

import model.buildings.Enums.GateHouseType;
import model.Governance;

public class GateHouse extends Building{

    private int capacity,filledCapacity;
    private Governance gateController;

    public GateHouse(GateHouseType gateHouseType) {

    }

    public int getCapacity() {
        return capacity;
    }

    public int getFilledCapacity() {
        return filledCapacity;
    }

    public void setFilledCapacity(int filledCapacity) {
        this.filledCapacity = filledCapacity;
    }
}
