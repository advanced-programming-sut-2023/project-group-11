package model.map;

import model.buildings.Building;
import model.people.Units;

import java.util.ArrayList;

public class Tile {
    private Texture texture;
    private Building building = null;
    private ArrayList<Units> units = new ArrayList<>();
    private Tree tree;

    public Tile() {
        texture = Texture.SAND;
        tree = null;
    }

    public Tile(Texture texture, Tree tree) {
        this.texture = texture;
        this.tree = tree;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public ArrayList<Units> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Units> units) {
        this.units = units;
    }

    public String getResourceAmount() {
        String result = null;
        if (this.tree != null) result = "Wood: " + tree.getLeftWood();
        else if (this.texture.equals(Texture.IRON) || this.texture.equals(Texture.STONE))
            result = this.texture.getName() + ": Infinite";
        return result;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public void addUnit(Units unit) {
        this.units.add(unit);
    }

    public void clear() {
        this.building = null;
        this.tree = null;
        this.units = new ArrayList<>();
    }

    public boolean isFull() {
        return building != null || units.size() != 0 || tree != null;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "texture=" + texture +
                ", building=" + building +
                ", units=" + units +
                ", tree=" + tree +
                '}';
    }
}
