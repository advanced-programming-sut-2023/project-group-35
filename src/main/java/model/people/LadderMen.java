package model.people;
import Enum.*;
import model.Reign;

import javax.swing.plaf.synth.Region;

public class LadderMen extends MilitaryUnit{

    public LadderMen(Reign owner, UnitType unitType, int number , int hp) {
        super(owner, unitType, number , hp);
    }
}
