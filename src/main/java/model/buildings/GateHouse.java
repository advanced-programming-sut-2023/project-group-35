package model.buildings;

import model.Reign;
import Enum.*;
public class GateHouse extends House{
    private boolean isOpen;
    public GateHouse(Reign owner, BuildingType buildingType, int Growth) {
        super(owner, buildingType, Growth);
    }
    public void nextTurn() {

    }
}
