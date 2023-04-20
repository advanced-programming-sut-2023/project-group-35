package main.java.model.buildings;

import main.java.model.Reign;
import main.java.model.people.MilitaryUnit;
import main.java.Enum.*;
import main.java.model.structures.Structure;

import java.util.ArrayList;

public class bigTower extends Tower{

    ArrayList<Structure> structures = new ArrayList<>();
    public bigTower(Reign owner, int hp, BuildingType buildingType, int fireRange, int defendRange, MilitaryUnit militaryUnit) {
        super(owner, hp, buildingType, fireRange, defendRange, militaryUnit);
    }

    @Override
    public void nextTurn() {

    }
}
