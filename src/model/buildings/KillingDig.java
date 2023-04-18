package model.buildings;

import model.Reign;
import Enum.*;
public class KillingDig extends Building{

    private boolean visible = false;
    public KillingDig(Reign owner, int hp, BuildingType buildingType) {
        super(owner, hp, buildingType);
    }
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void nextTurn() {

    }
}
