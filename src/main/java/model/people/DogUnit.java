package main.java.model.people;
import main.java.Enum.*;

import javax.swing.plaf.synth.Region;

public class DogUnit extends MilitaryUnit{
    public DogUnit(Region owner, UnitType unitType, int number, int hp) {
        super(owner, unitType, number, hp);
    }
}
