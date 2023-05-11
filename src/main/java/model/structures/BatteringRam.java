package model.structures;

import model.people.Engineer;

public class BatteringRam extends Structure{
    public BatteringRam(Engineer mover) {
        super(300, true, 3, mover);
    }
}
