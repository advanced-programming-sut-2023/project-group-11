package model.people;

import model.Governance;
import model.people.enums.Hp;
import model.people.enums.Speed;
import model.people.enums.TroopDamage;

//TODO:1 add lord to troop and handle it be in drop unit & not be in unit maker
public class Lord extends Attacker {
    public Lord(Governance owner) {
        this.hp = Hp.LORD_HP.getHp();
        this.speed = Speed.MEDIUM;
        this.leftMoves = speed.getMovesInEachTurn();
        this.cost = Double.POSITIVE_INFINITY;
        this.damage = TroopDamage.VERY_HIGH.getDamage();
        this.ownerGovernance = owner;
        this.range = 1;
        this.name = "lord";
    }
}
