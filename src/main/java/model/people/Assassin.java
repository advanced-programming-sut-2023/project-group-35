package model.people;

import Enum.*;
import model.*;
public class Assassin extends MilitaryUnit{


    private boolean isVisible;

    public Assassin(UnitType unitType, Reign owner, Block block, int number, int hp, boolean isVisible) {
        super(unitType, owner, block, number, hp);
        this.isVisible = isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
