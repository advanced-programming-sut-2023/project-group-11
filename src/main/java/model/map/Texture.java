package model.map;

import javafx.scene.image.Image;

public enum Texture {
    SAND("sand", "desert"),                         //زمین
    SAND_DUNE("sand dune", "desert"),               //زمین با سنگ ریزه
    ROCK("rock", "rock"),                           // تخته سنگ - UnBuildable
    CLIFF("cliff", "cliff"),                        // UnBuildable - صخره
    STONE("stone", "stone"),                        // معدن سنگ
    IRON("iron", "iron"),                           // معدن آهن
    GRASS("grass", "grass"),                        // چمن
    DENSE_GRASSLAND("dense grassland", "grass"),    //علفزار پرتراکم
    GRASSLAND("grassland", "grassland"),            // UnBuildable
    OIL("oil", "desert"),                           //نفت
    MARSH("marsh", "marsh"),                        // جلگه
    SHALLOW_WATER("shallow water", "shallow_water"),// آب کم عمق
    RIVER("river", "water"),                        // رودخانه
    SMALL_LAKE("small lake", "water"),              // برکه کوچک
    BIG_LAKE("big lake", "water"),                  // برکه بزرگ
    SEA("sea", "water"),                            // دریا
    BEACH("beach", "desert"),                       // ساحل دریا
    PITCH("pitch", "pitch")                         // UnBuildable خندق
    ;

    private final String name;
    private final String textureImageName;
    private transient Image image;

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

    public boolean slowsDown() {
        return this.equals(MARSH) || this.equals(BEACH) || this.equals(SAND_DUNE) || this.equals(SHALLOW_WATER);
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        if (image == null)
            image = new Image(System.getProperty("user.dir") + "/src/main/resources/IMG/Textures/" + textureImageName + ".png");
        return image;
    }
}
