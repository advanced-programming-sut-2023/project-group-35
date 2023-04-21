package model.buildings;
import Enum.*;
import controller.*;
import model.Reign;

import java.util.ArrayList;

public class Church extends Building{
    public Church(Reign owner, int hp, BuildingType buildingType) {
        super(owner, hp, buildingType);
        GameController.populationChange(2);
    }


    public UnitType getUnitType(UnitType unitType) {
        return null;
    }
    @Override
    public void nextTurn() {

    }

}
