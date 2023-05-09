package model.map;

public enum Territory {
    UP_LEFT(1),
    UP(2),
    UP_RIGHT(3),
    LEFT(4),
    RIGHT(5),
    DOWN_LEFT(6),
    DOWN(7),
    DOWN_RIGHT(8),
    ;

    private final int area;

    Territory(int area) {
        this.area = area;
    }

    public int getArea() {
        return area;
    }

    public static Territory getTerritoryByArea(int area) {
        for (Territory value : values())
            if (value.getArea() == area) return value;
        return null;
    }
}
