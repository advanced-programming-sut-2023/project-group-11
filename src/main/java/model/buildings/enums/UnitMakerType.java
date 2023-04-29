package model.buildings.enums;

import model.resources.AllResource;

public enum UnitMakerType {
    MERCENARY_TENT("mercenary tent",10,114,0, AllResource.WOOD,10,
            0,true,true,false),
    BARRACKS("barracks",10,189,0,AllResource.WOOD,15,
            0,true,false,false),
    ENGINEER_GUILD("engineer guild",10,189,100,AllResource.WOOD,10,
            0,true,false,true);


    private String name;
    private int size;
    private int hitPoint;
    private int goldCost;
    private AllResource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private boolean isActive;
    private boolean isMercenaryMaker;
    private boolean isEngineerMaker;

    UnitMakerType(String name, int size, int hitPoint, int goldCost, AllResource resourceCostType,
                  int resourceCostNumber, int workersNumber, boolean isActive,
                  boolean isMercenaryMaker, boolean isEngineerMaker) {
        this.name = name;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.resourceCostType = resourceCostType;
        this.resourceCostNumber = resourceCostNumber;
        this.workersNumber = workersNumber;
        this.isActive = isActive;
        this.isMercenaryMaker = isMercenaryMaker;
        this.isEngineerMaker = isEngineerMaker;
    }

    public boolean isMercenaryMaker() {
        return isMercenaryMaker;
    }

    public boolean isEngineerMaker() {
        return isEngineerMaker;
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

    public String getName() {
        return name;
    }

    public static UnitMakerType getUnitMakerTypeByName(String name){
        for (UnitMakerType unitMakerType : UnitMakerType.values())
            if(unitMakerType.name.equals(name))
                return unitMakerType;
        return null;
    }

}
