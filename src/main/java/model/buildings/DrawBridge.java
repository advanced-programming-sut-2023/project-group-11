package model.buildings;

public class DrawBridge extends DefensiveBuilding {

    private boolean isOpen;

    public DrawBridge(boolean isOpen) {
        this.isOpen = isOpen;
        name = "draw bridge";
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void defend() {

    }

}
