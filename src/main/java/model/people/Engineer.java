package model.people;

import model.Governance;
import model.Stronghold;
import model.User;
import model.people.enums.Hp;
import model.people.enums.Speed;

public class Engineer extends Units {
    private boolean inWork = false;
    private boolean hasOilPail = false;
    private boolean emptyPail = true;

    public Engineer() {
        this.hp = Hp.VERY_LOW.getHp();
        this.cost = 30;
        this.speed = Speed.HIGH;
        this.ownerGovernance = Stronghold.getCurrentGovernance();
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