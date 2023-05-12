package Enum;

import model.people.LadderMen;

public enum UnitType {
    ARCHER( BuildingType.SMALL_STONE_GATE , Resource.BOW,null, 5, 10 , 15 ,3 , 8 ),
    LADDERMAN(BuildingType.ENGINEER_GUILD,null,null,0,2,5,3,1),
    CROSSBOWMAN(BuildingType.BARRACK,Resource.BOW,null,10,20,12,2,12),
    SPEARMAN(BuildingType.BARRACK,Resource.SPEAR,null,8,10,20,3,1),
    PIKEMAN(BuildingType.BARRACK,Resource.SPEAR,Resource.ARMOR,15,20,25,1,1),
    SWORDMAN(BuildingType.BARRACK,Resource.SWORD,Resource.ARMOR,15,25,20,2,1),
    KNIGHT(BuildingType.BARRACK,Resource.SWORD,Resource.HORSE,20,20,30,5,1),
    BLACKMONK(BuildingType.CATHEDRAL,null,null,20,15,20,2,1),
    ARABIANBOWS(BuildingType.MERCENARY_CAMP,null,null,10,10,15,3,5),
    SLAVE(BuildingType.MERCENARY_CAMP,null,null,2,5,10,4,1),
    SLINGER(BuildingType.MERCENARY_CAMP,null,null,12,15,15,2,4),
    ASSASIN(BuildingType.MERCENARY_CAMP,null,null,30,15,15,4,1),
    HORSEARCHER(BuildingType.MERCENARY_CAMP,null,null,20,15,25,5,6),
    ARABIANSWORDMAN(BuildingType.MERCENARY_CAMP,null,null,20,20,15,3,1),
    FIRETHROWER(BuildingType.MERCENARY_CAMP,null,null,20,25,10,3,5),
    ENGINEER(BuildingType.ENGINEER_GUILD,null,null,20,0,20,3,1),
    TUNNELER(BuildingType.ENGINEER_GUILD,null,null,15,0,15,3,1),
    DOG(null,null,null,0,10,18,5,1);

    //public final String name;

    public final BuildingType buildingProducedIn;
    public final Resource resourceToBuild;
    public final Resource secondResource;
    public final int cost;
    private int attackPower; // variable?

    public int getDefencePower() {
        return defencePower;
    }
    private int defencePower; // variable?
    public final int speed; // variable?
    public int id; // ????
    public final int range;

    UnitType(BuildingType producedIn, Resource resourceToBuild,Resource secondResource, int cost,
             int attackPower, int defencePower, int speed, int range) {
        //this.name = name;
        this.buildingProducedIn = producedIn;
        this.resourceToBuild = resourceToBuild;
        this.secondResource = secondResource;
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


    public int getAttackPower() {
        return attackPower;
    }
    public int getRange(){
        return range;
    }
}
