package model.people;

import controller.SelectUnitMenuController;
import model.buildings.Climbable;
import model.map.Tile;

public class Attacker extends Unit {
    protected int damage;
    protected int range;
    protected boolean attacked = false;

    public int getDamage() {
        return damage;
    }

    public int getRange(Tile tile) {
        if (SelectUnitMenuController.isValidUnitForAirAttack(this.getName()) &&
                tile.getBuilding() != null && tile.getBuilding() instanceof Climbable climbable)
            return range + climbable.getRangeIncrement();
        return range;
    }

    public boolean hasAttacked() {
        return attacked;
    }

    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }
}
