package model.people;

import model.people.Units;

public class Attacker extends Units {
    protected int damage;
    protected int range;
    protected boolean attacked = false;

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public boolean hasAttacked() {
        return attacked;
    }

    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }
}
