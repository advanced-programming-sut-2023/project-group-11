package view.enums;

public enum Zoom {
    VERY_LOW(10, 0),
    LOW(18, 1),
    NORMAL(30, 2),
    HIGH(45, 3),
    VERY_HIGH(90, 4),
    ;
    private final int size;
    private final int level;

    Zoom(int size, int level) {
        this.size = size;
        this.level = level;
    }

    public int getSize() {
        return size;
    }

    public int getLevel() {
        return level;
    }

    public static Zoom getZoomByLevel(int level) {
        for (Zoom zoom : Zoom.values())
            if (zoom.level == level) return zoom;
        return null;
    }
}
