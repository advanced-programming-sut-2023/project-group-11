package model.buildings.enums;

import model.resources.AllResource;

public enum ChurchType {

    CHAPEL("chapel",6,150,250,null,0,0,
            true,false,2,false,0),
    CATHEDRAL("cathedral",13,459,1000,null,0,0,
            true,false,2,true, 5);

    private String name;
    private int size;
    private int hitPoint;
    private double goldCost;
    private AllResource resourceCostType;
    private int resourceCostNumber;
    private int workersNumber;
    private boolean isActive;
    private boolean areWorkersEngineer;
    private int popularityEffect;
    private boolean isGeneral;
    private int makeMonkCost;


    ChurchType(String name,int size, int hitPoint, double goldCost,
               AllResource resourceCostType, int resourceCostNumber, int workersNumber,
               boolean isActive, boolean areWorkersEngineer,
               int popularityEffect, boolean isGeneral, int makeMonkCost) {
        this.name = name;
        this.size = size;
        this.hitPoint = hitPoint;
        this.goldCost = goldCost;
        this.resourceCostType = resourceCostType;
        this.resourceCostNumber = resourceCostNumber;
        this.workersNumber = workersNumber;
        this.isActive = isActive;
        this.areWorkersEngineer = areWorkersEngineer;
        this.popularityEffect = popularityEffect;
        this.isGeneral = isGeneral;
        this.makeMonkCost = makeMonkCost;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public double getGoldCost() {
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

    public boolean AreWorkersEngineer() {
        return areWorkersEngineer;
    }


    public int getPopularityEffect() {
        return popularityEffect;
    }

    public boolean isGeneral() {
        return isGeneral;
    }

    public int getMakeMonkCost() {
        return makeMonkCost;
    }

    public static ChurchType getChurchTypeByName(String name){
        for (ChurchType churchType : ChurchType.values())
            if(churchType.name.equals(name))
                return churchType;
        return null;
    }
}
