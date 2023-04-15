package Model.people.Enums;

public enum Hp {
    VERY_LOW(60),
    LOW(80),
    MEDIUM(100),
    HIGH(120),
    VERY_HIGH(140);

    private final int hp;

    Hp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }
}
