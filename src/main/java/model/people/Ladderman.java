package model.people;

import model.people.enums.Hp;
import model.people.enums.Speed;

public class Ladderman extends Units {
    public Ladderman() {
        this.hp = Hp.VERY_LOW.getHp();
        this.speed = Speed.HIGH;
    }
}