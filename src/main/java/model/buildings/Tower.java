package model.buildings;

import model.Reign;
import model.people.MilitaryUnit;
import Enum.*;
import model.people.RangedUnit;

import java.util.ArrayList;

public class Tower extends Building{
    int fireRange;
    int defendRange;
    private MilitaryUnit militaryUnit;


    private ArrayList<RangedUnit> rangedUnits = new ArrayList<>();

    public Tower(Reign owner, BuildingType buildingType, int fireRange, int defendRange, MilitaryUnit militaryUnit) {
        super(owner, buildingType);
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
