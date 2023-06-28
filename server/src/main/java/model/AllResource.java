package model;

import javafx.scene.image.Image;

public enum AllResource {

    BOW("bow", 31),
    CROSSBOW("crossbow", 58),
    SPEAR("spear", 20),
    PIKE("pike", 36),
    MACE("mace", 58),
    SWORD("sword", 58),
    LEATHER_ARMOR("leather", 25),
    METAL_ARMOR("metal", 58),
    HOP("hop", 15),
    ALE("ale", 20),
    WHEAT("wheat", 23),
    FLOUR("flour", 32),
    IRON("iron", 45),
    STONE("stone", 14),
    PITCH("pitch", 20),
    WOOD("wood", 4),
    MEAT("meat", 8),
    APPLE("apple", 8),
    CHEESE("cheese", 8),
    BREAD("bread", 8),
    NONE("none", 0);

    private final String name;
    private final int price;
    private Image image;

    AllResource(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public static AllResource getAllResourceByName(String name) {
        for (AllResource allResource : AllResource.values())
            if (allResource.name.equals(name)) return allResource;
        return null;
    }

    public boolean isFood() {
        return this.equals(AllResource.BREAD) ||
                this.equals(AllResource.MEAT) ||
                this.equals(AllResource.CHEESE) ||
                this.equals(AllResource.APPLE);
    }

    public Image getImage() {
        if (image == null)
            image = new Image(System.getProperty("user.dir") + "/src/main/resources/IMG/AllResource/" + name + ".png");
        return image;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "AllResource{" +
                "name='" + name + '\'' +
                ", buy price=" + price +
                ", sell price=" + price / 2 +
                '}';
    }
}