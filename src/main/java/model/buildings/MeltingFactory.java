package model.buildings;
import Enum.*;
import model.Reign;

public class MeltingFactory extends Building{
    private boolean isFunctioning;
    public MeltingFactory(Reign owner, BuildingType buildingType) {
        super(owner, buildingType);
    }
}
