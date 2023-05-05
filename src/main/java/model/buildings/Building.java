package model.buildings;
import Enum.*;
import model.Block;
import model.Reign;

import java.util.ArrayList;

public class Building {
    public BuildingType buildingType;
    private Block block;
    private Reign owner;
    private int hp;
    public static ArrayList<Building> allBuildings = new ArrayList<>();

    public Building(BuildingType buildingType, Reign owner, Block block) {
        this.buildingType = buildingType;
        this.block = block;
        this.owner = owner;
        this.hp = buildingType.hp;
    }

    public void nextTurn() {

    }
    public void getDamaged(int amountOfDamage){}
    public Reign getOwner() {
        return owner;
    }
    public boolean isOwnerCorrect(Reign reign) {
        if (this.owner.equals(reign)) return true;
        return false;
    }

    public Block getBlock() {
        return block;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
