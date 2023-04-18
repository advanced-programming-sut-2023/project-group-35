package model.people;
import Enum.*;

import javax.swing.plaf.synth.Region;

public class Tunneler extends MilitaryUnit{
    public Tunneler(Region owner, UnitType unitType, int number, int hp) {
        super(owner, unitType, number, hp);
    }
}
