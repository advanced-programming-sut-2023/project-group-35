package model.people;

import Enum.*;
import model.*;


public class RangedUnit extends MilitaryUnit{
    private int range;

    public RangedUnit(Reign owner, UnitType unitType, int number, int hp, int range) {
        super(owner, unitType, number , hp);
        this.range = range;
    }
}
