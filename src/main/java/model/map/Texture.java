package model.map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public enum Texture {
    SAND("sand", "desert"),
    SAND_DUNE("sand dune", "desert"),
    ROCK("rock", "rock"),           // UnBuildable
    CLIFF("cliff", "cliff"),      // UnBuildable - Sakhreh
    STONE("stone", "stone"),
    IRON("iron", "iron"),
    GRASS("grass", "grass"),
    DENSE_GRASSLAND("dense grassland", "grass"),
    GRASSLAND("grassland", "grassland"),  // UnBuildable
    OIL("oil", "desert"),
    MARSH("marsh", "marsh"),      // Jolge
    SHALLOW_WATER("shallow water", "shallow_water"),
    RIVER("river", "water"),
    SMALL_LAKE("small lake", "water"),
    BIG_LAKE("big lake", "water"),
    SEA("sea", "water"),
    BEACH("beach", "desert"),
    PITCH("pitch", "pitch")             // unbuildable
    ;

    private final String name;
    private final String textureImageName;

    Texture(String name, String textureImageName) {
        this.name = name;
        this.textureImageName = textureImageName;
    }

    public static Texture getTextureByName(String name) {
        for (Texture value : Texture.values())
            if (value.name.equals(name)) return value;
        return null;
    }

    public boolean isWater() {
        return this.equals(OIL) || this.equals(MARSH) || this.equals(SMALL_LAKE) || this.equals(BIG_LAKE) ||
                this.equals(SEA) || this.equals(SHALLOW_WATER) || this.equals(RIVER);
    }

    public boolean isSuitableForUnit() {
        return !(isWater() || this.equals(CLIFF) || this.equals(PITCH)) ||
                this.equals(MARSH) || this.equals(SHALLOW_WATER) || this.equals(OIL);
    }
    public boolean isSuitableForPitch() {
        return this.equals(SAND_DUNE) || this.equals(SAND) || this.equals(BEACH);
    }

    public boolean isStone() {
        return this.equals(STONE);
    }

    public boolean isIron() {
        return this.equals(IRON);
    }

    public boolean isGrass() {
        return this.equals(GRASS) || this.equals(DENSE_GRASSLAND);
    }

    public boolean isBuildable() {
        return !(isWater() || this.equals(CLIFF) || this.equals(ROCK) || this.equals(PITCH));
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return new Image(System.getProperty("user.dir") + "/src/main/resources/IMG/Textures/" + textureImageName + ".png");
    }
}
