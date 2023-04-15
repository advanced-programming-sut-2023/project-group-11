package Model.Resources;

public enum Resource {
    HOP("hop"),
    ALE("ale"),
    WHEAT("wheat"),
    FLOUR("flour"),
    IRON("iron"),
    STONE("stone"),
    PITCH("pitch"),
    WOOD("wood"),
    ;
    private final String name;

    Resource(String name) {
        this.name = name;
    }

    public Resource getResourceByName(String name) {
        for (Resource resource : Resource.values())
            if (resource.name.equals(name)) return resource;
        return null;
    }
}
