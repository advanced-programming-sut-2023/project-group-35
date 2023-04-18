package model.people;

import javax.swing.plaf.synth.Region;
import Enum.*;
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
