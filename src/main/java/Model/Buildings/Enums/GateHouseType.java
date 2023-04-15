package Model.Buildings.Enums;

public enum GateHouseType {

    SMALL(8),
    LARGE(10);

    private int capacity;
    private int range;

    GateHouseType(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
