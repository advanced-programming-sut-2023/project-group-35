package model.buildings;

import Enum.*;
import model.Reign;

public class Converter extends Building{
    int rate;
    Resource input;
    Resource output;

    public Converter(Reign owner, BuildingType buildingType, int rate, Resource input, Resource output) {
        super(owner, buildingType);
        this.rate = rate;
        this.input = input;
        this.output = output;
    }

    @Override
    public void nextTurn() {

    }
}
