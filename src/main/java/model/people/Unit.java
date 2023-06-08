package model.people;

import javafx.scene.image.Image;
import model.Governance;
import model.Stronghold;
import model.map.Tile;
import model.people.enums.Speed;
import model.people.enums.UnitState;

public abstract class Unit {
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
    private Image image = null;

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

    public void initializeUnit(Tile tile, boolean isDropping) {
        this.ownerGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        this.ownerGovernance.addUnit(this);
        this.setLocation(Stronghold.getCurrentGame().getMap().getTileLocation(tile));
        if (!(this instanceof Machine || this instanceof Lord || isDropping))
            this.ownerGovernance.changeCurrentPopulation(-1);
        tile.getUnits().add(this);
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

    public void stopPatrol() {
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

    public void removeFromGame(Tile tile) {
        tile.getUnits().remove(this);
        ownerGovernance.removeUnit(this);
    }

    public String toString() {
        return "UnitType->" + name + " * HP->" + hp + " * State->" + unitState + " * Moves Left->" + leftMoves +
                " * Speed->" + speed.getMovesInEachTurn() + " * Owner->" + ownerGovernance.getOwner().getNickname();
    }

    public Image getImage() {
        if (image == null)
            image = new Image(System.getProperty("user.dir") + "/src/main/resources/IMG/Units/" + name + ".png");
        return image;
    }
}
