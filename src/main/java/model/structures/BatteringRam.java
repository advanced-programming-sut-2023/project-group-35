package model.structures;

import model.Block;
import model.Reign;
import model.people.Engineer;
import Enum.*;

public class BatteringRam extends Structure{
    public BatteringRam(StructuresType type, Reign owner, Engineer mover, Block block) {
        super(type, owner, mover, block);
    }
}
