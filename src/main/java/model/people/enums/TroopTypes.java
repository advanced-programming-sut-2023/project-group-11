package model.people.enums;

import model.resources.TroopEquipment;

public enum TroopTypes {
    ARCHER              (Hp.LOW,        Speed.HIGH,         TroopEquipment.BOW,         null,                Damage.LOW,       false, false, false, 5, 0.5, true, true, true, 12),
    CROSSBOWMAN         (Hp.MEDIUM,     Speed.LOW,          TroopEquipment.CROSSBOW,    null,                Damage.LOW,       false, false, false, 3, 1, false, false, true, 20),
    SPEARMAN            (Hp.VERY_LOW,   Speed.MEDIUM,       TroopEquipment.SPEAR,       null,                Damage.MEDIUM,    false, false, false, 1, 1, true, true, true, 8),
    PIKEMAN             (Hp.HIGH,       Speed.LOW,          TroopEquipment.PIKE,         TroopEquipment.METAL_ARMOR,   Damage.MEDIUM,     false, false, false, 1, 1, false, true, true, 20),
    MACEMAN             (Hp.MEDIUM,     Speed.HIGH,         TroopEquipment.MACE,         TroopEquipment.LEATHER_ARMOR, Damage.HIGH,      false, false, false, 1, 1, true, true, true, 20),
    SWORDSMAN           (Hp.HIGH,       Speed.VERY_LOW,     TroopEquipment.SWORD,        TroopEquipment.METAL_ARMOR,   Damage.VERY_HIGH,  false, false, false, 1, 1, false, false, true, 40),
    KNIGHT              (Hp.HIGH,       Speed.VERY_HIGH,    TroopEquipment.SWORD,        TroopEquipment.METAL_ARMOR,   Damage.VERY_HIGH, true, false, false, 1, 1, false, false, true, 40),
    TUNNELER            (Hp.VERY_LOW,   Speed.HIGH,         null,            null,                Damage.MEDIUM,    false, false, false, 1, 1, false, false, true, 30),
    LADDERMAN           (Hp.VERY_LOW,   Speed.HIGH,         null,            null,                Damage.NO_ATTACK,  false, false, false, 0, 0, false, false, true, 4),
    BLACK_M0NK          (Hp.MEDIUM,     Speed.LOW,          null,            null,                Damage.MEDIUM,     false, false, false, 1, 1, false, false, true, 10),
    ARCHER_BOW          (Hp.LOW,        Speed.HIGH,         null,            null,                Damage.LOW,        false, true, false, 5, 0.5, false, true, true, 75),
    SLAVES              (Hp.VERY_LOW,   Speed.HIGH,         null,            null,                Damage.VERY_LOW,   false, true, true, 1,  1, false, true, true, 5),
    SLINGER             (Hp.VERY_LOW,   Speed.HIGH,         null,            null,                Damage.MEDIUM,     false, true, false, 2, 1, false, false, true, 12),
    ASSASSIN            (Hp.MEDIUM,     Speed.MEDIUM,       null,            null,                Damage.MEDIUM,  false, true, false, 1, 1, false, false, false, 60),
    HORSE_ARCHER        (Hp.MEDIUM,     Speed.VERY_HIGH,    null,            null,                Damage.LOW,        true, true, false, 5, 0.5, false, false, true, 80),
    ARABIAN_SWORDSMAN   (Hp.HIGH,       Speed.LOW,          null,            null,                Damage.HIGH,        false, true, false, 1, 1, false, false, true, 80),
    FIRE_THROWER        (Hp.LOW,        Speed.MEDIUM,       null,            null,                Damage.HIGH,       false, true, true, 3, 1, false, false, true, 100),
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
    private final boolean canScaleWall;
    private final boolean canDigKhandagh;
    private final boolean revealed;
    private final int cost;

    TroopTypes(Hp hp, Speed speed, TroopEquipment weaponType, TroopEquipment armorType, Damage damage, boolean hasHorse,
               boolean isArab, boolean hasFiringWeapon, int range, double damageRatioOnArmor, boolean canScaleWall,
               boolean canDigKhandagh, boolean revealed, int cost) {
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
        this.canScaleWall = canScaleWall;
        this.canDigKhandagh = canDigKhandagh;
        this.revealed = revealed;
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

    public boolean canScaleWall() {
        return canScaleWall;
    }

    public boolean canDigKhandagh() {
        return canDigKhandagh;
    }

    public int getCost() {
        return cost;
    }

    public boolean isRevealed() {
        return revealed;
    }
}
