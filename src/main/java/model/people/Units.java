package model.people;

import model.Governance;
import model.people.enums.Speed;
import model.people.enums.UnitState;

public abstract class Units {
    protected String name;
    protected int hp;
    protected Speed speed;
    protected int leftMoves;
    protected double cost;
    protected Governance ownerGovernance;
    protected UnitState unitState = UnitState.STANDING;

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getSpeed() {
        return speed.getMovesInEachTurn();
    }

    public UnitState getUnitState() {
        return unitState;
    }

    public void setOwnerGovernance(Governance ownerGovernance) {
        this.ownerGovernance = ownerGovernance;
    }

    public void setUnitState(UnitState unitState) {
        this.unitState = unitState;
    }

    public double getCost() {
        return cost;
    }

    public Governance getOwner() {
        return ownerGovernance;
    }

    public String getName() {
        return name;
    }

    public int getLeftMoves() {
        return leftMoves;
    }

    public void setLeftMoves(int leftMoves) {
        this.leftMoves = leftMoves;
    }
}
