package Model.Buildings.Enums;

import Model.Resources.AllResource;
import Model.Resources.Resource;
import Model.people.Engineer;

import java.util.ArrayList;

public enum StorageType {
    ;

    private final AllResource resource;
    private final int capacity;
    private final int filledCapacity;
    private int size;
    private int hitPoint;
    private int goldCost;
    private Resource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private boolean isActive;
    public ArrayList<Model.people.Engineer> Engineer;

    StorageType(AllResource resource, int capacity, int filledCapacity) {
        this.resource = resource;
        this.capacity = capacity;
        this.filledCapacity = filledCapacity;
    }

    public AllResource getResource() {
        return resource;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFilledCapacity() {
        return filledCapacity;
    }
}
