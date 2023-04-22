package model.buildings;

import Enum.*;
import model.Reign;

public class Converter extends Building{
    int rate;
    Resource input;
    Resource output;

    public Converter(Reign owner, int hp , BuildingType buildingType, int rate, Resource input, Resource output) {
        super(owner, hp, buildingType);
        this.rate = rate;
        this.input = input;
        this.output = output;
    }

    @Override
    public void nextTurn() {

    }
}
