package model.people;
import Enum.*;
import model.Block;
import model.Reign;

import javax.swing.plaf.synth.Region;

public class LadderMen extends MilitaryUnit{

    public LadderMen(UnitType unitType, Reign owner, Block block, int number) {
        super(unitType, owner, block, number);
    }
}
