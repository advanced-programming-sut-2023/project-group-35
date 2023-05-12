package model.buildings;

import model.Block;
import model.Reign;
import Enum.*;
public class KillingDig extends Building{
    private boolean visibility = false;

    public KillingDig(BuildingType buildingType, Reign owner, Block block) {
        super(buildingType, owner, block);
    }

    public void changeVisibility() {
        visibility = !visibility;
    }

    public boolean isVisibility() {
        return visibility;
    }


}
