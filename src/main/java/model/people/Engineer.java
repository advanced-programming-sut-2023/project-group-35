package model.people;

import java.util.ArrayList;
import Enum.*;
import model.Reign;

import javax.swing.plaf.synth.Region;

public class Engineer extends MilitaryUnit{
    public boolean isWorking = false;
    public Engineer(Reign owner, UnitType unitType, int number , int hp) {
        super(owner, unitType , number , hp);
    }
}
