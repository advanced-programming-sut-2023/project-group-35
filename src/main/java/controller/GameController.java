package controller;

import model.*;
import model.buildings.*;
import Enum.*;
import java.util.regex.Matcher;

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
        if (!block.getFieldType().isSuitableForBuilding())
            return "the filed type is not suitable to drop building";
        if(type.equals(BuildingType.PITCH_RIG) && !block.getFieldType().equals(FieldType.plain))
            return "you should build the pitch rig on a plain";
        if(map.getBlockByLocation(x, y).getBuilding() != null) return "there is already a building in this block";
        // things other than building?? // like structures
        // check the side blocks for the same type for stoke pile and ?? ..
        if(type.equals(BuildingType.BASE)) return "you can't build a new base inside a game";
        if(type.goldCost > playingReign.getGold()) return "you do not have enough gold";
        if(playingReign.getResourceAmount(type.resourceToBuild) < type.resourceAmount)
            return "you do not have enough resources to build this building";
        playingReign.spendGold(type.goldCost);
        playingReign.changeResourceAmount(type.resourceToBuild, type.resourceAmount);
        BuildTheBuilding(type);
        return ""; //todo complete
    }
    public void BuildTheBuilding(BuildingType type) {
        // if(type.equals())
        //todo different buildings
    }
    public String dropWall(Matcher matcher) {
        return null;
    }
    public String dropStairs(Matcher matcher) {
        return null;
    }
    public String selectBuilding(int x, int y) {
        if(!areCoordinatesCorrect(x, y)) return "coordinates are not correct";
        Building building = game.getBuilding(x, y);
        if(building == null) return "there is no building in this block";
        if(!building.isOwnerCorrect(game.getPlayingReign())) return "the building is not yours";
        game.setSelectedBuilding(building);
        return "select building successful";
    }

    public String selectUnit(int x , int y) {
        return null;
    }

    // drop unit chie aslan?????




    public String nextTurn() {
        return null;
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


