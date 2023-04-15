package Model.Buildings;

public class Trap extends DefensiveBuilding {

    private int damage;
    private boolean isActive;

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setActive(boolean active) {
        isActive = active;
    }

    public int getDamage() {
        return damage;
    }

    public void defend(){

    }

}
