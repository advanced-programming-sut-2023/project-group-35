package model.buildings;

import Enum.*;
import controller.GameController;
import model.Reign;

public class House extends Building{

    public House(Reign owner, BuildingType buildingType , int Growth) {
        super(owner, buildingType);
        owner.changePopulation(Growth);
    }
    @Override
    public void nextTurn() {

    }
}
