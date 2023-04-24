package model.buildings.enums;

import model.people.Engineer;
import model.resources.Resource;

import java.util.ArrayList;

public enum ProductiveBuildingType {
    ;

    private String name;
    private int size;
    private int hitPoint;
    private int goldCost;
    private Resource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private boolean isActive;
    public ArrayList<model.people.Engineer> Engineer;
    private final Resource producedResource;
    private Resource requiredResource;
    private final int productionRate;

    ProductiveBuildingType(String name, int size, int hitPoint, int goldCost, Resource resourceCostType,
                           int resourceCostNumber, int workersNumber, boolean isActive,
                           ArrayList<model.people.Engineer> engineer, Resource producedResource,
                           Resource requiredResource, int productionRate) {
        this.name = name;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.resourceCostType = resourceCostType;
        this.resourceCostNumber = resourceCostNumber;
        this.workersNumber = workersNumber;
        this.isActive = isActive;
        Engineer = engineer;
        this.producedResource = producedResource;
        this.requiredResource = requiredResource;
        this.productionRate = productionRate;
    }

    public int getSize() {
        return size;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getGoldCost() {
        return goldCost;
    }

    public Resource getResourceCostType() {
        return resourceCostType;
    }

    public int getResourceCostNumber() {
        return resourceCostNumber;
    }

    public int getWorkersNumber() {
        return workersNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public ArrayList<model.people.Engineer> getEngineer() {
        return Engineer;
    }

    public Resource getProducedResource() {
        return producedResource;
    }

    public Resource getRequiredResource() {
        return requiredResource;
    }

    public int getProductionRate() {
        return productionRate;
    }
}
