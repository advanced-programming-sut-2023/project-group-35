package model.buildings;

import model.Block;
import model.Reign;
import Enum.*;
public class Inn extends Building {
    private int popularityRateIncrease; //handle this
    private int wineUsageRate;


    public Inn(BuildingType buildingType, Reign owner, Block block) {
        super(buildingType, owner, block);
        owner.changePopularity(buildingType.getRelatedInteger());
    }

    public void setWineUsageRate(int wineUsageRate) {
        this.wineUsageRate = wineUsageRate;
    }
    public int getWineUsageRate() {
        return wineUsageRate;
    }

    @Override
    public void nextTurn() {

    }
}
