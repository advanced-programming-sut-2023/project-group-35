package model.people;

import Enum.*;
import model.*;

public class DogUnit extends MilitaryUnit{
    public DogUnit(UnitType unitType, Reign owner, Block block, int number, int hp) {
        super(unitType, owner, block, number, hp);
    }
}
