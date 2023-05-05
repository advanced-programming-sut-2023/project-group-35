package model.buildings;

import Enum.*;
import model.Reign;
import model.*;
public class Converter extends Building{
    int rate;
    Resource input;
    Resource output;

    public Converter(BuildingType buildingType, Reign owner, Block block, Resource input, Resource output) {
        super(buildingType, owner, block);
        this.rate = buildingType.getRelatedInteger();
        this.input = input;
        this.output = output;
    }

    @Override
    public void nextTurn() {

    }
}
