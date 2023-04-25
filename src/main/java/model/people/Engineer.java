package model.people;

import java.util.ArrayList;
import Enum.*;
import model.Block;
import model.Reign;

import javax.swing.plaf.synth.Region;

public class Engineer extends MilitaryUnit{


    private boolean isWorking = false;

    public Engineer(UnitType unitType, Reign owner, Block block, int number, int hp) {
        super(unitType, owner, block, number, hp);
    }
    public boolean isWorking() {
        return isWorking;
    }
    public void setWorking(boolean working) {
        isWorking = working;
    }

}
