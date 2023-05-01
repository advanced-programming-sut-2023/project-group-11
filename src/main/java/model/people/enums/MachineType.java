package model.people.enums;

public enum MachineType {

    PORTABLE_SHIELD("portable shield", 1, 72, 50,
            1, false, Speed.CONSTANT, 0),
    BATTERING_RAM("battering ram", 2, 72, 150,
            4, false, Speed.VERY_LOW, 31),
    SIEGE_TOWER("siege tower", 3, 72, 150,
            4, false, Speed.LOW, 0);

    private final String name;
    private final int size;
    private final int hitPoint;
    private final int goldCost;
    private final int workersNumber;
    private final boolean isActive;
    private final Speed speed;
    private final int damage;

    MachineType(String name, int size, int hitPoint,
                int goldCost, int workersNumber, boolean isActive,
                Speed speed, int damage) {
        this.name = name;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.workersNumber = workersNumber;
        this.isActive = isActive;
        this.speed = speed;
        this.damage = damage;
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

    public static MachineType getMachineTypeByName(String name) {
        for (MachineType machineType : MachineType.values())
            if (machineType.name.equals(name))
                return machineType;
        return null;
    }
}
