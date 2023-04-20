package main.java.model.people;

import javax.swing.plaf.synth.Region;
import main.java.Enum.*;
public class Assassin extends MilitaryUnit{


    private boolean isVisible;
    public Assassin(Region owner, UnitType unitType, int number, int hp) {
        super(owner, unitType, number, hp);
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
