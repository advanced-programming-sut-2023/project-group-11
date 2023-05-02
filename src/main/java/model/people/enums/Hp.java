package model.people.enums;

public enum Hp {
    VERY_LOW(60),
    LOW(80),
    MEDIUM(100),
    HIGH(120),
    SIEGE_HP(300),
    SHIELD_HP(500);


    private final int hp;

    Hp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }
}
