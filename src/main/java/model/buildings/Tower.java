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


    public Tower(BuildingType type, Reign owner, Block block) {
        super(type, owner, block);
        this.fireRange = type.getRelatedInteger();
        this.defendRange = 2 * type.getRelatedInteger(); // what's the difference?
        //this.militaryUnit = unit;
    }

    public ArrayList<RangedUnit> getRangedUnits() {
        return rangedUnits;
    }


}
