package Model.Resources;

public enum AllResource {

    BOW("bow", Utils.BOW, 31),
    CROSSBOW("crossbow", Utils.CROSSBOW, 58),
    SPEAR("spear", Utils.SPEAR, 20),
    PIKE("pike", Utils.PIKE, 36),
    MACE("mace", Utils.MACE, 58),
    SWORD("sword", Utils.SWORD, 58),
    LEATHER_ARMOR("leather", Utils.LEATHER_ARMOR, 25),
    METAL_ARMOR("metal", Utils.METAL_ARMOR, 58),
    HOP("hop", Resource.HOP, 15),
    ALE("ale", Resource.ALE, 20),
    WHEAT("wheat", Resource.WHEAT, 23),
    FLOUR("flour", Resource.FLOUR, 32),
    IRON("iron", Resource.IRON, 45),
    STONE("stone", Resource.STONE, 14),
    PITCH("pitch", Resource.PITCH, 20),
    WOOD("wood", Resource.WOOD, 4),
    MEAT("meat", Food.MEAT, 8),
    APPLE("apple", Food.APPLE, 8),
    CHEESE("cheese", Food.CHEESE, 8),
    BREAD("bread", Food.BREAD, 8);

    private final String name;
    private final int price;

    AllResource(String name, Food food, int price) {
        this.name = name;
        this.price = price;
    }

    AllResource(String name, Resource resource, int price) {
        this.name = name;
        this.price = price;
    }

    AllResource(String name, Utils utils, int price) {
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String printResource() {
        return null;
    }
}
