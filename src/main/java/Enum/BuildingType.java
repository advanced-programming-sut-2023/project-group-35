package Enum;

public enum BuildingType {
    BASE(null, 0 , 0, 0, 0, 0, false), // مقر
    SMALL_STONE_GATE( null , 0, 0, 0 , 20, 0, false), // changble passsing
    BIG_STONE_GATEHOUSE(Resource.STONE , 20 , 0 , 0 , 20, 0, false), // changable passing
    MOVING_BRIDGE(Resource.WOOD, 10 , 0 , 0 , 20, 0, true), // پل متحرک
    LOOKOUT_TOWER(Resource.STONE, 10, 0 , 0 , 20, 0, false),
    PERIMETER_TOWER(Resource.STONE, 10 , 0 , 0 , 20, 0, false),
    DEFENCE_TURRET(Resource.STONE , 15 , 0 , 0 , 20, 0, false),
    SQUARE_TOWER(Resource.STONE, 35 , 0 , 0 , 20, 0, false),
    CIRCLE_TOWER(Resource.STONE  , 40 , 0 , 0 , 20, 0, false),
    ARMOURY(Resource.WOOD, 5, 0, 0, 20, 0, false), // TO STORE
    ARMOURER(Resource.WOOD, 20 , 100 , 1, 20, 0, false),
    //WORKSHOP(),
    BARRACK(Resource.STONE, 15 , 0 , 0 , 20, 0, false), //سربازخانه
    MERCENARY_CAMP(Resource.WOOD, 10 , 0 , 0 ,0, 0, false), // سرباز خانه مزدوران
    ENGINEER_GUILD(Resource.WOOD, 10, 100 , 0 , 20, 0, false),
    KILLING_PIT(Resource.WOOD , 6 ,0, 0, 20, 0, true), // passable or unpassable
    INN(Resource.WOOD , 20 , 100 , 1 , 0, 0, false),
    MILL(Resource.WOOD, 20 , 0 , 3 , 0, 0, false),
    IRON_MINE(Resource.WOOD, 20 , 0, 2, 0, 0, true),
    SHOP(Resource.WOOD, 5, 0 , 1 , 0, 0, false),
    OX_TETHER(Resource.WOOD, 5 , 0, 1 ,0, 0, false),
    PITCH_RIG(Resource.WOOD, 20 , 0 , 1 , 0, 0, false),
    STONE_MINE(Resource.WOOD, 20 , 0 ,3 , 0, 0, true),
    WOOD_CUTTER(Resource.WOOD, 3 , 0 , 1 , 0, 0, false),
    HOVEL(Resource.WOOD, 6 , 0 , 0 , 20, 0, false),
    CHURCH(null , 0 , 250 , 0 , 20, 0, false),
    CATHEDRAL(null , 0 , 1000 , 0 , 20, 0, false),
    BLACK_SMITH(Resource.WOOD , 20 , 100 , 0 , 20, 0, false),
    FLETCHER(Resource.WOOD, 20 , 100 ,0 , 0, 0, false),
    POLE_TURNER(Resource.WOOD, 10 , 100, 0 , 0, 0, false), // نیزه سازی
    OIL_SMELTER(Resource.IRON, 100 , 0, 100 , 0, 0, false),
    CAGED_WAR_DOGS(Resource.WOOD, 10, 0, 100, 0, 0, false),
    //SIEGE_TENT(),
    STABLE(Resource.WOOD, 20, 400, 0 , 0, 0, true),
    APPLE_GARDEN(Resource.WOOD, 5, 0, 1, 0, 0, true),
    DAIRY_FARM(Resource.WOOD, 10, 0, 1, 0 , 0, true),
    HOP_FARM(Resource.WOOD, 15, 0, 1, 0, 0, true),
    HUNTING_GROUND(Resource.WOOD, 5, 0, 1, 0, 0, true), // what to do??
    WHEAT_FARM(Resource.WOOD, 15, 0, 1, 0, 0, true),
    BAKERY(Resource.WOOD, 10, 0 , 1, 0, 0, false),
    BREWERY(Resource.WOOD, 10 , 0, 1, 0, 0, false),
    STOCK_PILE(null , 0 , 0 , 0  ,0, 0, false),
    FOOD_STOCK_PILE(Resource.WOOD, 5, 0, 0, 0, 0, false);

//    TUNNEL(),
//
//    QUARRY(),
//    PATCH_RIG(),
//    DITCH(),
//    MERCENARY_POST(),




    //public final String name;
    //public final String Group;
    public final Resource resourceToBuild;
    public final int resourceAmount;
    public final int goldCost;
    public final int workersNumber;

    public final int hp;
    private final int rateOrCapacity;
    public final boolean isPassableForTroop;

    BuildingType(Resource resourceToBuild, int resourceAmount, int goldCost, int workersNumber, int hp, int rateOrCapacity, boolean isPassableForTroop) {
        //Group = group;
        this.resourceToBuild = resourceToBuild;
        this.resourceAmount = resourceAmount;
        this.goldCost = goldCost;
        this.workersNumber = workersNumber;
        this.hp = hp;
        this.rateOrCapacity = rateOrCapacity;
        this.isPassableForTroop = isPassableForTroop;
    }

    public String getName() {
        String tmp = this.name().toLowerCase();
        return tmp.replaceAll("_", " ");
    }

    public static BuildingType getBuildingTypeByName(String name) {
        for (BuildingType value : BuildingType.values()) {
            if(value.getName().equals(name.toLowerCase().trim())) return value;
        }
        return null;
    }
    public boolean canKeepStructure() {
        return this.checkForEquals(SQUARE_TOWER, CIRCLE_TOWER);
    }
    public boolean checkForEquals(BuildingType... types) {
        for (BuildingType type : types) {
            if(type.equals(this)) return true;
        }
        return false;
    }

    public int getRelatedInteger() {
        return this.rateOrCapacity;
    }


}
