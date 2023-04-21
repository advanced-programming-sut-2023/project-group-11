package model.people.enums;

import model.resources.TroopEquipment;

public enum MeleeTroops {
    SPEARMAN(Hp.VERY_LOW, Speed.MEDIUM, TroopEquipment.SPEAR, null, Damage.MEDIUM, false, false, false, true, true, 8),
    PIKEMAN(Hp.HIGH, Speed.LOW, TroopEquipment.PIKE, TroopEquipment.METAL_ARMOR, Damage.MEDIUM, false, false, false, false, true, 8),
    MACEMAN(Hp.MEDIUM, Speed.MEDIUM, TroopEquipment.MACE, TroopEquipment.LEATHER_ARMOR, Damage.HIGH, false, false, false, true, true, 8),
    SWORDSMAN(Hp.HIGH, Speed.VERY_LOW, TroopEquipment.SWORD, TroopEquipment.METAL_ARMOR, Damage.VERY_HIGH, false, false, false, false, false, 8),
    KNIGHT(Hp.HIGH, Speed.VERY_HIGH, TroopEquipment.SWORD, TroopEquipment.METAL_ARMOR, Damage.VERY_HIGH, true, false, false, false, false, 8),
    TUNNELER(Hp.VERY_LOW, Speed.HIGH, null, null, Damage.MEDIUM, false, false, false, false, false, 8),
    BLACK_M0NK(Hp.MEDIUM, Speed.LOW, null, null, Damage.MEDIUM, false, false, false, false, false, 8),
    SLAVES(Hp.VERY_LOW, Speed.HIGH, null, null, Damage.MEDIUM, false, true, true, false, true, 8),
    ARABIAN_SWORDSMAN(Hp.HIGH, Speed.HIGH, null, null, Damage.HIGH, false, true, false, false, false, 8),
    ;

    private final Hp hp;
    private final Speed speed;
    private final TroopEquipment weaponType;
    private final TroopEquipment armorType;
    private final Damage damage;
    private final boolean hasHorse;
    private final boolean isArab;
    private final boolean hasFiringWeapon;
    private final boolean canScaleWall;
    private final boolean canDigKhandagh;
    private final int cost;

    MeleeTroops(Hp hp, Speed speed, TroopEquipment weaponType, TroopEquipment armorType, Damage damage, boolean hasHorse,
                boolean isArab, boolean hasFiringWeapon, boolean canScaleWall, boolean canDigKhandagh, int cost) {
        this.hp = hp;
        this.speed = speed;
        this.weaponType = weaponType;
        this.armorType = armorType;
        this.damage = damage;
        this.hasHorse = hasHorse;
        this.isArab = isArab;
        this.hasFiringWeapon = hasFiringWeapon;
        this.canScaleWall = canScaleWall;
        this.canDigKhandagh = canDigKhandagh;
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

    public boolean canScaleWall() {
        return canScaleWall;
    }

    public boolean canDigKhandagh() {
        return canDigKhandagh;
    }

    public int getCost() {
        return cost;
    }
}
