package Model.people.Enums;

import Model.Resources.Utils;

public enum RangedTroops {
    ARCHER(Hp.LOW, Speed.HIGH, Utils.BOW, null, Damage.LOW, false, false, false, 5, 0.5, 12);

    private final Hp hp;
    private final Speed speed;
    private final Utils weaponType;
    private final Utils armorType;
    private final Damage damage;
    private final boolean hasHorse;
    private final boolean isArab;
    private final boolean hasFiringWeapon;
    private final int range;
    private final double damageRatioOnArmor;
    private int cost;

    RangedTroops(Hp hp, Speed speed, Utils weaponType, Utils armorType, Damage damage, boolean hasHorse,
                 boolean isArab, boolean hasFiringWeapon, int range, double damageRatioOnArmor, int cost) {
        this.hp = hp;
        this.speed = speed;
        this.weaponType = weaponType;
        this.armorType = armorType;
        this.damage = damage;
        this.hasHorse = hasHorse;
        this.isArab = isArab;
        this.hasFiringWeapon = hasFiringWeapon;
        this.range = range;
        this.damageRatioOnArmor = damageRatioOnArmor;
        this.cost = cost;
    }

    public int getHp() {
        return hp.getHp();
    }

    public Speed getSpeed() {
        return speed;
    }

    public Utils getWeaponType() {
        return weaponType;
    }

    public Utils getArmorType() {
        return armorType;
    }

    public int getDamage() {
        return damage.getDamage();
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

    public int getRange() {
        return range;
    }

    public double getDamageRatioOnArmor() {
        return damageRatioOnArmor;
    }

    public int getCost() {
        return cost;
    }
}
