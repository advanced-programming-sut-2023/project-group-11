package Model.map;

public class Map {
    private final String name;
    private final Tile[][] map;
    private final int size;

    public Map(String name, int size) {
        this.name = name;
        this.size = size;

        this.map  = new Tile[size][size];
        for (Tile[] tiles : map)
            for (Tile tile : tiles)
                tile = new Tile();
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
}
