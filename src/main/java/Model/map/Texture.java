package Model.map;

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
    WATER("water"),
    ;

    private final String name;

    Texture(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
