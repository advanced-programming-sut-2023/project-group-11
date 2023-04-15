package Model.Buildings;

import Model.Resources.AllResource;

public class ProductiveBuilding extends Building {

    private AllResource producedResource;
    private AllResource requiredResource;
    private int productionRate;

    public AllResource getProducedResource() {
        return producedResource;
    }

    public AllResource getRequiredResource() {
        return requiredResource;
    }

    public int getProductionRate() {
        return productionRate;
    }

    public void oneTurnPass(){

    }

}
