package model;

import java.util.ArrayList;

public class Path {
    private final ArrayList<int[]> path;

    public Path() {
        this.path = new ArrayList<>();
    }

    public ArrayList<int[]> getPath() {
        return path;
    }

    public void addLocationToPath(int[] location) {
        path.add(location);
    }

    public int getLength() {
        return path.size();
    }

    public void removePath(Path path) {
        this.path.remove(path);
    }
}
