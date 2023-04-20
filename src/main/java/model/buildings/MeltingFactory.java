package main.java.model.buildings;
import main.java.Enum.*;
import main.java.model.Reign;

public class MeltingFactory extends Building{
    private boolean isFunctioning;
    public MeltingFactory(Reign owner, int hp, BuildingType buildingType) {
        super(owner, hp, buildingType);
    }
}
