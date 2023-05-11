package model.people;

import Enum.*;
import model.Block;
import model.Reign;

import javax.swing.text.BadLocationException;

public class MilitaryUnit {

    public UnitType unitType;
    public int speed;
    private Reign owner;
    private Block block;
    private int number;
    private int hp;
    private UnitState unitState;
    private int range;
    private int damage;

    public UnitState getUnitState() {
        return unitState;
    }
    public void setUnitState(UnitState unitState) {
        this.unitState = unitState;
    }

    public MilitaryUnit(UnitType unitType, Reign owner, Block block, int number, int hp,int range,int damage,int speed) {
        this.unitType = unitType;
        this.damage = damage;
        this.owner = owner;
        this.block = block;
        this.number = number;
        this.hp = hp;
        this.unitState = UnitState.STABLE;
        this.range = range;
        this.speed = speed;
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
}
