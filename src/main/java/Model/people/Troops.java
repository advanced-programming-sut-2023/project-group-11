package Model.people;

import Model.Resources.Utils;

public abstract class Troops extends Units {
    protected Utils weaponType;
    protected Utils armorType;
    protected int damage;
    protected boolean hasHorse;
    protected boolean isArab;
    protected boolean hasFiringWeapon;

    public Utils getWeaponType() {
        return weaponType;
    }

    public Utils getArmorType() {
        return armorType;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isHasHorse() {
        return hasHorse;
    }

    public boolean isArab() {
        return isArab;
    }

    public boolean isHasFiringWeapon() {
        return hasFiringWeapon;
    }
}
