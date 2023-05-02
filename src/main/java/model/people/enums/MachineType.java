package model.people.enums;

public enum MachineType {

    PORTABLE_SHIELD("portable shield", Hp.SHIELD_HP, 50, 1, Speed.VERY_HIGH, SiegeDamage.NO_ATTACK, 0),
    BATTERING_RAM("battering ram", Hp.SIEGE_HP, 150, 4, Speed.VERY_LOW, SiegeDamage.HIGH, 1),
    SIEGE_TOWER("siege tower", Hp.SIEGE_HP, 150, 4, Speed.MEDIUM, SiegeDamage.NO_ATTACK, 0),
    TREBUCHETS("trebuchets", Hp.SIEGE_HP, 150, 3, Speed.CONSTANT, SiegeDamage.MEDIUM, 10),
    FIRE_BALLISTA("fire ballista", Hp.SIEGE_HP, 150, 2, Speed.LOW, SiegeDamage.MEDIUM, 10),
    CATAPULTS("catapults", Hp.SIEGE_HP, 150, 2, Speed.MEDIUM, SiegeDamage.HIGH, 15),
    ;

    private final String name;
    private final Hp hitPoint;
    private final int goldCost;
    private final int workersNumber;
    private final Speed speed;
    private final SiegeDamage damage;
    private final int range;

    MachineType(String name, Hp hitPoint, int goldCost, int workersNumber, Speed speed, SiegeDamage damage, int range) {
        this.name = name;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.workersNumber = workersNumber;
        this.speed = speed;
        this.damage = damage;
        this.range = range;
    }

    public String getName() {
        return name;
    }

    public int getHitPoint() {
        return hitPoint.getHp();
    }

    public int getGoldCost() {
        return goldCost;
    }

    public int getWorkersNumber() {
        return workersNumber;
    }

    public Speed getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage.getDamage();
    }

    public int getRange() {
        return range;
    }

    public static MachineType getMachineTypeByName(String name) {
        for (MachineType machineType : MachineType.values())
            if (machineType.name.equals(name))
                return machineType;
        return null;
    }
}
