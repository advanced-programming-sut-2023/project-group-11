package model.map;

public enum Texture {
    SAND("sand", Color.BEIGE_BACKGROUND),
    SAND_DUNE("sand dune", Color.BEIGE_BACKGROUND),
    ROCK("rock", Color.GRAY_BACKGROUND),       // UnBuildable
    CLIFF("cliff", Color.DARK_GRAY_BACKGROUND),      // UnBuildable - Sakhreh
    STONE("stone", Color.SILVER_BACKGROUND),
    IRON("iron", Color.ORANGE_BACKGROUND),
    GRASS("grass", Color.GREEN_BACKGROUND),
    DENSE_GRASSLAND("dense grassland", Color.GREEN_BACKGROUND),
    GRASSLAND("grassland", Color.BRIGHT_GREEN_BACKGROUND),  // UnBuildable
    OIL("oil", Color.BEIGE_BACKGROUND),
    MARSH("marsh", Color.DARK_GREEN_BACKGROUND),      // Jolge
    SHALLOW_WATER("shallow water", Color.BRIGHT_BLUE_BACKGROUND),
    RIVER("river", Color.DARK_BLUE_BACKGROUND),
    SMALL_LAKE("small lake", Color.BLUE_BACKGROUND),
    BIG_LAKE("big lake", Color.BLUE_BACKGROUND),
    SEA("sea", Color.DARK_BLUE_BACKGROUND),
    BEACH("beach", Color.BEIGE_BACKGROUND),
    ;

    private final String name;
    private final Color color;

    Texture(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public static Texture getTextureByName(String name) {
        for (Texture value : Texture.values())
            if (value.name.equals(name)) return value;
        return null;
    }

    public boolean isWater() {
        return this.equals(OIL) || this.equals(MARSH) || this.equals(SMALL_LAKE) || this.equals(BIG_LAKE) ||
                this.equals(SEA) || this.equals(SHALLOW_WATER) || this.equals(RIVER) || this.equals(BEACH);
    }

    public boolean isStone() {
        return this.equals(ROCK) || this.equals(STONE) || this.equals(CLIFF);
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
