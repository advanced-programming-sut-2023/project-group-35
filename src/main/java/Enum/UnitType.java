package Enum;

public enum UnitType {
    ARCHER( BuildingType.SMALL_STONE_GATE , Resource.BOW, 5, 10 , 10 ,5 , 10 );

    //public final String name;
    public final BuildingType buildingProducedIn;
    public final Resource resourceToBuild;
    public final int cost;
    private int attackPower; // variable?

    public int getDefencePower() {
        return defencePower;
    }
    private int defencePower; // variable?
    public final int speed; // variable?
    public int id; // ????
    public final int range;

    UnitType(BuildingType producedIn, Resource resourceToBuild, int cost, int attackPower, int defencePower, int speed, int range) {
        //this.name = name;
        this.buildingProducedIn = producedIn;
        this.resourceToBuild = resourceToBuild;
        this.cost = cost;
        this.attackPower = attackPower;
        this.defencePower = defencePower;
        this.speed = speed;
        this.range = range;
    }

    public static UnitType getUnitTypeByName(String name) {
        for (UnitType value : UnitType.values()) {
            if(value.name().toLowerCase().equals(name)) return value;
        }
        return null;
    }
    public void setDefencePower(int defencePower) {
        this.defencePower = defencePower;
    }


}
