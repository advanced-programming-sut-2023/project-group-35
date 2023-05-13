package model.structures;

import model.Block;
import model.Reign;
import model.people.Engineer;
import model.people.MilitaryUnit;
import Enum.*;

public class MovingShield extends Structure{

    public MovingShield(StructuresType type, Reign owner, Engineer mover, Block block) {
        super(type, owner, mover, block);
    }
}
