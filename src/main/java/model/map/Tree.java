package model.map;

import javafx.scene.image.Image;

public class Tree {
    private final String name;
    private int leftWood = 200;
    private Image image;

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

    public Image getImage() {
        if (image == null)
            image = new Image(System.getProperty("user.dir") + "/src/main/resources/IMG/Tree/" + name + ".png");
        return image;
    }

    public static boolean isTreeName(String name) {
        return name.equals("small")
                || name.equals("date")
                || name.equals("coconut")
                || name.equals("cherry")
                || name.equals("olive");
    }
}
