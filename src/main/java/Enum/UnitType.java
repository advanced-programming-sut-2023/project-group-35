package main.java.Enum;

public enum UnitType {
    ARCHER(5 , 5, 10 , 10 ,5 , Resources.BOW , BuildingType.building);

    private int cost;
    private final int attackPower;
    private int defencePower;
    private final int speed;
    private int id;
    private int range;
    private Resources resource;
    private BuildingType producedIn;


    UnitType(int cost, int attackPower, int defencePower, int speed , int range , Resources resource, BuildingType producedIn) {
        this.cost = cost;
        this.attackPower = attackPower;
        this.defencePower = defencePower;
        this.speed = speed;
        this.range = range;
        this.resource = resource;
        this.producedIn = producedIn;
    }
    public void setDefencePower(int defencePower) {
        this.defencePower = defencePower;
    }
    public void nextTurn(){return;}

}
