package Model.people;

import Model.people.Enums.Hp;
import Model.people.Enums.Speed;

public class Ladderman extends Units {
    public Ladderman() {
        this.hp = Hp.VERY_LOW.getHp();
        this.speed = Speed.HIGH;
    }
}