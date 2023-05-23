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

    public Building(BuildingType buildingType, Reign owner, Block block) {
        this.buildingType = buildingType;
        this.block = block;
        this.owner = owner;
        this.hp = buildingType.hp;
        owner.getBuildings().add(this);
        block.setBuilding(this);
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void nextTurn() {

    }
    public void iFDestroyed() {
        // to override
    }
    public void destroy() {
        iFDestroyed();
        this.getBlock().removeBuilding();
        this.getOwner().getBuildings().remove(this);

    }
    public void getDamaged(int amountOfDamage){
        hp -= amountOfDamage;
        if(hp <= 0) destroy();
    }
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
