package model.buildings;

import Enum.*;
import model.Block;
import model.Reign;

public class StoreHouse extends Building{

    public StoreHouse(BuildingType buildingType, Reign owner, Block block) {
        super(buildingType, owner, block);
        owner.changeResourcesCapacity(buildingType, buildingType.getRelatedInteger());
    }

    public void isDestroyed() {
        getOwner().changeResourcesCapacity(buildingType, buildingType.getRelatedInteger());
    }
}
