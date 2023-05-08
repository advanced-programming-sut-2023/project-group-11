package model.people;

import model.Governance;
import model.Stronghold;
import model.map.Tile;
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
    protected int[] location;
    protected int[] patrolOrigin;
    protected int[] patrolDestination;
    protected boolean isPatrolling = false;

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getSpeed() {
        return speed.getMovesInEachTurn();
    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }

    public UnitState getUnitState() {
        return unitState;
    }

    protected void setOwnerGovernance() {
        this.ownerGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        this.ownerGovernance.addUnit(this);
        this.ownerGovernance.changeCurrentPopulation(-1);
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

    public int[] getPatrolOrigin() {
        return patrolOrigin;
    }

    public void setPatrolOrigin(int[] patrolOrigin) {
        this.patrolOrigin = patrolOrigin;
        isPatrolling = true;
    }

    public int[] getPatrolDestination() {
        return patrolDestination;
    }

    public void setPatrolDestination(int[] patrolDestination) {
        this.patrolDestination = patrolDestination;
    }

    public void unPatrol() {
        patrolOrigin[0] = patrolOrigin[1] = patrolDestination[0] = patrolDestination[1] = -1;
        isPatrolling = false;
    }

    public boolean isPatrolling() {
        return isPatrolling;
    }

    public void setPatrolling(boolean patrolling) {
        isPatrolling = patrolling;
    }

    public boolean isForCurrentGovernance() {
        return ownerGovernance.equals(Stronghold.getCurrentGame().getCurrentGovernance());
    }

    public void removeFromGame(Tile tile, Governance owner) {
        tile.getUnits().remove(this);
        owner.removeUnit(this);
    }

    public String toString() {
        return "Unit Type: " + name + " HP: " + hp;
    }
}
