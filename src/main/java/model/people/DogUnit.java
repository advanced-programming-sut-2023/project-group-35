package model.people;

import Enum.*;
import model.*;

public class DogUnit extends MilitaryUnit{
    public DogUnit(Reign owner, UnitType unitType, int number, int hp) {
        super(owner, unitType, number, hp);
    }
}
