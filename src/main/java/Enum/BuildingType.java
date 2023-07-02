package Enum;

import javafx.scene.image.Image;

public enum BuildingType {
    BASE("strongHold.png", null, 0 , 0, 0, 1000, 0, false), // مقر
    SMALL_STONE_GATE("gateHouse/stoneGateHouse.png",  null , 0, 0, 0 , 200, 0, false), // changble passsing
    BIG_STONE_GATEHOUSE("gateHouse/bigStoneGateHouse.png", Resource.STONE , 20 , 0 , 0 , 500, 0, false), // changable passing
    MOVING_BRIDGE("", Resource.WOOD, 10 , 0 , 1 , 100, 0, true), // پل متحرک
    LOOKOUT_TOWER("towers/lookoutTower.png", Resource.STONE, 10, 0 , 0 , 500, 0, false),
    PERIMETER_TOWER("towers/1d.png", Resource.STONE, 15 , 0 , 0 , 550, 0, false),
    DEFENCE_TURRET("towers/lookoutTower.png", Resource.STONE , 20 , 0 , 0 , 600, 0, false),
    SQUARE_TOWER("towers/2a.png", Resource.STONE, 35 , 0 , 0 , 800, 0, false),
    CIRCLE_TOWER("towers/circleTower.png", Resource.STONE  , 40 , 0 , 0 , 800, 0, false),
    ARMOURY("townBuildings/weaponBuildings/1.png", Resource.WOOD, 5, 0, 0, 300, 150, true), // TO STORE
    ARMOURER("weaponBuildings/armourer.png", Resource.WOOD, 20 , 100 , 1, 120, 2, true),
    //WORKSHOP(),
    BARRACK("barrack.png", Resource.STONE, 15 , 0 , 0 , 250, 0, true), //سربازخانه
    MERCENARY_CAMP("Military Buildings/mercenaryCamp.png", Resource.WOOD, 10 , 0 , 0 ,150, 0, true), // سرباز خانه مزدوران
    ENGINEER_GUILD("Military Buildings/engineersGuild.png",Resource.WOOD, 10, 100 , 0 , 200, 0, true),
    KILLING_PIT("killingPit.png", Resource.WOOD , 6 ,0, 0, 999, 0, true), // passable or unpassable
    INN("townBuildings/inn.png", Resource.WOOD , 20 , 100 , 1 , 100, 2, true),
    MILL("industryBuildings/mill.png",Resource.WOOD, 20 , 0 , 3 , 120, 5, true),
    IRON_MINE("ironMine.png", Resource.WOOD, 20 , 0, 2, 200, 30, true),
    SHOP("market", Resource.WOOD, 5, 0 , 1 , 150, 0, true),
    OX_TETHER("oxTether.png", Resource.WOOD, 5 , 0, 1 ,0, 0, false),
    PITCH_RIG("pitchRig.png", Resource.WOOD, 20 , 0 , 1 , 145, 25, false),
    STONE_MINE("industryBuildings/stoneMine.png", Resource.WOOD, 20 , 0 ,3 , 100, 50, true),
    WOOD_CUTTER("woodCutter.png", Resource.WOOD, 3 , 0 , 1 , 0, 2, true),
    HOVEL("townBuildings/hovel.png", Resource.WOOD, 6 , 0 , 0 , 30, 8, true),
    CHURCH("townBuildings/church.png", null , 0 , 250 , 0 , 200, 2, true),
    CATHEDRAL("townBuildings/cathedral.png", null , 0 , 1000 , 1 , 400, 2, true),
    BLACK_SMITH("blackSmith.png", Resource.WOOD , 20 , 100 , 1 , 150, 2, true),
    FLETCHER("weaponBuildings/fletcher.png", Resource.WOOD, 20 , 100 ,1 , 200, 2, true),
    POLE_TURNER("", Resource.WOOD, 10 , 100, 1 , 150, 2, true), // نیزه سازی
    OIL_SMELTER("", Resource.IRON, 100 , 0, 100 , 0, 0, false),
    CAGED_WAR_DOGS("", Resource.WOOD, 10, 0, 100, 0, 0, false),
    //SIEGE_TENT(),
    STABLE("stable.png", Resource.WOOD, 20, 400, 2 , 120, 8, true),
    APPLE_GARDEN("farm.png", Resource.WOOD, 5, 0, 1, 80, 15, true),
    DAIRY_FARM("farm.png", Resource.WOOD, 10, 0, 1, 80 , 5, true),
    HOP_FARM("farm2.png", Resource.WOOD, 15, 0, 1, 80, 50, true),
    HUNTING_GROUND("huntingGround.png", Resource.WOOD, 5, 0, 1, 80, 15, true), // what to do??
    WHEAT_FARM("farm2.png", Resource.WOOD, 15, 0, 1, 80, 40, true),
    BAKERY("foodProcessing/bakery.png", Resource.WOOD, 10, 0 , 1, 80, 10, true),
    BREWERY("foodProcessing/brewery.png", Resource.WOOD, 10 , 0, 1, 150, 5, true),
    STOCK_PILE("industryBuildings/foodStockPile.png", null , 0 , 0 , 0  ,999, 50, true),
    FOOD_STOCK_PILE("industryBuildings/foodStockPile.png", Resource.WOOD, 5, 0, 0, 200, 50, true);
    //public final String name;
    //public final String Group;
    public final Resource resourceToBuild;
    public final int resourceAmount;
    public final int goldCost;
    public final int workersNumber;
    public final int hp;
    private final int rateOrCapacity;
    public final boolean isPassableForTroop;
    public String buildingUrl;

    BuildingType(String url, Resource resourceToBuild, int resourceAmount, int goldCost, int workersNumber, int hp, int rateOrCapacity, boolean isPassableForTroop) {
        //Group = group;
        this.resourceToBuild = resourceToBuild;
        this.resourceAmount = resourceAmount;
        this.goldCost = goldCost;
        this.workersNumber = workersNumber;
        this.hp = hp;
        this.rateOrCapacity = rateOrCapacity;
        this.isPassableForTroop = isPassableForTroop;
        this.buildingUrl = url;
    }
    public Image getImage() {
        Image image = new Image(BuildingType.class.getResource("/Images/buildings/" + this.getBuildingUrl()).toExternalForm(), 60, 60, false, true);
        return image;
    }
    public static BuildingType getBuildingByImage(Image image) {
        String url = image.getUrl();
        for (BuildingType value : BuildingType.values()) {
            if(url.contains(value.buildingUrl)) return value;
        }
        return null;
    }
    public  boolean isEqualToAnyOf(BuildingType... buildingTypes) {
        for (BuildingType buildingType : buildingTypes) {
            if(buildingType.equals(this)) return true;
        }
        return false;
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

    public String getBuildingUrl() {
        return buildingUrl;
    }
}
