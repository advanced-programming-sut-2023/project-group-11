package model.buildings;

public class DrawBridge extends Building {

    private boolean isOpen;

    public DrawBridge(boolean isOpen) {
        this.isOpen = isOpen;
        size = 1;
        name = "draw bridge";
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
