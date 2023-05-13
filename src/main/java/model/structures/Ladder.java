package model.structures;

import model.Block;
import model.Reign;
import Enum.*;
import model.people.Engineer;

public class Ladder extends Structure{
    public Ladder(StructuresType type, Reign owner, Engineer mover, Block block) {
        super(type, owner, mover, block);
    }
}
