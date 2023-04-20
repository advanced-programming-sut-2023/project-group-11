package model.map;

import model.buildings.Building;
import model.resources.AllResource;
import model.people.Units;

import java.util.ArrayList;

public class Tile {
    private Texture texture = Texture.SAND;
    private Building building = null;
    private ArrayList<Units> units = new ArrayList<>();
    private ArrayList<AllResource> resources = new ArrayList<>();
    private Tree tree = null;

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

    public ArrayList<AllResource> getResource() {
        return resources;
    }

    public void addResource(AllResource resource) {
        this.resources.add(resource);
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
        this.resources = new ArrayList<>();
    }

    public boolean isFull() {
        return building != null || units.size() != 0 || tree != null;
    }
}
