package model.people;

import model.Stronghold;
import model.people.enums.Hp;
import model.people.enums.Speed;

public class Engineer extends Units {
    private boolean inWork = false;
    private boolean hasOilPail = false;
    private boolean emptyPail = true;

    public Engineer() {
        this.name = "engineer";
        this.hp = Hp.VERY_LOW.getHp();
        this.cost = 30;
        this.speed = Speed.VERY_HIGH;
        this.ownerGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        this.ownerGovernance.addUnit(this);
    }

    public boolean isInWork() {
        return inWork;
    }

    public void setInWork(boolean inWork) {
        this.inWork = inWork;
    }

    public boolean hasOilPail() {
        return hasOilPail;
    }

    public void setHasOilPail(boolean hasOilPail) {
        this.hasOilPail = hasOilPail;
    }

    public boolean isEmptyPail() {
        return emptyPail;
    }

    public void setEmptyPail(boolean emptyPail) {
        this.emptyPail = emptyPail;
    }
}