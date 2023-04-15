package Model.Buildings;

import Model.Buildings.Enums.GateHouseType;
import Model.Governance;

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
