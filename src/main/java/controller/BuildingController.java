package controller;

import model.*;
import model.buildings.Building;
import Enum.*;
import model.buildings.GateHouse;
import model.people.MilitaryUnit;

import java.util.regex.*;

public class BuildingController extends GameController{
    private final Building building;

    public BuildingController(Game game) {
        super(game);
        this.building = game.getSelectedBuilding();
    }


    public String showHitPoint() {
        return "left hitPoint of this building is " + building.getHp();
    }

    public static String createUnit(Block block, UnitType unitType, int count, Reign playingReign, Game game) {
        if(playingReign.getGold() < unitType.cost * count) return "you don't have enough gold";
        if(unitType.resourceToBuild != null && playingReign.getResourceAmount(unitType.resourceToBuild) < count)
            return "you don't have enough" + unitType.resourceToBuild.name() + "to build this unit";
        if(playingReign.getUnemployedPopulation() < count) return "you don't have enough unemployed population";
        MilitaryUnit unit = new MilitaryUnit(unitType , playingReign, block, count);
        playingReign.changeUnemployedPopulation(-count);
        playingReign.spendGold(count * unitType.cost);
        if(unitType.resourceToBuild != null) playingReign.changeResourceAmount(unitType.resourceToBuild, count);
        playingReign.getMilitaryUnits().add(unit);
        game.getAllOfTheUnits().add(unit);
        block.addUnit(unit);
        return "create unit successful";
    }
    public static String repair(Building building) {
        if(building.getHp() == building.buildingType.hp) return "this building is already repaired";
        Resource resource = building.buildingType.resourceToBuild;
        Reign playingReign = building.getOwner();
        if(resource != null && playingReign.getResourceAmount(resource) < building.buildingType.resourceAmount)
            return "you don't have enough resources to repair this building";
//        if(getMap().findUnitsOfOpponent(building.getBlock() , 3 ,  building.getOwner()))
//            return "repair failed, there are units belonging to your opponent near this building";
        building.setHp(building.buildingType.hp);
        if(resource != null) building.getOwner().changeResourceAmount(resource, building.buildingType.resourceAmount);
        return "building repaired successfully";
    }

    public String changeTaxRate(int rate) {
        if(!(building instanceof GateHouse)) return "you can't change the tax rate with this building";
        if(rate <= 0) return "change tax failed: rate amount not correct";
        building.getOwner().setTaxRate(rate);
        return "set tax rate successful"; // todo check
    }
    public String ChangeFoodDistribution (int rate) {
        if(!(building instanceof GateHouse)) return "you can't change the food rate with this building";
        if(rate <= 0) return "change food failed: rate amount not correct";
        building.getOwner().setFoodRate(rate);
        return "set food rate successful";
    }
    public void deleteSelectedBuilding() {
        game.setSelectedBuilding(null);
    }
}
