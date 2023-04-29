package model.buildings;

import model.buildings.enums.UnitMakerType;
import model.resources.TroopEquipment;
import model.people.Units;

public class UnitMaker extends Building {


    private boolean isMercenaryMaker;
    private boolean isEngineerMaker;

    public UnitMaker(UnitMakerType unitMakerType) {
        name = unitMakerType.getName();
        size = unitMakerType.getSize();
        hitPoint = unitMakerType.getHitPoint();
        goldCost = unitMakerType.getGoldCost();
        resourceCostType = unitMakerType.getResourceCostType();
        resourceCostNumber = unitMakerType.getResourceCostNumber();
        workersNumber = unitMakerType.getWorkersNumber();
        isActive = unitMakerType.isActive();
        isMercenaryMaker = unitMakerType.isMercenaryMaker();
        isEngineerMaker = unitMakerType.isEngineerMaker();
    }

    public void makeUnit(String UnitName){
        //TODO: implementation
    }

}
