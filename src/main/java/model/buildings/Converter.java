package main.java.model.buildings;

import main.java.Enum.*;
import main.java.model.Reign;

public class Converter extends Building{
    int rate;
    Resources input;
    Resources output;

    public Converter(Reign owner,int hp , BuildingType buildingType, int rate, Resources input, Resources output) {
        super(owner, hp, buildingType);
        this.rate = rate;
        this.input = input;
        this.output = output;
    }

    @Override
    public void nextTurn() {

    }
}
