package model.buildings;

import Enum.*;
import model.Block;
import model.Reign;

public class StoreHouse extends Building{

    public StoreHouse(BuildingType buildingType, Reign owner, Block block) {
        super(buildingType, owner, block);
        owner.addToResourcesCapacity(buildingType, buildingType.getRelatedInteger());
    }

}
