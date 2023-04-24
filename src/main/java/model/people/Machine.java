package model.people;

import model.people.enums.MachineType;

import java.util.ArrayList;

public class Machine extends Units {

//    portable-shield * battering-rams * siege-tower * catapults * trebuchets * fire-ballista
    protected int size;
    protected int workersNumber;
    protected boolean isActive;
    protected ArrayList<Engineer> engineers;
    protected int damage;
    private MachineType machineType;


    public void move(){

    }

    public Machine(MachineType machineType) {
        this.machineType = machineType;
        speed = machineType.getSpeed();
        cost = machineType.getGoldCost();
        size = machineType.getSize();
        hp = machineType.getHitPoint();
        workersNumber = machineType.getWorkersNumber();
        isActive = machineType.isActive();
        damage = machineType.getDamage();
    }

    public Machine() {
    }

    public void damage(){

    }
}
