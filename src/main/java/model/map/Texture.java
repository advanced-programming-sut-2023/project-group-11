package model.map;

public enum Texture {
    SAND("sand"),
    SAND_DUNE("sand dune"),
    ROCK("rock"),       // Unbuildable
    CLIFF("cliff"),      // Unbuildable - Sakhreh
    STONE("stone"),
    IRON("iron"),
    GRASS("grass"),
    DENSE_GRASSLAND("dense grassland"),
    GRASSLAND("grassland"),  // Unbuildable
    OIL("oil"),
    MARSH("marsh"),      // Jolge
    SHALLOW_WATER("shallow water"),
    RIVER("river"),
    SMALL_LAKE("small lake"),
    BIG_LAKE("big lake"),
    SEA("sea"),
    BEACH("beach"),
    ;

    private final String name;

    Texture(String name) {
        this.name = name;
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
}
