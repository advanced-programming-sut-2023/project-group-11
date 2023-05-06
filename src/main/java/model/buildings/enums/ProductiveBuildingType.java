package model.buildings.enums;

import model.AllResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ProductiveBuildingType {
    QUARRY("quarry", 1, 114, 0, AllResource.WOOD, 20,
            3, true, new ArrayList<>(List.of(AllResource.STONE)),
            AllResource.NONE, 12, 0),
    IRON_MINE("iron mine", 2, 39, 0, AllResource.WOOD, 20,
            2, true, new ArrayList<>(List.of(AllResource.IRON)),
            AllResource.NONE, 4, 0),
    WOOD_CUTTER("wood cutter", 1, 39, 0, AllResource.WOOD, 3,
            1, true, new ArrayList<>(List.of(AllResource.WOOD)),
            AllResource.NONE, 20, 0),
    PITCH_RIG("pitch rig", 2, 39, 0, AllResource.WOOD, 20,
            1, true, new ArrayList<>(List.of(AllResource.PITCH)),
            AllResource.NONE, 5, 0),

    HUNTERS_POST("hunter's post", 1, 39, 0, AllResource.WOOD, 5,
            1, true, new ArrayList<>(List.of(AllResource.MEAT)),
            AllResource.NONE, 5, 0),
    DAIRY_FARM("dairy farm", 5, 39, 0, AllResource.WOOD, 10,
            1, true, new ArrayList<>(List.of(AllResource.CHEESE)),
            AllResource.NONE, 5, 0),
    APPLE_ORCHARD("apple orchard", 5, 39, 0, AllResource.WOOD, 5,
            1, true, new ArrayList<>(List.of(AllResource.APPLE)),
            AllResource.NONE, 5, 0),
    WHEAT_FARM("wheat farm", 4, 39, 0, AllResource.WOOD, 15,
            1, true, new ArrayList<>(List.of(AllResource.WHEAT)),
            AllResource.NONE, 5, 0),
    HOPS_FARM("hops farm", 4, 39, 0, AllResource.WOOD, 15,
            1, true, new ArrayList<>(List.of(AllResource.HOP)),
            AllResource.NONE, 5, 0),
    INN("inn", 2, 114, 100, AllResource.WOOD, 20,
            1, true, new ArrayList<>(List.of(AllResource.NONE)),
            AllResource.ALE, 1, 1),

    BAKERY("bakery", 2, 114, 0, AllResource.WOOD, 10,
            1, true, new ArrayList<>(List.of(AllResource.BREAD)),
            AllResource.FLOUR, 8, 2),
    BREWERY("brewery", 2, 114, 0, AllResource.WOOD, 10,
            1, true, new ArrayList<>(List.of(AllResource.ALE)),
            AllResource.HOP, 2, 2),
    MILL("mill", 1, 114, 0, AllResource.WOOD, 20,
            3, true, new ArrayList<>(List.of(AllResource.FLOUR)),
            AllResource.WHEAT, 3, 2),

    SPEAR_WORKSHOP("spear workshop", 2, 114, 100, AllResource.WOOD, 10,
            1, true, new ArrayList<>(Arrays.asList(AllResource.SPEAR, AllResource.PIKE)),
            AllResource.WOOD, 5, 5),
    BLACKSMITH_WORKSHOP("blacksmith workshop", 2, 114, 100, AllResource.WOOD, 20,
            1, true, new ArrayList<>(Arrays.asList(AllResource.SWORD, AllResource.MACE)),
            AllResource.IRON, 5, 5),
    BOW_WORKSHOP("bow workshop", 2, 114, 100, AllResource.WOOD, 20,
            1, true, new ArrayList<>(Arrays.asList(AllResource.BOW, AllResource.CROSSBOW)),
            AllResource.WOOD, 5, 5),
    ARMOR_WORKSHOP("spear workshop", 2, 114, 100, AllResource.WOOD, 20,
            1, true, new ArrayList<>(Arrays.asList(AllResource.LEATHER_ARMOR, AllResource.METAL_ARMOR)),
            AllResource.IRON, 5, 5),
    ;

    private final String name;
    private final int size;
    private final int hitPoint;
    private final int goldCost;
    private final AllResource resourceCostType;
    private final int resourceCostNumber;
    private final int workersNumber;
    private final boolean isActive;
    private final ArrayList<AllResource> producedResource;
    private final AllResource requiredResource;
    private final int productionRate;
    private final int consumptionRate;

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
