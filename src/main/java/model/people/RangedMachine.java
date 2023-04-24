package model.people;

import model.people.enums.RangedMachineType;

public class RangedMachine extends Machine {

    private int range;
    private RangedMachineType rangedMachineType;

    public RangedMachine(RangedMachineType rangedMachineType) {
        this.rangedMachineType = rangedMachineType;
        speed = rangedMachineType.getSpeed();
        cost = rangedMachineType.getGoldCost();
        size = rangedMachineType.getSize();
        hp = rangedMachineType.getHitPoint();
        workersNumber = rangedMachineType.getWorkersNumber();
        isActive = rangedMachineType.isActive();
        damage = rangedMachineType.getDamage();
    }

    public int getRange() {
        return range;
    }

    public void damage(){

    }

    public void move(){

    }
}
