package model.people.enums;

public enum SiegeDamage {
    NO_ATTACK(0),
    MEDIUM(150),
    HIGH(250);

    private final int damage;

    SiegeDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
