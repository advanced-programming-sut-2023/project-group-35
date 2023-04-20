package main.java.model.buildings;

import main.java.model.Reign;
import main.java.Enum.*;
public class OilDig extends Building{
    private boolean isBurning;
    public OilDig(Reign owner, int hp, BuildingType buildingType) {
        super(owner, hp, buildingType);
    }
}
