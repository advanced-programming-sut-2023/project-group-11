package model.buildings;

import model.AllResource;
import model.buildings.enums.ProductiveBuildingType;

import java.util.ArrayList;

public class ProductiveBuilding extends Building {
    private final ArrayList<AllResource> producedResource;
    private final AllResource requiredResource;
    private final int productionRate;
    private final int consumptionRate;

    public ProductiveBuilding(ProductiveBuildingType productiveBuildingType) {
        name = productiveBuildingType.getName();
        size = productiveBuildingType.getSize();
        hitPoint = productiveBuildingType.getHitPoint();
        maxHitPoint = productiveBuildingType.getHitPoint();
        goldCost = productiveBuildingType.getGoldCost();
        resourceCostType = productiveBuildingType.getResourceCostType();
        resourceCostNumber = productiveBuildingType.getResourceCostNumber();
        workersNumber = productiveBuildingType.getWorkersNumber();
        isActive = productiveBuildingType.isActive();
        producedResource = productiveBuildingType.getProducedResource();
        requiredResource = productiveBuildingType.getRequiredResource();
        productionRate = productiveBuildingType.getProductionRate();
        consumptionRate = productiveBuildingType.getConsumptionRate();
    }

    public ArrayList<AllResource> getProducedResource() {
        return producedResource;
    }

    public AllResource getRequiredResource() {
        return requiredResource;
    }

    public int getProductionRate() {
        return productionRate;
    }

    public int getConsumptionRate() {
        return consumptionRate;
    }
}
