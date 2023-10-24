package model.buildings;

import model.buildings.enums.ChurchType;

public class Church extends Building {

    private final boolean isGeneral;

    public Church(ChurchType churchType) {
        name = churchType.getName();
        size = churchType.getSize();
        hitPoint = churchType.getHitPoint();
        maxHitPoint = churchType.getHitPoint();
        goldCost = churchType.getGoldCost();
        resourceCostType = churchType.getResourceCostType();
        resourceCostNumber = churchType.getResourceCostNumber();
        isActive = churchType.isActive();
        isGeneral = churchType.isGeneral();
    }

//    @Override
//    public void removeFromGame() {
////        super.removeFromGame();
//        owner.changeReligiousFactor(-2);
//    }

    public boolean isGeneral() {
        return isGeneral;
    }
}
