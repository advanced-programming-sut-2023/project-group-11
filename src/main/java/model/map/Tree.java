package model.map;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class Tree {
    private final String name;
    @JsonIgnore
    private int leftWood = 2000;

    public Tree(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getLeftWood() {
        return leftWood;
    }

    public void setLeftWood(int leftWood) {
        this.leftWood = leftWood;
    }

    public static boolean isTreeName(String name) {
        return name.equals("small")
                || name.equals("date")
                || name.equals("coconut")
                || name.equals("cherry")
                || name.equals("olive");
    }
}
