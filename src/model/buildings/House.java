package model.buildings;

import Enum.*;
import controller.GameController;
import model.Reign;

public class House extends Building{

    public House(Reign owner, int hp, BuildingType buildingType , int Growth) {
        super(owner, hp, buildingType);
        GameController.populationChange(Growth);
    }
    @Override
    public void nextTurn() {

    }
}
