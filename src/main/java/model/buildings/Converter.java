package model.buildings;

import Enum.*;
import model.Reign;
import model.*;
public class Converter extends Building{
    int rate;
    Resource input;
    Resource output;

    public Converter(Reign owner, BuildingType buildingType, Block block, int rate, Resource input, Resource output) {
        super(buildingType, owner, block);
        this.rate = rate;
        this.input = input;
        this.output = output;
    }

    @Override
    public void nextTurn() {

    }
}
