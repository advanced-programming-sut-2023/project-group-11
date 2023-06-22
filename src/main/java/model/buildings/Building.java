package model.buildings;

import javafx.scene.image.Image;
import model.AllResource;
import model.Governance;
import model.map.Map;

public abstract class Building {
    protected int xCoordinate, yCoordinate;
    protected Governance owner;
    protected int size;
    protected int hitPoint;
    protected int maxHitPoint;
    protected double goldCost;
    protected AllResource resourceCostType;
    protected int resourceCostNumber;
    protected int workersNumber;
    protected boolean isActive;
    protected boolean isFiring = false;
    protected int firingLeft = 0;
    protected boolean isSick = false;
    protected String name;
    protected transient Image image;

    public void setOwner(Governance owner) {
        this.owner = owner;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public int getMaxHitPoint() {
        return maxHitPoint;
    }

    public Governance getOwner() {
        return owner;
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

    public String getName() {
        return name;
    }

    public void repair() {
        hitPoint = maxHitPoint;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public boolean isFiring() {
        return isFiring;
    }

    public void setFiring(boolean firing) {
        isFiring = firing;
    }

    public int getFiringLeft() {
        return firingLeft;
    }

    public void setFiringLeft(int firingLeft) {
        this.firingLeft = firingLeft;
    }

    public boolean isSick() {
        return isSick;
    }

    public void setSick(boolean sick) {
        isSick = sick;
    }

    public Image getImage() {
        if (image == null)
            image = new Image(System.getProperty("user.dir") + "/src/main/resources/IMG/Building/" +
                    getClass().getSimpleName() + "/" + name + ".png");
        return image;
    }

    public void removeFromGame(Map map) {
        if (this.name.equals("hovel")) owner.changeMaxPopulation(-8);
        if (this instanceof Church) owner.changeReligiousFactor(-2);

        owner.getBuildings().remove(this);
        owner.setUnemployedPopulation(owner.getUnemployedPopulation() + workersNumber);

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                map.getTile(xCoordinate + i, yCoordinate + j).setBuilding(null);
    }

    @Override
    public String toString() {
        String result = "Building Type: " + name + " HP: " + hitPoint + " Owner: " + owner.getOwner().getNickname();
        if (this.isFiring) result += " Firing left: " + firingLeft;
        return result;
    }

    public String getPublicDetails(){
        String output = name + "\n";
        if((int)goldCost > 0)
            output += (int)goldCost + " gold\n";
        if(resourceCostType != null && !resourceCostType.equals(AllResource.NONE)){
            output += resourceCostNumber + " " + resourceCostType.getName() + "\n";
        }
        return output;
    }

    @Override
    public boolean equals(Object obj) {
        return name == ((Building) obj).getName();
    }
}
