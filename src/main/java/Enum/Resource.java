package Enum;

public enum Resource {
    WOOD(20 , 18, null, BuildingType.WOOD_CUTTER, BuildingType.STOCK_PILE),
    STONE(20 , 20, null, BuildingType.STONE_MINE, BuildingType.STOCK_PILE),
    WHEAT(0, 0, null, BuildingType.WHEAT_FARM, null), // گندم
    APPLE(0, 0, null, BuildingType.APPLE_GARDEN, BuildingType.FOOD_STOCK_PILE),
    CHEESE(0, 0, null, BuildingType.DAIRY_FARM, BuildingType.FOOD_STOCK_PILE),
    MEAT(0, 0, null , null, BuildingType.FOOD_STOCK_PILE),
    PROCESSED_MEAT(0, 0, MEAT, BuildingType.HUNTING_GROUND, BuildingType.FOOD_STOCK_PILE ),
    IRON(10 , 20, null , BuildingType.HOP_FARM, BuildingType.STOCK_PILE), //
    HOP(0, 0, null , BuildingType.HOP_FARM, null), // جو
    BEAR(0, 0, HOP, BuildingType.BREWERY, BuildingType.FOOD_STOCK_PILE), //
    FLOUR(0, 0, WHEAT, BuildingType.MILL, null), // wheat
    BREAD(0, 0, FLOUR, BuildingType.BAKERY, BuildingType.FOOD_STOCK_PILE), //
    HORSE(0, 0, null, BuildingType.STABLE, null), //
    TAR(0, 0, null, BuildingType.PITCH_RIG, null), //قیر
    BOW(100,50, WOOD, BuildingType.FLETCHER, BuildingType.ARMOURY), //wood
    SPEAR(0, 0, WOOD, BuildingType.POLE_TURNER, BuildingType.ARMOURER), //نیزه// wood
    ARMOR(0, 0, IRON, BuildingType.ARMOURER, BuildingType.ARMOURY), //iron
    SWORD(0, 0, IRON, BuildingType.BLACK_SMITH, BuildingType.ARMOURY); //iron



    public int buyPrice;
    public int sellPrice;
    public final Resource producedFrom;
    public final BuildingType producedIn;
    public final BuildingType storedInBuilding;

    Resource(int buyPrice, int sellPrice, Resource producedFrom, BuildingType producedIn, BuildingType storedInBuilding) {
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.producedFrom = producedFrom;
        this.producedIn = producedIn;
        this.storedInBuilding = storedInBuilding;
    }

    public BuildingType getStoredInBuilding() {
        return storedInBuilding;
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
