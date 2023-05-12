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
        if(super.getOwner().getResourceAmount(Resource.WINE) < 1){
            return;
        }
    else if(super.getOwner().getResourceAmount(Resource.WINE) < wineRateUsage){
            super.getOwner().setPopularity(super.getOwner().getPopularity()+super.getOwner().getResourceAmount(Resource.WINE)*2);
            super.getOwner().changeResourceAmount(Resource.WINE,-super.getOwner().getResourceAmount(Resource.WINE));
    }
    else{
        super.getOwner().changeResourceAmount(Resource.WINE,-wineRateUsage);
        super.getOwner().setPopularity(super.getOwner().getPopularity()+wineRateUsage*2);
    }
    }
}
