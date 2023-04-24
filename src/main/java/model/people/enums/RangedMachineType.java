package model.people.enums;

public enum RangedMachineType {

    TREBUCHETS("trebuchets", 2, 100, 150,
            3, false, Speed.CONSTANT, 10, 10),
    FIRE_BALLISTA("fire ballista", 2, 100, 150,
            2, false, Speed.LOW, 10, 10),
    CATAPULTS("catapults", 2, 100, 150,
            2, false, Speed.MEDIUM, 10, 10),
    ;

    private String name;
    private int size;
    private int hitPoint;
    private int goldCost;
    private int workersNumber;
    private boolean isActive;
    private Speed speed;
    private int damage;
    private int range;

    RangedMachineType(String name, int size, int hitPoint, int goldCost, int workersNumber,
                      boolean isActive, Speed speed, int damage, int range) {
        this.name = name;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.workersNumber = workersNumber;
        this.isActive = isActive;
        this.speed = speed;
        this.damage = damage;
        this.range = range;
    }


    public int getSize() {
        return size;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getGoldCost() {
        return goldCost;
    }

    public int getWorkersNumber() {
        return workersNumber;
    }

    public boolean isActive() {
        return isActive;
    }


    public Speed getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public static RangedMachineType getMachineTypeByName(String name) {
        for (RangedMachineType rangedMachineType : RangedMachineType.values())
            if (rangedMachineType.name.equals(name))
                return rangedMachineType;
        return null;
    }
}
