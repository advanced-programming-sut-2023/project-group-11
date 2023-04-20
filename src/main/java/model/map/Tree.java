package model.map;


public enum Tree {
    SMALL("small"),
    DATE("date"),
    COCONUT("coconut"),
    CHERRY("cherry"),
    OLIVE("olive"),
    ;

    private final String name;

    Tree(String name) {
        this.name = name;
    }

    public static Tree getTreeByName(String name) {
        for (Tree value : Tree.values())
            if (value.name.equals(name)) return value;
        return null;
    }
}
