package Model.people.Enums;

public enum Speed {

    VERY_LOW(1),
    LOW(2),
    MEDIUM(3),
    HIGH(4),
    VERY_HIGH(5);

    private final int moveInEachTurn;

    Speed(int moveInEachTurn) {
        this.moveInEachTurn = moveInEachTurn;
    }

    public int getMoveInEachTurn() {
        return moveInEachTurn;
    }
}
