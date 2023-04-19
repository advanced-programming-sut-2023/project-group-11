package model.buildings.Enums;

public enum ChurchType {

    CHAPEL(false, 0),
    CATHEDRAL(true, 5);
    private boolean isGeneral;
    private int makeMonkCost;

    ChurchType(boolean isGeneral, int makeMonkCost) {
        this.isGeneral = isGeneral;
        this.makeMonkCost = makeMonkCost;
    }

    public boolean isGeneral() {
        return isGeneral;
    }

    public int getMakeMonkCost() {
        return makeMonkCost;
    }
}
