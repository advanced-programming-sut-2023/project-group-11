package model.people;

import model.Game;
import model.Stronghold;
import model.people.enums.TroopTypes;
import model.resources.TroopEquipment;

public class Troops extends Units {
    protected final TroopTypes type;
    protected TroopEquipment weaponType;
    protected TroopEquipment armorType;
    protected int damage;
    protected boolean hasHorse;
    protected boolean isArab;
    protected boolean hasFiringWeapon;
    protected final boolean canScaleWall;
    protected final boolean canDigKhandagh;
    protected final int range;
    protected final double damageRatioOnArmor;
    protected boolean revealed;


    public Troops(TroopTypes TROOP) {
        this.type = TROOP;
        this.hp = TROOP.getHp();
        this.speed = TROOP.getSpeed();
        this.weaponType = TROOP.getWeaponType();
        this.armorType = TROOP.getArmorType();
        this.damage = TROOP.getDamage();
        this.hasHorse = TROOP.hasHorse();
        this.isArab = TROOP.isArab();
        this.hasFiringWeapon = TROOP.hasFiringWeapon();
        this.range = TROOP.getRange();
        this.damageRatioOnArmor = TROOP.getDamageRatioOnArmor();
        this.canScaleWall = TROOP.canScaleWall();
        this.canDigKhandagh = TROOP.canDigKhandagh();
        this.revealed = TROOP.isRevealed();
        this.cost = TROOP.getCost();
        this.ownerGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
    }

    public TroopTypes getType() {
        return type;
    }

    public TroopEquipment getWeaponType() {
        return weaponType;
    }

    public TroopEquipment getArmorType() {
        return armorType;
    }

    public int getDamage() {
        return damage;
    }

    public boolean hasHorse() {
        return hasHorse;
    }

    public boolean isArab() {
        return isArab;
    }

    public boolean hasFiringWeapon() {
        return hasFiringWeapon;
    }

    public boolean canScaleWall() {
        return canScaleWall;
    }

    public boolean canDigKhandagh() {
        return canDigKhandagh;
    }

    public int getRange() {
        return range;
    }

    public double getDamageRatioOnArmor() {
        return damageRatioOnArmor;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }
}
