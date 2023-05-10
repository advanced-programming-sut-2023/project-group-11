package model;

import java.util.ArrayList;

public class Path {
    private final ArrayList<int[]> path;

    public Path() {
        this.path = new ArrayList<>();
    }
    private Path(ArrayList<int[]> path) {
        this.path = new ArrayList<>(path);
    }

    public ArrayList<int[]> getPath() {
        return path;
    }

    public void addToPath(int[] location) {
        path.add(location);
    }

    public void removeFromPath(int[] location) {
        this.path.remove(location);
    }

    public int getLength() {
        return path.size();
    }

    public Path clone() {
        return new Path(this.getPath());
    }
}
