package model.buildings;

import Enum.*;
import model.Reign;

import java.util.ArrayList;

public class Armory extends Building{

    private static int capacity = 0;
    public Armory(Reign owner, BuildingType buildingType, int capacity) {
        super(owner, buildingType);
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

    }

}
