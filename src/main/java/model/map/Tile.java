package model.map;

import controller.BuildingUtils;
import model.Governance;
import model.buildings.Building;
import model.people.Troops;
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

    public ArrayList<Units> getUnitsByType(String unitType) {
        ArrayList<Units> selectedUnits = new ArrayList<>();
        for (Units unit : units) {
            if (unit.getName().equals(unitType))
                selectedUnits.add(unit);
        }
        return selectedUnits;
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

    public boolean hasEnemy(Governance governance) {
        for (Units units : units) {
            if (!units.getOwnerGovernance().equals(governance))
                return true;
        }
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

        for (Units unit : units)
            if (unit instanceof Troops troop && troop.isRevealed())
                unitsName += (i++) + unit.getName() + "HP: " + unit.getHp() + '\n';

        result += "Texture: " + texture.getName() + '\n';
        if (BuildingUtils.isBuildingInTile(building))
            result += "Building: " + building.getName() + "HP: " + building.getHitPoint() + '\n';
        if (units.size() > 0) result += "Units: " + unitsName;
        if (tree != null) result += "Tree: " + tree.getName() + '\n';
        if (getResourceAmount() != null) result += getResourceAmount() + '\n';

        return result;
    }

    public void clearUnitsByType(ArrayList<Units> selectedUnits) {
        this.units.removeAll(selectedUnits);
    }
}
