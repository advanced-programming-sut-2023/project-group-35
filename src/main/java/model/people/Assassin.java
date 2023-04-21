package model.people;

import Enum.*;
import model.*;
public class Assassin extends MilitaryUnit{


    private boolean isVisible;
    public Assassin(Reign owner, UnitType unitType, int number, int hp) {
        super(owner, unitType, number, hp);
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
