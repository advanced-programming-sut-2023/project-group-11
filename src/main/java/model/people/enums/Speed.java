package model.people.enums;

public enum Speed {

    CONSTANT(0),
    VERY_LOW(1),
    LOW(2),
    MEDIUM(3),
    HIGH(4),
    VERY_HIGH(5);

    private final int movesInEachTurn;

    Speed(int movesInEachTurn) {
        this.movesInEachTurn = movesInEachTurn;
    }

    public int getMovesInEachTurn() {
        return movesInEachTurn;
    }
}
