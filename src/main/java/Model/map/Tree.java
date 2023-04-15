package Model.map;


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

    public String getName() {
        return name;
    }
}
