package Model.people;

import Model.Stronghold;
import Model.people.Enums.Damage;
import Model.people.Enums.Hp;
import Model.people.Enums.Speed;

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
