package main.java.model.people;
import main.java.Enum.*;

import javax.swing.plaf.synth.Region;

public class RangedUnit extends MilitaryUnit{
    private int range;

    public RangedUnit(Region owner, UnitType unitType, int number, int hp, int range) {
        super(owner, unitType, number , hp);
        this.range = range;
    }
}
