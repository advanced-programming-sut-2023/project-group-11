package model.people;

import model.Stronghold;
import model.people.Enums.Damage;
import model.people.Enums.Hp;
import model.people.Enums.Speed;

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
