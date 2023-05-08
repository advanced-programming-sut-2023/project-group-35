package model.people;

import Enum.*;
import model.Block;
import model.Reign;

import javax.swing.text.BadLocationException;

public class MilitaryUnit {

    public UnitType unitType;
    private Reign owner;
    private Block block;
    private Block destBlock;
    private int number;
    private int hp;
    private UnitState unitState;
    private boolean isMoving;

    public UnitState getUnitState() {
        return unitState;
    }
    public void setUnitState(UnitState unitState) {
        this.unitState = unitState;
    }

    public MilitaryUnit(UnitType unitType, Reign owner, Block block, int number, int hp) {
        this.unitType = unitType;
        this.owner = owner;
        this.block = block;
        this.number = number;
        this.hp = hp;
        this.unitState = UnitState.STABLE;
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
    public void getDamaged(int amountOfDamage){}
    public Reign getOwner() {
        return owner;
    }

    public Block getBlock() {
        return block;
    }

    public UnitType getUnitType() {
        return unitType;
    }
    public void stopMoving() {
        destBlock = null;
        isMoving = false;
    }
    public void startMoving(Block dest) {
        destBlock = dest;
        isMoving = true;
    }
    public boolean isMoving() {
        return isMoving;
    }

    public Block getDestBlock() {
        return destBlock;
    }
}
