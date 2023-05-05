package model.buildings;

import model.Block;
import model.Reign;
import Enum.*;
public class GateHouse extends House{
    private boolean isOpen;

    public GateHouse(BuildingType buildingType, Reign owner, Block block, int growth) {
        super(buildingType, owner, block, growth);
    }

    public void nextTurn() {

    }
}
