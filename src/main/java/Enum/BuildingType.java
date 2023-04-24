package Enum;

import Enum.*;
public enum BuildingType {
    BASE(null, 0 , 0, 0, 0),
    SMALL_STONE_GATE( null , 0, 0, 0 , 20),
    BIG_STONE_GATEHOUSE(Resource.STONE , 20 , 0 , 0 , 20),
    DRAW_BRIDGE(Resource.WOOD, 10 , 0 , 0 , 20),
    LOOKOUT_TOWER(Resource.STONE, 10, 0 , 0 , 20),
    PERIMETER_TOWER(Resource.STONE, 10 , 0 , 0 , 20),
    DEFENCE_TURRET(Resource.STONE , 15 , 0 , 0 , 20),
    SQUARE_TOWER(Resource.STONE, 35 , 0 , 0 , 20),
    CIRCLE_TOWER(Resource.STONE  , 40 , 0 , 0 , 20),
    ARMOURY(Resource.WOOD, 5, 0, 0, 20), // TO STORE
    ARMOURER(Resource.WOOD, 20 , 100 , 1, 20),
    //WORKSHOP(),
    BARRACK(Resource.STONE, 15 , 0 , 0 , 20),
    MERCENARY_CAMP(Resource.WOOD, 10 , 0 , 0 ,0),
    ENGINEER_GUILD(Resource.WOOD, 10, 100 , 0 , 20),
    KILLING_PIT(Resource.WOOD , 6 ,0, 0, 20),
    INN(Resource.WOOD , 20 , 100 , 1 , 0),
    MILL(Resource.WOOD, 20 , 0 , 3 , 0),
    IRON_MINE(Resource.WOOD, 20 , 0, 2, 0),
    SHOP(Resource.WOOD, 5, 0 , 1 , 0),
    OX_TETHER(Resource.WOOD, 5 , 0, 1 ,0),
    PITCH_RIG(Resource.WOOD, 20 , 0 , 1 , 0),
    STONE_MINE(Resource.WOOD, 20 , 0 ,3 , 0),
    STOCK_PILE(null , 0 , 0 , 0  ,0),
    WOOD_CUTTER(Resource.WOOD, 3 , 0 , 1 , 0),
    HOVEL(Resource.WOOD, 6 , 0 , 0 , 20),
    CHURCH(null , 0 , 250 , 0 , 20),
    CATHEDRAL(null , 0 , 1000 , 0 , 20),
    BLACK_SMITH(Resource.WOOD , 20 , 100 , 0 , 20),
    FLETCHER(Resource.WOOD, 20 , 100 ,0 , 0),
    POLE_TURNER(Resource.WOOD, 10 , 100, 0 , 0),
    OIL_SMELTER(Resource.IRON, 100 , 0, 100 , 0),
    CAGED_WAR_DOGS(Resource.WOOD, 10, 0, 100, 0),
    //SIEGE_TENT(),
    STABLE(Resource.WOOD, 20, 400, 0 , 0),
    APPLE_GARDEN(Resource.WOOD, 5, 0, 1, 0),
    DAIRY_FARM(Resource.WOOD, 10, 0, 1, 0 ),
    HOP_FARM(Resource.WOOD, 15, 0, 1, 0),
    HUNTING_GROUND(Resource.WOOD, 5, 0, 1, 0),
    WHEAT_FARM(Resource.WOOD, 15, 0, 1, 0),
    BAKERY(Resource.WOOD, 10, 0 , 1, 0),
    BREWERY(Resource.WOOD, 10 , 0, 1, 0),
    FOOD_STOCK_PILE(Resource.WOOD, 5, 0, 0, 0);

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

    BuildingType(Resource resourceToBuild, int resourceAmount, int goldCost, int workersNumber, int hp) {
        //Group = group;
        this.resourceToBuild = resourceToBuild;
        this.resourceAmount = resourceAmount;
        this.goldCost = goldCost;
        this.workersNumber = workersNumber;
        this.hp = hp;
    }

    public String getName() {
        String tmp = this.name().toLowerCase();
        return tmp.replaceAll("_", " ");
    }

    public BuildingType getBuildingTypeByName(String name) {
        for (BuildingType value : BuildingType.values()) {
            if(value.getName().equals(name)) return value;
        }
        return null;
    }

}
