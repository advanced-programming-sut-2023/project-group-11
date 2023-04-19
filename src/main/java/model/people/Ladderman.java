package model.people;

import model.people.Enums.Hp;
import model.people.Enums.Speed;

public class Ladderman extends Units {
    public Ladderman() {
        this.hp = Hp.VERY_LOW.getHp();
        this.speed = Speed.HIGH;
    }
}