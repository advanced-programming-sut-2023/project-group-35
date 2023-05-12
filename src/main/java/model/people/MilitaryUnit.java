package model.people;

import Enum.*;
import model.Block;
import model.Reign;

import java.util.ArrayList;

public class MilitaryUnit {

    public UnitType unitType;
    public int speed;
    private Reign owner;
    private Block block;
    private Block destBlock;
    private Block secondDestBlock;
    //private ArrayList<Block> dest = new ArrayList<>();
    private int number;
    private int hp;
    private UnitState unitState;
    private int range;
    private int damage;
    private boolean isMoving;
    private boolean isPatrolling;

    public UnitState getUnitState() {
        return unitState;
    }
    public void setUnitState(UnitState unitState) {
        this.unitState = unitState;
    }

    public MilitaryUnit(UnitType unitType, Reign owner, Block block, int number) {
        this.unitType = unitType;
        this.damage = unitType.getAttackPower();
        this.owner = owner;
        this.block = block;
        this.number = number;
        this.hp = unitType.getDefencePower();
        this.unitState = UnitState.STABLE;
        this.range = unitType.getRange();
        this.speed = unitType.speed;
    }

    public void moveTo(Block dest) {
        this.getBlock().removeUnit(this);
        this.block = dest;
        dest.getMilitaryUnits().add(this);
    }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
    public void getDamaged(int amountOfDamage){
        int casualties =  amountOfDamage/unitType.getDefencePower();
        if(casualties >= number){
            number = 0;
        }
        else{
            number = number - casualties;
        }
    }
    public Reign getOwner() {
        return owner;
    }

    public Block getBlock() {
        return block;
    }

    public int getRange() {
        return range;
    }
    public int getDamage(){
        return damage;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public int getSpeed() {
        return speed;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public Block getDest() {
        return destBlock;
    }
    public void setDestination(Block block) {
        destBlock = block;
        isMoving = true;
    }
    public void setSecondDestBlock(Block block) {
        secondDestBlock = block;
    }

    public void stopMoving() {
        destBlock = null;
        secondDestBlock = null;
        isMoving = false;
    }
    public boolean changeDestForPatrol() {
        if(!this.getBlock().equals(destBlock)) return false;
        Block tmp = destBlock;
        destBlock = secondDestBlock;
        secondDestBlock = tmp;
        return true;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public Block getDestBlock() {
        return destBlock;
    }

    public boolean isPatrolling() {
        return isPatrolling;
    }
}
