package model.buildings;
import Enum.*;
import model.Block;
import model.Reign;

public class MeltingFactory extends Building{
    private boolean isFunctioning;

    public MeltingFactory(BuildingType buildingType, Reign owner, Block block) {
        super(buildingType, owner, block);
    }
}
