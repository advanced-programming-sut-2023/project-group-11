package model.map;

import model.Stronghold;

public class Map {
    private final String name;
    private final Tile[][] tiles;
    private final int size;

    public Map(String name, int size) {
        this.name = name;
        this.size = size;

        this.tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            tiles[i] = new Tile[size];
            for (int j = 0; j < size; j++) {
                tiles[i][j] = new Tile();
            }
        }
        Stronghold.addMap(this);
    }

    public Map(String name, Tile[][] tiles, int size) {
        this.name = name;
        this.tiles = tiles;
        this.size = size;
        Stronghold.addMap(this);
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
                    return new int[]{i, j};
        return null;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public Tile getTile(int[] location) {
        return tiles[location[0]][location[1]];
    }
}
