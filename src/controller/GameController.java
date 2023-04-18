package controller;

import model.*;
import model.buildings.Building;

import java.util.ArrayList;
import java.util.regex.Matcher;
import Enum.*;
import view.Menu;

import javax.swing.plaf.synth.Region;

public class GameController {
    private Map map;
    private User starter;
    private Reign currentReign;
    private Building selectedBuilding;
    private Block selectedBlock;
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Region> regions = new ArrayList<>();
    int turnsPassed;

    public GameController() {
    }

    public static void showPopularityFactors() {
    }
    public void showPopularity() {
    }
    public void showFoodList() {
    }
    public String setFoodRate(int rate) {
        return null;
    }
    public void showFoodRate() {

    }
    public String setTaxRate(int rate) {
        return null;
    }
    public void showTaxRate() {

    }
    public String setFearRate(int rate) {
        return null;
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

    public String createUnit(Matcher matcher) {
        return null;
    }
    public String repair() {
        return null;
    }

    public String changeTaxRate(int rate) {
        return null;
    }
    public String foodDistribution (int rate) {
        return null;
    }




    public String selectUnit(int x , int y) {
        return null;
    }
    public String moveUnit(int x, int y) {
        return null;
    }

    public String setUnitState(String state) {
        return null;
    }
    public UnitState getUnitState(String state) {
        return null;
    }

    public String attackEnemy(int x , int y) {
        return null;
    }

    public String airAttack(int x, int y) {
        return null;
    }

    public String pourOil(Direction direction) {
        return null;
    }
    public String digTunnel(int x , int y) {
        return null;
    }

    public String buildEquipment(StructuresType equipmentType) {
        return null;
    }
    public String digMoat(int x, int y) {
        return null;
    }

    public String fillMoat(int x, int y) {
        return null;
    }

    public String putOnLadder(int x, int y) {
        return null;
    }

    public String pushOffLadder(int x , int y) {
        return null;
    }
    public String climbUp(int x,int y){return null;}
    public String disbandUnit() {
        return null;
    }

    public String moveStructure() {
        return null;
    }
    public String dPS(int x,int y){return null;}





    public String showTradeList () {
        return null;
    }
    public String showMembers() {
        return null;
    }
    public String addRequest(Matcher matcher) {
        return null;
    }
    public String acceptTrade(Matcher matcher) {
        return null;
    }
    public String donate(Matcher matcher) {
        return null;
    }
    public String showTradeHistory() {
        return null;
    }

    public String showPriceList() {
        return null;
    }
    public String checkForBuying() {
        return null;
    }
    public String purchase() {
        return null;
    }
    public String checkForSelling() {
        return null;
    }
    public String sell() {
        return null;
    }


    // things buildings can do like population
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


