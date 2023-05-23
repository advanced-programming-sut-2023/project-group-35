package model;

import model.buildings.*;
import model.people.MilitaryUnit;

import java.util.*;

public class Game {
    private User starter;
    private Map map;
    protected Reign playingReign;
    private Building selectedBuilding;
    private MilitaryUnit selectedUnit;
    private final ArrayList<MilitaryUnit> allOfTheUnits = new ArrayList<>();
    private final ArrayList<Building> allTheBuildings = new ArrayList<>();
    private int numberOfPlayers = 0;
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Reign> reigns = new ArrayList<>();
    private int turnsPassed;

    public Game(User starter, Map map) {
        this.starter = starter;
        this.map = map;
        addReign(starter);
    }

    public MilitaryUnit getSelectedUnit() {
        return selectedUnit;
    }

    public String showReigns() {
        String output = "REIGNS IN THE GAME:";
        int i = 1;
        for (Reign reign : reigns) {
            output += "\n" + i + "- " + "username: " + reign.getUser().getUserName()
                    + "Nick Name: " + reign.getNickName();
        }
        return output;
    }

    public ArrayList<MilitaryUnit> getAllOfTheUnits() {
        return allOfTheUnits;
    }

    public Reign getPlayingReign() {
        return playingReign;
    }
    public Reign getReignByNickName(String nickName) {
        for (Reign reign : reigns) {
            if(reign.getNickName().equals(nickName)) return reign;
        }
        return null;
    }
    public boolean isUserInTheGame(User userGiven) {
        for (User user : users) {
            if(user.equals(userGiven)) return true;
        }
        return false;
    }

    public void addReign(User user) {
        users.add(user);
        Reign reign = new Reign(user);
        reigns.add(reign);
        if(reigns.size() == 1) playingReign = reign;
        map.addRegin(reign);
        numberOfPlayers++;
        reign.earnGold(1000);
    }
    public void setSelectedBuilding(Building building) {
        this.selectedBuilding = building;
    }

    public void setSelectedUnit(MilitaryUnit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public Map getMap() {
        return map;
    }
    public Building getBuildingOfBlock(int x , int y) {
        return getMap().getBlockByLocation(x, y).getBuilding();
    }
    public ArrayList<MilitaryUnit> getUnitsOfBlock(int x , int y) {
        return map.getBlockByLocation(x , y).getMilitaryUnits();
    }

    public ArrayList<MilitaryUnit> getUnitsOfReignInBlock(Reign reign , int x , int y) {
        ArrayList <MilitaryUnit> units = new ArrayList<>();
        for (MilitaryUnit unit : getUnitsOfBlock(x, y)) {
            if (unit.getOwner().equals(reign)) units.add(unit);
        }
        return units;
    }

    public Building getSelectedBuilding() {
        return selectedBuilding;
    }

    public int getTurnsPassed() {
        return turnsPassed;
    }
    public void oneTurnPassed() {
        turnsPassed++;
    }

    public void removeUnitIfKilled(MilitaryUnit unit) {
        if(unit.getHp() > 0) return;
        allOfTheUnits.remove(unit);
        unit.getBlock().getMilitaryUnits().remove(unit);
        unit.getOwner().getMilitaryUnits().remove(unit);
    }
    public void addBuilding(Building building) {
        allTheBuildings.add(building);
    }

    public ArrayList<Building> getAllTheBuildings() {
        return allTheBuildings;
    }
    public ArrayList<Reign> getReigns() {
        return reigns;
    }
    public Reign getNextReign() {
        int turn = reigns.indexOf(playingReign);
        if(turn == reigns.size()-1) return reigns.get(0);
        return reigns.get(turn + 1);
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setPlayingReign(Reign playingReign) {
        this.playingReign = playingReign;
    }
}

