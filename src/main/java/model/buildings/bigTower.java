package model.buildings;

import model.Block;
import model.Reign;
import Enum.*;
import model.structures.Structure;

import java.util.ArrayList;

public class BigTower extends Tower{

    ArrayList<Structure> structures = new ArrayList<>();
    public BigTower(BuildingType buildingType, Reign owner, Block block) {
        super(buildingType, owner , block);

    }

    @Override
    public void nextTurn() {
    return;
    }
}
