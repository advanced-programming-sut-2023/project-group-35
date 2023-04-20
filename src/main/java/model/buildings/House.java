package main.java.model.buildings;

import main.java.Enum.*;
import main.java.controller.GameController;
import main.java.model.Reign;

public class House extends Building{

    public House(Reign owner, int hp, BuildingType buildingType , int Growth) {
        super(owner, hp, buildingType);
        GameController.populationChange(Growth);
    }
    @Override
    public void nextTurn() {

    }
}
