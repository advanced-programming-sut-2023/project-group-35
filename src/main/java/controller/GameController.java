package controller;

import model.*;
import model.buildings.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import Enum.*;

public class GameController {

    protected Reign playingReign;
    protected Game game;

    public GameController(Game game) {
        this.game = game;
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


