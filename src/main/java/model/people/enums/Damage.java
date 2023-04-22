package model.people.enums;

public enum Damage {
    NO_ATTACK(0),
    VERY_LOW(20),
    LOW(30),
    MEDIUM(40),
    HIGH(50),
    VERY_HIGH(60);

    private final int damage;

    Damage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
