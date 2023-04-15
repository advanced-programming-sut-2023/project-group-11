package Model.Resources;


public enum Utils {
    BOW("bow"),
    CROSSBOW("crossbow"),
    SPEAR("spear"),
    PIKE("pike"),
    MACE("mace"),
    SWORD("sword"),
    LEATHER_ARMOR("leather"),
    METAL_ARMOR("metal"),
    ;
    private final String name;

    Utils(String name) {
        this.name = name;
    }

    public Utils getUtilsByName(String name) {
        for (Utils util : Utils.values())
            if (util.name.equals(name)) return util;
        return null;
    }
}
