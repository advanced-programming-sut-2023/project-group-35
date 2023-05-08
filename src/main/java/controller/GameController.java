package controller;

import model.*;
import model.buildings.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import Enum.*;

import model.people.MilitaryUnit;
import view.GameMenu;

public class GameController {

    protected Reign playingReign;
    protected Game game;
    protected Map map;

    public GameController(Game game) {
        this.game = game;
        this.map = game.getMap();
        this.playingReign = game.getPlayingReign();
    }

    public String addUser(Matcher matcher) {
        User user = User.getUserByUsername(matcher.group("username"));
        if(user == null) return "this user does not exist";
        if(game.isUserInTheGame(user)) return "this user is already in the game";
        game.addReign(user);
        return "user added successfully";
    }

    public String dropBuilding(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(!areCoordinatesCorrect(x, y)) return "coordinates are not correct";
        BuildingType type = BuildingType.getBuildingTypeByName(matcher.group("type"));
        if(type == null) return "wrong building type";
        Block block = map.getBlockByLocation(x, y);
        if (!block.getFieldType().isSuitableForBuilding)
            return "the filed type is not suitable to drop building";
        if(map.getBlockByLocation(x, y).getBuilding() != null) return "there is already a building in this block";
        if(map.getBlockByLocation(x, y).getStructures().size() > 0) {
            return "drop building failed: there are some structures in this block";
        }
        if(type.equals(BuildingType.BASE)) return "you can't build a new base inside a game";
        if(type.equals(BuildingType.PITCH_RIG) && !block.getFieldType().equals(FieldType.plain))
            return "you should build the pitch rig on a plain";
        // todo check the side blocks for the same type for stoke pile and ?? ..
        if(type.goldCost > playingReign.getGold()) return "you do not have enough gold";
        if(playingReign.getResourceAmount(type.resourceToBuild) < type.resourceAmount)
            return "you do not have enough resources to build this building";
        playingReign.spendGold(type.goldCost);
        playingReign.changeResourceAmount(type.resourceToBuild, type.resourceAmount);

        return BuildTheBuilding(type, block);

    }
    public String BuildTheBuilding(BuildingType type, Block block) {
        Building building;
        if(type.checkForEquals(BuildingType.SHOP)) {
            building = new Building(type, playingReign, block);
        }
        if(type.checkForEquals(BuildingType.SMALL_STONE_GATE, BuildingType.BIG_STONE_GATEHOUSE)) {
             building = new GateHouse(type, playingReign, block);
        } else if(type.checkForEquals(BuildingType.HOVEL)) {
            building = new House(type, playingReign, block);
        } else if(type.checkForEquals(BuildingType.MOVING_BRIDGE)) {
            building = new MovingBridge(type, playingReign, block);
        } else if(type.checkForEquals(BuildingType.LOOKOUT_TOWER, BuildingType.PERIMETER_TOWER, BuildingType.DEFENCE_TURRET)) {
            building = new Tower(type, playingReign, block);
        } else if(type.checkForEquals(BuildingType.SQUARE_TOWER, BuildingType.CIRCLE_TOWER)) {
            building = new BigTower(type, playingReign, block);
        } else if(type.checkForEquals(BuildingType.ARMOURY)) {
            building = new Armory(type, playingReign, block); // delete this class?
        } else if(type.checkForEquals(BuildingType.ARMOURER)) { // what else? پست شکار و
            building = new Converter(type, playingReign, block, Resource.IRON, Resource.ARMOR );
        } else if(type.checkForEquals(BuildingType.MILL)) {
            building = new Converter(type, playingReign, block, Resource.WHEAT, Resource.FLOUR);
        } else if(type.checkForEquals(BuildingType.BLACK_SMITH)) {
            building = new Converter(type, playingReign , block, Resource.IRON, Resource.SWORD);
        } else if(type.checkForEquals(BuildingType.FLETCHER)) {
            building = new Converter(type, playingReign, block , Resource.WOOD, Resource.BOW);
        } else if(type.checkForEquals(BuildingType.POLE_TURNER)) {
            building = new Converter(type, playingReign, block , Resource.WOOD, Resource.SPEAR);
        } else if(type.checkForEquals(BuildingType.HUNTING_GROUND)) {
            building = new Converter(type, playingReign, block , Resource.MEAT, Resource.PROCESSED_MEAT);
        } else if(type.checkForEquals(BuildingType.BREWERY)) {
            building = new Converter(type, playingReign, block , Resource.HOP, Resource.BEAR);
        } else if(type.checkForEquals(BuildingType.INN)) {
            building = new Inn(type, playingReign, block); // delete???
        } else if(type.checkForEquals(BuildingType.PITCH_RIG)) {
            if(!block.getFieldType().equals(FieldType.plain)) return "you should drop a pitch rig on a plain";
            building = new Producer(type, playingReign, block, Resource.TAR);
        } else if(type.checkForEquals(BuildingType.STONE_MINE)) {
            // todo check the ground
            building = new Producer(type, playingReign, block , Resource.STONE);
        } else if(type.checkForEquals(BuildingType.STOCK_PILE)) {
            // todo check the near blocks
        } else if(type.checkForEquals(BuildingType.APPLE_GARDEN)) {
            building = new Producer(type, playingReign, block, Resource.APPLE);
        } else if(type.checkForEquals(BuildingType.WHEAT_FARM)) {
            building = new Producer(type, playingReign, block, Resource.WHEAT);
        } else if(type.checkForEquals(BuildingType.BAKERY)) {
            building = new Producer(type, playingReign, block, Resource.BREAD);
        } else if(type.checkForEquals(BuildingType.DAIRY_FARM)) {
            building = new Producer(type, playingReign, block , Resource.CHEESE);
        }

        //todo different buildings

        return "drop building successful";
    }
    public String dropWall(Matcher matcher) {
        return null;
    }
    public String dropStairs(Matcher matcher) {
        return null;
    }
    public String selectBuilding(int x, int y) {
        if(!areCoordinatesCorrect(x, y)) return "coordinates are not correct";
        Building building = map.getBlockByLocation(x, y).getBuilding();
        if(building == null) return "there is no building in this block";
        if(!building.isOwnerCorrect(game.getPlayingReign())) return "the building is not yours";
        game.setSelectedBuilding(building);
        return "select building successful";
    }

