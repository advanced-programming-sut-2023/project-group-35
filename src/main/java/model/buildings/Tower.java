package main.java.model.buildings;

import main.java.model.Reign;
import main.java.model.people.MilitaryUnit;
import main.java.Enum.*;
import main.java.model.people.RangedUnit;

import java.util.ArrayList;

public class Tower extends Building{
    int fireRange;
    int defendRange;
    private MilitaryUnit militaryUnit;


    private ArrayList<RangedUnit> rangedUnits = new ArrayList<>();

    public Tower(Reign owner, int hp, BuildingType buildingType, int fireRange, int defendRange, MilitaryUnit militaryUnit) {
        super(owner, hp, buildingType);
        this.fireRange = fireRange;
        this.defendRange = defendRange;
        this.militaryUnit = militaryUnit;
    }

    public ArrayList<RangedUnit> getRangedUnits() {
        return rangedUnits;
    }

    @Override
    public void nextTurn() {

    }
}
