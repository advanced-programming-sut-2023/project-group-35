package Enum;

public enum Resource {
    WOOD(20 , 18, null, BuildingType.WOOD_CUTTER, BuildingType.STOCK_PILE),
    STONE(20 , 18, null, BuildingType.STONE_MINE, BuildingType.STOCK_PILE),
    WHEAT(5, 3, null, BuildingType.WHEAT_FARM, BuildingType.FOOD_STOCK_PILE), // گندم
    APPLE(10, 8, null, BuildingType.APPLE_GARDEN, BuildingType.FOOD_STOCK_PILE),
    CHEESE(10, 8, null, BuildingType.DAIRY_FARM, BuildingType.FOOD_STOCK_PILE),
    MEAT(10, 8, null , null, BuildingType.FOOD_STOCK_PILE),
    PROCESSED_MEAT(10, 9, MEAT, BuildingType.HUNTING_GROUND, BuildingType.FOOD_STOCK_PILE ),
    IRON(30, 20, null , BuildingType.IRON_MINE, BuildingType.STOCK_PILE), //
    HOP(10, 2, null , BuildingType.HOP_FARM, BuildingType.FOOD_STOCK_PILE), // جو
    BEAR(15, 5, HOP, BuildingType.BREWERY, BuildingType.FOOD_STOCK_PILE), //
    FLOUR(7, 5, WHEAT, BuildingType.MILL, BuildingType.FOOD_STOCK_PILE), // wheat
    BREAD(20, 15, FLOUR, BuildingType.BAKERY, BuildingType.FOOD_STOCK_PILE), //
    HORSE(70, 50, null, BuildingType.STABLE, null), //
    TAR(50, 30, null, BuildingType.PITCH_RIG, BuildingType.STOCK_PILE), //قیر
    BOW(100,50, WOOD, BuildingType.FLETCHER, BuildingType.ARMOURY), //wood
    SPEAR(100, 50, WOOD, BuildingType.POLE_TURNER, BuildingType.ARMOURER), //نیزه// wood
    ARMOR(100, 50, IRON, BuildingType.ARMOURER, BuildingType.ARMOURY), //iron
    SWORD(100, 50, IRON, BuildingType.BLACK_SMITH, BuildingType.ARMOURY);




    public int buyPrice;
    public int sellPrice;
    public  Resource producedFrom;
    private  BuildingType producedIn;
    public  BuildingType storedInBuilding;

    Resource(int buyPrice, int sellPrice, Resource producedFrom, BuildingType producedIn, BuildingType storedInBuilding) {
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.producedFrom = producedFrom;
        this.producedIn = producedIn;
        this.storedInBuilding = storedInBuilding;
    }

    public BuildingType getStoredInBuilding() {
        if(this.equals(SWORD)) System.out.println("stored in building: " + this.storedInBuilding);
        return this.storedInBuilding;
    }

    public static Resource getResourceByName(String name) {
        for (Resource value : Resource.values()) {
            if(value.name().toLowerCase().equals(name)) return value;
        }
        return null;
    }
    public static Resource getEntry(BuildingType type) {
       /* for (Resource value : Resource.values()) {
            if(value.producedIn == null)
                continue;
            if(value.producedIn.equals(type)) return value.producedFrom;
        }*/
        switch (type){
            case BREWERY -> {
                return HOP;
            }
            case MILL -> {
                return FLOUR;
            }
            case BAKERY -> {
                return BREAD;
            }
            case FLETCHER, POLE_TURNER -> {
                return WOOD;
            }
            case ARMOURER , BLACK_SMITH->{
                return IRON;
            }

        }
        return null;
    }
    public static Resource getProduct(BuildingType buildingType) {
/*        for (Resource value : Resource.values()) {
            if(value.producedIn == null){
                System.out.println(value);
                continue;}
            if (value.producedIn.equals(buildingType)) return value;
        }
        return null;
    }*/
        switch (buildingType){
            case WOOD_CUTTER -> {
                return WOOD;
            }
            case STONE_MINE -> {
                return STONE;
            }
            case WHEAT_FARM -> {
                return WHEAT;
            }
            case APPLE_GARDEN -> {
                return APPLE;
            }
            case DAIRY_FARM -> {
                return CHEESE;
            }
            case HUNTING_GROUND -> {
                return PROCESSED_MEAT;
            }
            case HOP_FARM -> {
                return HOP;
            }
            case IRON_MINE -> {
                return IRON;
            }
            case BREWERY -> {
                return BEAR;
            }
            case MILL -> {
                return FLOUR;
            }
            case BAKERY -> {
                return BREAD;
            }
            case STABLE -> {
                return HORSE;
            }
            case FLETCHER -> {
                return BOW;
            }
            case PITCH_RIG -> {
                return TAR;
            }
            case POLE_TURNER -> {
                return SPEAR;
            }
            case BLACK_SMITH -> {
                return SWORD;
            }
            case ARMOURER -> {
                return ARMOR;
            }
        }
        return null;
    }
}
