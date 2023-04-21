package model.people;

import model.resources.TroopEquipment;

public abstract class Troops extends Units {
    protected TroopEquipment weaponType;
    protected TroopEquipment armorType;
    protected int damage;
    protected boolean hasHorse;
    protected boolean isArab;
    protected boolean hasFiringWeapon;

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
}
