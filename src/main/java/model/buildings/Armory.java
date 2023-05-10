package model.buildings;

import Enum.*;
import model.Block;
import model.Reign;

import java.util.ArrayList;

public class Armory extends Building{

    private static int capacity = 0;

    public Armory(BuildingType buildingType, Reign owner, Block block) {
        super(buildingType, owner, block);
        Armory.capacity += capacity;
        //addResources();
    }


    public void addResources() {

    }
    public static int getCapacity() {
        return capacity;
    }

    @Override
    public void nextTurn() {
    return;
    }

}
