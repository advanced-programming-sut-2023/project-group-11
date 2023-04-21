package model.people.enums;

import model.resources.TroopEquipment;

public enum RangedTroops {
    ARCHER(Hp.LOW, Speed.HIGH, TroopEquipment.BOW, null, Damage.LOW, false, false, false, 5, 0.5, 12),
    CROSSBOWMAN(Hp.MEDIUM, Speed.LOW, TroopEquipment.CROSSBOW, null, Damage.LOW, false, false, false, 3, 1, 12),
    ARCHER_BOW(Hp.LOW, Speed.HIGH, null, null, Damage.LOW, false, true, false, 5, 0.5, 12),
    SLINGER(Hp.VERY_LOW, Speed.HIGH, null, null, Damage.MEDIUM, false, true, false, 2, 1, 12),
    HORSE_ARCHER(Hp.MEDIUM, Speed.VERY_HIGH, null, null, Damage.LOW, true, true, false, 5, 0.5, 12),
    FIRE_THROWER(Hp.LOW, Speed.VERY_HIGH, null, null, Damage.HIGH, false, true, true, 3, 1, 12),
    ;

    private final Hp hp;
    private final Speed speed;
    private final TroopEquipment weaponType;
    private final TroopEquipment armorType;
    private final Damage damage;
    private final boolean hasHorse;
    private final boolean isArab;
    private final boolean hasFiringWeapon;
    private final int range;
    private final double damageRatioOnArmor;
    private final int cost;

    RangedTroops(Hp hp, Speed speed, TroopEquipment weaponType, TroopEquipment armorType, Damage damage, boolean hasHorse,
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

    public TroopEquipment getWeaponType() {
        return weaponType;
    }

    public TroopEquipment getArmorType() {
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
