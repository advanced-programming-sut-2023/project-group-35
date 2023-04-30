package model.buildings;

import model.Reign;
import model.people.MilitaryUnit;
import Enum.*;
import model.structures.Structure;

import java.util.ArrayList;

public class bigTower extends Tower{

    ArrayList<Structure> structures = new ArrayList<>();
    public bigTower(Reign owner, BuildingType buildingType, int fireRange, int defendRange, MilitaryUnit militaryUnit) {
        super(owner, buildingType, fireRange, defendRange, militaryUnit);
    }

    @Override
    public void nextTurn() {

    }
}
