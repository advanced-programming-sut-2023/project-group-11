package model.buildings;

public abstract class Climbable extends Building {
    protected boolean isClimbable;

    public boolean isClimbable() {
        return isClimbable;
    }

    public void setClimbable(boolean climbable) {
        isClimbable = climbable;
    }
}
