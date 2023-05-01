package model.buildings.enums;

import model.resources.AllResource;

import java.util.ArrayList;
import java.util.Arrays;

public enum ProductiveBuildingType {
    QUARRY("quarry", 6, 114, 0, AllResource.WOOD, 20,
            3, true, new ArrayList<>(Arrays.asList(AllResource.STONE)), null, 10, 0),
    IRON_MINE("iron mine", 4, 39, 0, AllResource.WOOD, 20,
            2, true, new ArrayList<>(Arrays.asList(AllResource.IRON)), null, 10, 0),
    WOOD_CUTTER("wood cutter", 3, 39, 0, AllResource.WOOD, 3,
            1, true, new ArrayList<>(Arrays.asList(AllResource.WOOD)), null, 10, 0),
    PITCH_RIG("pitch rig", 4, 39, 0, AllResource.WOOD, 20,
            1, true, new ArrayList<>(Arrays.asList(AllResource.PITCH)), null, 10, 0),

    HUNTERS_POST("hunter's post", 3, 39, 0, AllResource.WOOD, 5,
            1, true, new ArrayList<>(Arrays.asList(AllResource.MEAT)), null, 10, 0),
    DAIRY_FARM("dairy farm", 10, 39, 0, AllResource.WOOD, 10,
            1, true, new ArrayList<>(Arrays.asList(AllResource.CHEESE)), null, 10, 0),
    APPLE_ORCHARD("apple orchard", 11, 39, 0, AllResource.WOOD, 5,
            1, true, new ArrayList<>(Arrays.asList(AllResource.APPLE)), null, 10, 0),
    WHEAT_FARM("wheat farm", 9, 39, 0, AllResource.WOOD, 15,
            1, true, new ArrayList<>(Arrays.asList(AllResource.WHEAT)), null, 10, 0),
    HOPS_FARM("hops farm", 9, 39, 0, AllResource.WOOD, 15,
            1, true, new ArrayList<>(Arrays.asList(AllResource.HOP)), null, 10, 0),

    BAKERY("bakery", 4, 114, 0, AllResource.WOOD, 10,
            1, true, new ArrayList<>(Arrays.asList(AllResource.BREAD)), null, 10, 0),
    BREWERY("brewery", 4, 114, 0, AllResource.WOOD, 10,
            1, true, new ArrayList<>(Arrays.asList(AllResource.ALE)), null, 10, 0),
    MILL("mill", 3, 114, 0, AllResource.WOOD, 20,
            3, true, new ArrayList<>(Arrays.asList(AllResource.FLOUR)), null, 10, 0),

    SPEAR_WORKSHOP("spear workshop", 4, 114, 100, AllResource.WOOD, 10,
            1, true, new ArrayList<>(Arrays.asList(AllResource.SPEAR, AllResource.PIKE)),
            AllResource.WOOD, 10, 10),
    BlACKSMITH_WORKSHOP("blacksmith workshop", 4, 114, 100, AllResource.WOOD, 20,
            1, true, new ArrayList<>(Arrays.asList(AllResource.SWORD, AllResource.MACE)),
            AllResource.IRON, 10, 10),
    BOW_WORKSHOP("bow workshop", 4, 114, 100, AllResource.WOOD, 20,
            1, true, new ArrayList<>(Arrays.asList(AllResource.BOW, AllResource.CROSSBOW)),
            AllResource.WOOD, 10, 10),
    ARMOR_WORKSHOP("spear workshop", 4, 114, 100, AllResource.WOOD, 20,
            1, true, new ArrayList<>(Arrays.asList(AllResource.LEATHER_ARMOR, AllResource.METAL_ARMOR)),
            AllResource.IRON, 10, 10),

    ;

    private String name;
    private int size;
    private int hitPoint;
    private int goldCost;
    private AllResource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private boolean isActive;
    private final ArrayList<AllResource> producedResource;
    private AllResource requiredResource;
    private int productionRate, consumptionRate;


    ProductiveBuildingType(String name, int size, int hitPoint, int goldCost, AllResource resourceCostType,
                           int resourceCostNumber, int workersNumber, boolean isActive, ArrayList<AllResource> producedResource,
                           AllResource requiredResource, int productionRate, int consumptionRate) {
        this.name = name;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.resourceCostType = resourceCostType;
        this.resourceCostNumber = resourceCostNumber;
        this.workersNumber = workersNumber;
        this.isActive = isActive;
        this.producedResource = producedResource;
        this.requiredResource = requiredResource;
        this.productionRate = productionRate;
        this.consumptionRate = consumptionRate;
    }

    public String getName() {
        return name;
    }

    public int getConsumptionRate() {
        return consumptionRate;
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

    public AllResource getResourceCostType() {
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

    public ArrayList<AllResource> getProducedResource() {
        return producedResource;
    }

    public AllResource getRequiredResource() {
        return requiredResource;
    }

    public int getProductionRate() {
        return productionRate;
    }

    public static ProductiveBuildingType getProductiveBuildingTypeByName(String name) {
        for (ProductiveBuildingType productiveBuildingType : ProductiveBuildingType.values())
            if (productiveBuildingType.name.equals(name))
                return productiveBuildingType;
        return null;
    }
}
