package Model.Buildings;

import Model.Resources.AllResource;

public class Storage extends Building{

    private AllResource resource;
    private int capacity,filledCapacity;

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
