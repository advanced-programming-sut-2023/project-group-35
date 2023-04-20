package main.java.model.people;

import main.java.Enum.*;

import javax.swing.plaf.synth.Region;

public class MilitaryUnit {
    private Region owner;
    public UnitType unitType;
    private int number;
    private int hp;
    private UnitState unitState;

    public UnitState getUnitState() {
        return unitState;
    }
    public void setUnitState(UnitState unitState) {
        this.unitState = unitState;
    }


    public MilitaryUnit(Region owner, UnitType unitType, int number , int hp) {
        this.owner = owner;
        this.unitType = unitType;
        this.number = number;
        this.hp = hp;
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



}