    public String selectUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(!areCoordinatesCorrect(x , y)) return "the coordinates you have entered is not in the map";
        if(game.getUnitsOfBlock(x, y).size() == 0) return "there is no unit in this block";
        ArrayList<MilitaryUnit> units = game.getUnitsOfReignInBlock(playingReign , x , y);
        if(units.size() == 0) return "you don't have any unit in this block";
        if (units.size() > 1) {
            int unitNumber = GameMenu.askUserTheUnitToSelect(printUnits(units));
            if((units.get(unitNumber)) != null) game.setSelectedUnit(units.get(unitNumber));
            else return "you did not choose the right number of the unit";
        } else {
            game.setSelectedUnit(units.get(0));
        }
        return "select units successful!";
    }

    public String printUnits(ArrayList<MilitaryUnit> units) {
        String output = "choose one of the units below by writing the ordinal number of the unit";
        int i = 1;
        for (MilitaryUnit unit : units) {
            output += "\n" + i + ": unit " + unit.getUnitType() + "with the number " + unit.getNumber()
                    + "and the state: " + unit.getUnitState();
            i++;
        }
        return output;
    }

    // drop unit chie aslan?????



    public String nextTurn() {
        return null;
    }
    public void killUnit(MilitaryUnit unit) {

    }
    public void applyChangesOfTurn() {

    }
    public void deleteTheDeadRegion() {

    }
    public void endGame() {

    }
    public void endTotalTurn() {

    }
    public void leaveGame(){}
    public void showTurnsPassed(){} //???

    public Game getGame () {
        return game;
    }
    public Reign getPlayingReign() {
        return playingReign;
    }
    public Map getMap() {
        return game.getMap();
    }
    public boolean areCoordinatesCorrect(int x , int y) {
        int dimension = game.getMap().dimensions;
        if(x <= dimension && x >= 1 && y <= dimension && y >= 1) return true;
        return false;
    }
}


