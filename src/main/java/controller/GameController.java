package controller;

import model.*;
import model.buildings.*;

import java.util.ArrayList;
import java.util.regex.Matcher;

import model.people.MilitaryUnit;

public class GameController {

    protected Reign playingReign;
    protected Game game;

    public GameController(Game game) {
        this.game = game;
        this.playingReign = game.getPlayingReign();
    }


    public String dropBuilding(Matcher matcher) {
        return null;
    }
    public String dropWall(Matcher matcher) {
        return null;
    }
    public String dropStairs(Matcher matcher) {
        return null;
    }
    public String selectBuilding(int x, int y) {
        if(!isCoordinatesCorrect(x, y)) return "coordinates are not correct";
        Building building = game.getBuildingOfBlock(x, y);
        if(building == null) return "there is no building in this block";
        if(!building.isOwnerCorrect(game.getPlayingReign())) return "the building is not yours";
        game.setSelectedBuilding(building);
        return "select building successful";
    }

    public String selectUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(!isCoordinatesCorrect(x , y)) return "the coordinates you have entered is not in the map";
        if(game.getUnitsOfBlock(x, y).size() == 0) return "there is no unit in this block";
        ArrayList<MilitaryUnit> units = game.getUnitsOfReignInBlock(playingReign , x , y);
        if(units.size() == 0) return "you don't have any unit in this block";
        game.setSelectedUnits(units);
        return "select units successful!";
    }

    // drop unit chie aslan?????



    public static void populationChange(int number) {

    }
    public String nextTurn() {
        return null;
    }
    public void applyChangesOfTurn() {

    }
    public void deleteTheDeadRegion() {

    }
    public void endGame() {

    }
    public void endTotalTurn()
    {}
    public void leaveGame(){}
    public void showTurnsPassed(){}

    public Game getGame () {
        return game;
    }
    public Reign getPlayingReign() {
        return playingReign;
    }
    public Map getMap() {
        return game.getMap();
    }
    public boolean isCoordinatesCorrect(int x , int y) {
        int dimension = game.getMap().dimensions;
        if(x <= dimension && x >= 1 && y <= dimension && y >= 1) return true;
        return false;
    }
}


