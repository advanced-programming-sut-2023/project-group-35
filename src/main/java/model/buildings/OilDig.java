package model.buildings;

import model.Reign;
import Enum.*;
public class OilDig extends Building{
    private boolean isBurning;
    public OilDig(Reign owner, BuildingType buildingType) {
        super(owner, buildingType);
    }
}
