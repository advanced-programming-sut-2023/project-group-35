package main.java.model.buildings;

import main.java.model.Reign;
import main.java.Enum.*;
public class GateHouse extends House{
    private boolean isOpen;
    public GateHouse(Reign owner, int hp, BuildingType buildingType, int Growth) {
        super(owner, hp, buildingType, Growth);
    }
    public void nextTurn() {

    }
}
