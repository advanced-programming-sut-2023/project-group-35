package main.java.model.people;

import java.util.ArrayList;
import main.java.Enum.*;

import javax.swing.plaf.synth.Region;

public class Engineer extends MilitaryUnit{
    public boolean isWorking = false;
    public Engineer(Region owner, UnitType unitType, int number , int hp) {
        super(owner, unitType , number , hp);
    }
}
