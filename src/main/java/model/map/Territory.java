package model.map;

public enum Territory {
    UP_RIGHT(1),
    UP_LEFT(2),
    DOWN_LEFT(3),
    DOWN_RIGHT(4);

    private final int area;

    Territory(int area) {
        this.area = area;
    }

    public int getArea() {
        return area;
    }

    public Territory getTerritoryByArea(int area) {
        for (Territory value : values())
            if (value.getArea() == area) return value;
        return null;
    }
}
