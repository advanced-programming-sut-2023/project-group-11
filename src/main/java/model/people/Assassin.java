package model.people;

import model.Stronghold;
import model.people.enums.Damage;
import model.people.enums.Hp;
import model.people.enums.Speed;

public class Assassin extends Troops {
    private boolean revealed = false;

    {
        this.hp = Hp.MEDIUM.getHp();
        this.speed = Speed.MEDIUM;
        this.weaponType = null;
        this.armorType = null;
        this.damage = Damage.MEDIUM.getDamage();
        this.hasHorse = false;
        this.isArab = true;
        this.hasFiringWeapon = false;
        this.cost = 60;
        this.ownerGovernance = Stronghold.getCurrentGovernance();
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }
}
