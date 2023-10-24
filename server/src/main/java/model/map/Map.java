package model.map;

import model.Stronghold;
import model.User;

import java.util.ArrayList;

public class Map {
    private final String name;
    private transient User mainOwner;
    private ArrayList<String> owners = new ArrayList<>();
    private final Tile[][] tiles;
    private final int size;

    public Map(String name, int size) {
        this.name = name;
        this.size = size;

        this.tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            tiles[i] = new Tile[size];
            for (int j = 0; j < size; j++) {
                tiles[i][j] = new Tile(j, i);
            }
        }
        mainOwner = Stronghold.getCurrentUser();
        owners.add(mainOwner.getUsername());
        Stronghold.addMap(this);
    }

    public Map(String name, Tile[][] tiles, int size, ArrayList<String> owners, boolean isClone) {
        this.name = name;
        this.tiles = tiles;
        this.size = size;
        this.owners = owners;
        this.mainOwner = Stronghold.getUserByUsername(owners.get(0));
        if (!isClone) Stronghold.addMap(this);
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int[] getTileLocation(Tile tile) {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (tiles[i][j].equals(tile))
                    return new int[]{j, i};
        return null;
    }

    public Tile getTile(int x, int y) {
        try {
            return tiles[y][x];
        }catch (Exception e){
            return null;
        }
    }

    public Tile getTile(int[] location) {
        return tiles[location[1]][location[0]];
    }

    public User getMainOwner() {
        return mainOwner;
    }

    public ArrayList<String> getOwners() {
        return owners;
    }

    public void addOwner(String username) {
        owners.add(username);
    }

    @Override
    public String toString() {
        return getName();
    }
}
