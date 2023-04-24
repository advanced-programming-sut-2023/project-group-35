package model.people;

import Enum.*;
import model.*;


public class RangedUnit extends MilitaryUnit{
    private int range;

    public RangedUnit(UnitType unitType, Reign owner, Block block, int number, int hp, int range) {
        super(unitType, owner, block, number, hp);
        this.range = range;
    }
}
