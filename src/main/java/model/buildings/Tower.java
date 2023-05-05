package model.buildings;

import model.Block;
import model.Reign;
import model.people.MilitaryUnit;
import Enum.*;
import model.people.RangedUnit;

import java.util.ArrayList;

public class Tower extends Building{
    int fireRange;
    int defendRange;
    private MilitaryUnit militaryUnit; // what is this for??

    private ArrayList<RangedUnit> rangedUnits = new ArrayList<>();


    public Tower(BuildingType type, Reign owner, Block block, int fireRange, int defendRange) {
        super(type, owner, block);
        this.fireRange = fireRange;
        this.defendRange = defendRange;
        //this.militaryUnit = unit;
    }

    public ArrayList<RangedUnit> getRangedUnits() {
        return rangedUnits;
    }

    @Override
    public void nextTurn() {

    }
}
