package model.map;

import controller.BuildingUtils;
import model.Governance;
import model.Stronghold;
import model.buildings.Building;
import model.people.Troop;
import model.people.Unit;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Tile {
    private Texture texture;
    private Building building = null;
    private ArrayList<Unit> units = new ArrayList<>();
    private Tree tree;
    private final int[] location;

    public Tile(int x, int y) {
        texture = Texture.SAND;
        tree = null;
        location = new int[] {x, y};
    }

    public Tile(Texture texture, Tree tree, int x, int y) {
        this.texture = texture;
        this.tree = tree;
        location = new int[] {x, y};
    }

    public static Tile[][] cloneTiles(Tile[][] tiles) throws CloneNotSupportedException {
        Tile[][] cloned = tiles.clone();
        for (int i = 0; i < tiles.length; i++) {
            cloned[i] = tiles[i].clone();
            for (int j = 0; j < tiles[i].length; j++) {
                cloned[i][j] = (Tile) tiles[i][j].clone();
            }
        }
        return cloned;
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

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public ArrayList<Unit> getUnitsByType(String unitType) {
        ArrayList<Unit> selectedUnits = new ArrayList<>();
        for (Unit unit : units) {
            if (unit.getName().equals(unitType))
                selectedUnits.add(unit);
        }
        return selectedUnits;
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

    public int[] getLocation() {
        return location;
    }

    public void clear() {
        this.building = null;
        this.tree = null;
        this.units = new ArrayList<>();
    }

    public boolean hasEnemy(Governance governance) {
        for (Unit unit : units)
            if (!unit.getOwner().equals(governance))
                return true;
        return false;
    }

    public boolean isFull() {
        return building != null || units.size() != 0 || tree != null;
    }

    public boolean hasBuilding() {
        return building != null;
    }

    @Override
    public String toString() {
        String unitsName = "";
        String result = "";
        int i = 1;

        for (Unit unit : units)
            if (!(unit instanceof Troop troop) || troop.isRevealed() || troop.isForCurrentGovernance())
                unitsName += (i++) + ". " + unit.toString() + '\n';

        result += "Coordinates: x=" + location[0] + " y=" + location[1] + '\n';
        result += "Texture: " + texture.getName() + '\n';
        if (BuildingUtils.isBuildingInTile(building)) result += building.toString() + '\n';
        if (units.size() > 0) result += "Units:\n" + unitsName;
        if (tree != null) result += "Tree: " + tree.getName() + '\n';
        if (getResourceAmount() != null) result += getResourceAmount() + '\n';

        return result;
    }

    public void clearUnitsByType(ArrayList<Unit> selectedUnits) {
        this.units.removeAll(selectedUnits);
    }

    public Unit getLastUnitInTile() {
        Unit result = null;
        for (Unit unit : units)
            if (!(unit instanceof Troop troop) || troop.isRevealed() || troop.isForCurrentGovernance())
                result = unit;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tile tile)
            return Arrays.equals(this.location, tile.location);
        return false;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Tile(this.texture, this.tree, this.location[0], this.location[1]);
    }
}
