package model.map;

import model.Stronghold;

public class Map {
    private final String name;
    private final Tile[][] map;
    private final int size;

    public Map(String name, int size) {
        this.name = name;
        this.size = size;

        this.map  = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            map[i] = new Tile[size];
            for (int j = 0; j < size; j++) {
                map[i][j] = new Tile();
            }
        }
        Stronghold.addMap(this);
    }

    public Map(String name, Tile[][] map, int size) {
        this.name = name;
        this.map = map;
        this.size = size;
        Stronghold.addMap(this);
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public Tile[][] getMap() {
        return map;
    }

    public Tile getTile(int x, int y) {
        return map[x][y];
    }
}
