package model.buildings;

import model.buildings.enums.ProductiveBuildingType;
import model.resources.AllResource;

import java.util.ArrayList;

public class ProductiveBuilding extends Building {

    private ArrayList<AllResource> producedResource;
    private AllResource requiredResource;
    private int productionRate,consumptionRate;

    public ProductiveBuilding(ProductiveBuildingType productiveBuildingType) {
        name = productiveBuildingType.getName();
        size = productiveBuildingType.getSize();
        hitPoint = productiveBuildingType.getHitPoint();
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

    public void produce(){
        //TODO: implement
    }

    public void oneTurnPass(){

    }

}