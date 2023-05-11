package model.structures;

import model.people.Engineer;
import model.people.MilitaryUnit;

public class MovingShield extends Structure{

    public MovingShield(MilitaryUnit unit) {
        super(150, true, unit.getSpeed(),unit);
    }
}
