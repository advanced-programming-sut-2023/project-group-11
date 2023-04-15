package Model.Resources;

public enum Food {
    MEAT("meat"),
    APPLE("apple"),
    CHEESE("cheese"),
    BREAD("bread"),
    ;

    private final String name;

    Food(String name) {
        this.name = name;
    }

    public Food getFoodByName(String name) {
        for (Food food : Food.values())
            if (food.name.equals(name)) return food;
        return null;
    }
}
