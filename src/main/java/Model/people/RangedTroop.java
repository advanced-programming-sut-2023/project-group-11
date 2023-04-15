package Model.people;

import Model.Stronghold;
import Model.people.Enums.RangedTroops;

public class RangedTroop extends Troops {
    private final RangedTroops type;
    private final int range;
    private final double damageRatioOnArmor;

    public RangedTroop(RangedTroops RANGED_TROOP) {
        this.type = RANGED_TROOP;
        this.hp = RANGED_TROOP.getHp();
        this.speed = RANGED_TROOP.getSpeed();
        this.weaponType = RANGED_TROOP.getWeaponType();
        this.armorType = RANGED_TROOP.getArmorType();
        this.damage = RANGED_TROOP.getDamage();
        this.hasHorse = RANGED_TROOP.hasHorse();
        this.isArab = RANGED_TROOP.isArab();
        this.hasFiringWeapon = RANGED_TROOP.hasFiringWeapon();
        this.range = RANGED_TROOP.getRange();
        this.damageRatioOnArmor = RANGED_TROOP.getDamageRatioOnArmor();
        this.cost = RANGED_TROOP.getCost();
        this.ownerGovernance = Stronghold.getCurrentGovernance();
    }

    public int getRange() {
        return range;
    }

    public double getDamageRatioOnArmor() {
        return damageRatioOnArmor;
    }

    public RangedTroops getType() {
        return type;
    }
}
