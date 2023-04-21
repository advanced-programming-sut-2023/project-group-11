package model.resources;


public enum TroopEquipment {
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

    TroopEquipment(String name) {
        this.name = name;
    }

    public TroopEquipment getUtilsByName(String name) {
        for (TroopEquipment util : TroopEquipment.values())
            if (util.name.equals(name)) return util;
        return null;
    }
}
