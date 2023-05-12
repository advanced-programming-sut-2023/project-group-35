package model.buildings;

import model.Block;
import model.Reign;
import Enum.*;
public class Inn extends Building {
    private int wineUsageRate;



    public Inn(BuildingType buildingType, Reign owner, Block block) {
        super(buildingType, owner, block);
    }

    public void setWineUsageRate(int wineUsageRate) {
        this.wineUsageRate = wineUsageRate;
    }
    public int getWineUsageRate() {
        return wineUsageRate;
    }

    @Override
    public void nextTurn() {
        if(super.getOwner().getResourceAmount(Resource.BEAR) < 1){
            return;
        }
    else if(super.getOwner().getResourceAmount(Resource.BEAR) < wineUsageRate){
            super.getOwner().changePopularity(super.getOwner().getResourceAmount(Resource.BEAR)*2);
            super.getOwner().changeResourceAmount(Resource.BEAR,-super.getOwner().getResourceAmount(Resource.BEAR));
    }
    else{
        super.getOwner().changeResourceAmount(Resource.BEAR,-wineUsageRate);
        super.getOwner().changePopularity(wineUsageRate*2);
    }
    }
}
