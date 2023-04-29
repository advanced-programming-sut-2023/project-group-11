package model.buildings.enums;

import model.resources.AllResource;


public enum TrapType {
    KILLING_PIT("killing pit",1,0,0, AllResource.WOOD,6,
            0,100,true),
    PITCH_DITCH("pitch ditch",1,0,0,AllResource.PITCH,2,
            0,100,false),
    CAGED_WARDOGS("caged wardogs",3,0,100,AllResource.WOOD,10,
            0,100,false);

    private String name;
    private int size;
    private int hitPoint;
    private int goldCost;
    private AllResource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private final int damage;
    private boolean isActive;

    public int getDamage() {
        return damage;
    }

    public boolean isActive() {
        return isActive;
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


    TrapType(String name, int size, int hitPoint, int goldCost, AllResource resourceCostType, int resourceCostNumber,
             int workersNumber, int damage, boolean isActive) {
        this.name = name;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.resourceCostType = resourceCostType;
        this.resourceCostNumber = resourceCostNumber;
        this.workersNumber = workersNumber;
        this.damage = damage;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public static TrapType getTrapTypeByName(String name){
        for (TrapType trapType : TrapType.values())
            if(trapType.name.equals(name))
                return trapType;
        return null;
    }

}
