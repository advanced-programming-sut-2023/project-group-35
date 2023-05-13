package Enum;

import model.Reign;
import model.people.MilitaryUnit;

public enum StructuresType {
    STAIRS(0,999,false,0,30,2,Resource.STONE),
    LADDER(0,999,false,0,10,1,Resource.WOOD),
    MOVING_SHIELD(0,150,true,0,5,1,Resource.WOOD),
    WALL_BREAKER(500,300,true,1,100,3,Resource.WOOD),
    SIEGE_TOWER(0,250,true,0,50,2,Resource.WOOD),
    CATAPULT(200,200,true,5,150,2,Resource.WOOD),
    TREBUCHET(350,100,false,7,150,2,Resource.WOOD),
    FLAME_THROWER(300,200,false,4,150,2,Resource.WOOD);
    private int damage;
    private int hp;
    private boolean isMoving;
    private int range;
    private int amountOfMaterial;
    private int amountOfEngineer;
    private Resource resource;

    StructuresType(int damage, int hp, boolean isMoving, int range, int amountOfMaterial,
                   int amountOfEngineer, Resource resource) {

    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getAmountOfMaterial() {
        return amountOfMaterial;
    }

    public void setAmountOfMaterial(int amountOfMaterial) {
        this.amountOfMaterial = amountOfMaterial;
    }

    public int getAmountOfEngineer() {
        return amountOfEngineer;
    }

    public void setAmountOfEngineer(int amountOfEngineer) {
        this.amountOfEngineer = amountOfEngineer;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
