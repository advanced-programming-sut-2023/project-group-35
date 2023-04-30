package model.buildings;

import model.Reign;
import Enum.*;
public class Inn extends Building{
    private int popularityRateIncrease;



    private int wineRateUsage;

    public Inn(Reign owner, BuildingType buildingType) {
        super(owner, buildingType);
    }

    public void setWineRateUsage(int wineRateUsage) {
        this.wineRateUsage = wineRateUsage;
    }

    @Override
    public void nextTurn() {

    }
}
