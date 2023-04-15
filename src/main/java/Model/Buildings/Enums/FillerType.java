package Model.Buildings.Enums;

import Model.Resources.Resource;
import Model.people.Engineer;

import java.util.ArrayList;

public enum FillerType {

    WALL, STAIRS;

    private int size;
    private int hitPoint;
    private int goldCost;
    private Resource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private boolean isActive;
    public ArrayList<Model.people.Engineer> Engineer;

}
