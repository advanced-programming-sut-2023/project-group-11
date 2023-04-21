package model.buildings.enums;

import model.resources.Resource;

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
    public ArrayList<model.people.Engineer> Engineer;

}
