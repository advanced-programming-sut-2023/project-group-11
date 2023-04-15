package Model.people;

import Model.Stronghold;
import Model.people.Enums.MeleeTroops;

public class MeleeTroop extends Troops{
    private final MeleeTroops type;
    private final boolean canScaleWall;
    private final boolean canDigKhandagh;

    public MeleeTroop(MeleeTroops MELEE_TROOP) {
        this.type = MELEE_TROOP;
        this.hp = MELEE_TROOP.getHp();
        this.speed = MELEE_TROOP.getSpeed();
        this.weaponType = MELEE_TROOP.getWeaponType();
        this.armorType = MELEE_TROOP.getArmorType();
        this.damage = MELEE_TROOP.getDamage();
        this.hasHorse = MELEE_TROOP.hasHorse();
        this.isArab = MELEE_TROOP.isArab();
        this.hasFiringWeapon = MELEE_TROOP.hasFiringWeapon();
        this.canScaleWall = MELEE_TROOP.canScaleWall();
        this.canDigKhandagh = MELEE_TROOP.canDigKhandagh();
        this.cost = MELEE_TROOP.getCost();
        this.ownerGovernance = Stronghold.getCurrentGovernance();
    }

    public boolean isCanScaleWall() {
        return canScaleWall;
    }

    public boolean isCanDigKhandagh() {
        return canDigKhandagh;
    }

    public MeleeTroops getType() {
        return type;
    }
}
