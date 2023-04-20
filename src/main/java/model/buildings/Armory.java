package main.java.model.buildings;
import main.java.Enum.*;
import main.java.model.Reign;

import java.util.ArrayList;

public class Armory extends Building{

    private static int capacity = 0;
    public Armory(Reign owner, int hp, BuildingType buildingType, int capacity) {
        super(owner, hp, buildingType);
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
