package model.buildings;

import model.Reign;
import Enum.*;
public class Inn extends Building{
    private int popularityRateIncrease;



    private int wineRateUsage;

    public Inn(Reign owner, int hp, BuildingType buildingType) {
        super(owner, hp, buildingType);
    }

    public void setWineRateUsage(int wineRateUsage) {
        this.wineRateUsage = wineRateUsage;
    }

    @Override
    public void nextTurn() {

    }
}
