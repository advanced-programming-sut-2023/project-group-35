package model.structures;

import model.Block;
import model.Reign;
import model.people.Engineer;
import Enum.*;

public class Ballista extends Structure{
    public Ballista(StructuresType type, Reign owner, Engineer mover, Block block) {
        super(type, owner, mover, block);
    }
}
