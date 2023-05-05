package Enum;

public enum Resource {
    WOOD(20 , 18, null, BuildingType.WOOD_CUTTER),
//    GOLD(),
    STONE(20 , 20, null, BuildingType.STONE_MINE),
    WHEAT(0, 0, null, BuildingType.WHEAT_FARM),
    APPLE(0, 0, null, BuildingType.APPLE_GARDEN),
    CHEESE(0, 0, null, BuildingType.DAIRY_FARM),
    MEAT(0, 0, null , null),
    PROCESSED_MEAT(0, 0, MEAT, BuildingType.HUNTING_GROUND ),
    IRON(10 , 20, null , BuildingType.HOP_FARM), //
    HOP(0, 0, null , BuildingType.HOP_FARM), //
    BEAR(0, 0, HOP, BuildingType.BREWERY), //
    FLOUR(0, 0, WHEAT, BuildingType.MILL), // wheat
    BREAD(0, 0, FLOUR, BuildingType.BAKERY), //
    HORSE(0, 0, null, BuildingType.STABLE), //
    TAR(0, 0, null, BuildingType.PITCH_RIG), //قیر
    BOW(100,50, WOOD, BuildingType.FLETCHER), //wood
    SPEAR(0, 0, WOOD, BuildingType.POLE_TURNER), //نیزه// wood
    ARMOR(0, 0, IRON, BuildingType.ARMOURER), //iron
    SWORD(0, 0, IRON, BuildingType.BLACK_SMITH); //iron



    public int buyPrice;
    public int sellPrice;
    public final Resource producedFrom;
    public final BuildingType producedIn;

    Resource(int buyPrice, int sellPrice, Resource producedFrom, BuildingType producedIn) {
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.producedFrom = producedFrom;
        this.producedIn = producedIn;
    }

    public static Resource getResourceByName(String name) {
        for (Resource value : Resource.values()) {
            if(value.name().toLowerCase().equals(name)) return value;
        }
        return null;
    }
    public static Resource getEntry(BuildingType type) {
        for (Resource value : Resource.values()) {
            if(value.producedIn.equals(type)) return value.producedFrom;
        }
        return null;
    }
    public static Resource getProduct(BuildingType buildingType) {
        for (Resource value : Resource.values()) {
            if (value.producedIn.equals(buildingType)) return value;
        }
        return null;
    }
}
