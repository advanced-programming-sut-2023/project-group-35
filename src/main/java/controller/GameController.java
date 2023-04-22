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
        return null;
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
}


