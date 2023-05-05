package model.buildings;

import Enum.*;
import controller.GameController;
import model.Block;
import model.Reign;

public class House extends Building{

    public House(BuildingType buildingType, Reign owner, Block block) {
        super(buildingType, owner, block);
        owner.changePopulation(buildingType.getRelatedInteger());
    }

    @Override
    public void nextTurn() {

    }
}
