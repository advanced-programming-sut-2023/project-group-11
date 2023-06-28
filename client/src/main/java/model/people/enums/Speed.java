package model.people.enums;

public enum Speed {

    CONSTANT(0),
    VERY_LOW(2),
    LOW(3),
    MEDIUM(4),
    HIGH(5),
    VERY_HIGH(6);

    private final int movesInEachTurn;

    Speed(int movesInEachTurn) {
        this.movesInEachTurn = movesInEachTurn;
    }

    public int getMovesInEachTurn() {
        return movesInEachTurn;
    }
}
