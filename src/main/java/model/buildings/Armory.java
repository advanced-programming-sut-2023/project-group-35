package model.buildings;

import Enum.*;
import model.Block;
import model.Reign;
import model.people.MilitaryUnit;

import java.util.ArrayList;

public class Armory extends Building{

    private static int capacity = 0;
    //private ArrayList<MilitaryUnit> units = new ArrayList<>(); is this necessary?

    public Armory(BuildingType buildingType, Reign owner, Block block) {
        super(buildingType, owner, block);
        Armory.capacity += buildingType.getRelatedInteger();
        //addResources(); ????
    }


    public void addResources() {

    }
    public static int getCapacity() {
        return capacity;
    }

    @Override
    public void nextTurn() {

    }

}
