package Model.Buildings;

public class DrawBridge extends DefensiveBuilding{

    private boolean isOpen;

    public DrawBridge(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void defend(){

    }

}
