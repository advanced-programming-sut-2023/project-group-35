package model.buildings;

import model.Block;
import model.Reign;
import model.people.MilitaryUnit;
import Enum.*;
import model.structures.Structure;

import java.util.ArrayList;

public class bigTower extends Tower{

    ArrayList<Structure> structures = new ArrayList<>();
    public bigTower(BuildingType buildingType, Reign owner, Block block,  int fireRange, int defendRange) {
        super(buildingType, owner , block, fireRange, defendRange);
    }

    @Override
    public void nextTurn() {

    }
}
