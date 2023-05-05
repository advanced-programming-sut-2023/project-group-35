package model.buildings;

import model.Block;
import model.Reign;
import Enum.*;
public class OilDig extends Building{
    private boolean isBurning;

    public OilDig(BuildingType buildingType, Reign owner, Block block) {
        super(buildingType, owner, block);
    }
}
