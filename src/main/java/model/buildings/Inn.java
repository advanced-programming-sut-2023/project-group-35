package model.buildings;

import model.Block;
import model.Reign;
import Enum.*;
public class Inn extends Building {
    private int popularityRateIncrease; //handle this
    private int wineRateUsage;


    public Inn(BuildingType buildingType, Reign owner, Block block) {
        super(buildingType, owner, block);
    }

    public void setWineRateUsage(int wineRateUsage) {
        this.wineRateUsage = wineRateUsage;
    }

    public int getWineRateUsage() {
        return wineRateUsage;
    }
    @Override
    public void nextTurn() {

    }
}
