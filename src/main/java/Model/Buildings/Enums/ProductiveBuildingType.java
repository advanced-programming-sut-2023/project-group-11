package Model.Buildings.Enums;

import Model.Resources.Resource;
import Model.people.Engineer;

import java.util.ArrayList;

public enum ProductiveBuildingType {
    ;

    private final Resource producedResource;
    private Resource requiredResource;
    private final int productionRate;
    private int size;
    private int hitPoint;
    private int goldCost;
    private Resource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private boolean isActive;
    public ArrayList<Model.people.Engineer> Engineer;

    ProductiveBuildingType(Resource producedResource, Resource requiredResource, int productionRate) {
        this.producedResource = producedResource;
        this.requiredResource = requiredResource;
        this.productionRate = productionRate;
    }

    ProductiveBuildingType(Resource producedResource, int productionRate) {
        this.producedResource = producedResource;
        this.productionRate = productionRate;
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
