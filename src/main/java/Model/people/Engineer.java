package Model.people;

public class Engineer extends Units {
    private boolean inWork = false;
    private boolean hasOilPail = false;
    private boolean emptyPail = true;

    public boolean isInWork() {
        return inWork;
    }

    public void setInWork(boolean inWork) {
        this.inWork = inWork;
    }

    public boolean hasOilPail() {
        return hasOilPail;
    }

    public void setHasOilPail(boolean hasOilPail) {
        this.hasOilPail = hasOilPail;
    }

    public boolean isEmptyPail() {
        return emptyPail;
    }

    public void setEmptyPail(boolean emptyPail) {
        this.emptyPail = emptyPail;
    }
}