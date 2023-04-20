package main.java.model.buildings;
import main.java.Enum.*;
import main.java.model.Reign;

import java.util.ArrayList;

public class Producer extends Building{
    public int rate;

    //ArrayList<Resources> resources = new ArrayList<>();
    Resources resource;

    public Producer(Reign owner, int hp, BuildingType buildingType , int rate, Resources resources) {
        super(owner, hp, buildingType);
        this.rate = rate;
        this.resource = resources;
    }

    public void addResources() {

    }

    @Override
    public void nextTurn() {

    }
}
