package model.buildings;
import Enum.*;
import controller.*;
import model.Block;
import model.Reign;

import java.util.ArrayList;

public class Church extends Building{
    public Church( BuildingType buildingType, Reign owner, Block block) {
        super(buildingType, owner, block);
        owner.changePopulation(+2);
    }


    public UnitType getUnitType(UnitType unitType) {
        return null;
    }

    @Override
    public Reign getOwner() {
        return super.getOwner();
    }

    @Override
    public void nextTurn() {
     this.getOwner().changePopularity(1);
    }

}
