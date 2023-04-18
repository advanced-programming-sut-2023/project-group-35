package model.buildings;

import model.Reign;
import Enum.*;
public class OilDig extends Building{
    private boolean isBurning;
    public OilDig(Reign owner, int hp, BuildingType buildingType) {
        super(owner, hp, buildingType);
    }
}
