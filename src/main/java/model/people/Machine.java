package model.people;

import model.people.enums.MachineType;

import java.util.ArrayList;

public class Machine extends Units {
    private final int engineersNeededToActivate;
    private final ArrayList<Engineer> engineers = new ArrayList<>();
    private boolean isActive = false;
    private final int damage;
    private final String name;
    private final int range;

    public Machine(MachineType machineType) {
        name = machineType.getName();
        speed = machineType.getSpeed();
        leftMoves = speed.getMovesInEachTurn();
        cost = machineType.getGoldCost();
        hp = machineType.getHitPoint();
        engineersNeededToActivate = machineType.getWorkersNumber();
        damage = machineType.getDamage();
        range = machineType.getRange();
    }

    public int getEngineersNeededToActivate() {
        return engineersNeededToActivate;
    }

    public ArrayList<Engineer> getEngineers() {
        return engineers;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String getName() {
        return name;
    }
}
