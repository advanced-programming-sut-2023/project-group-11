package model.buildings;

import model.buildings.enums.MachineType;
import model.people.enums.Speed;

public class Machine extends Building{

//    portable-shield * battering-rams * siege-tower * catapults * trebuchets * fire-ballista
    private Speed speed;
    private int damage;
    private MachineType machineType;

    public void move(){

    }

    public Machine(MachineType machineType) {
    }

    public Machine() {
    }

    public void damage(){

    }
}
