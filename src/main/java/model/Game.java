package model;

import model.buildings.*;
import model.people.MilitaryUnit;

import java.util.*;

public class Game {
    private User starter;
    private Map map;
    private Reign playingReign;

    private Building selectedBuilding;
    private ArrayList<MilitaryUnit> selectedUnits; // people nabayad bashe?
    private MilitaryUnit selectedSingleUnit;
    private final ArrayList<MilitaryUnit> allOfTheUnits = new ArrayList<>();
    //private Block selectedBlock;
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Reign> reigns = new ArrayList<>();
    int turnsPassed;

    public Game(User starter, Map map) {
        this.starter = starter;
        this.map = map;
        addReign(starter);
    }

    public MilitaryUnit getSelectedSingleUnit() {
        return selectedSingleUnit;
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

    public ArrayList<MilitaryUnit> getSelectedUnits() {
        return selectedUnits;
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
    public MilitaryUnit getSelectedUnit() {
        return selectedUnit;
    }
    public void removeUnit(MilitaryUnit unit) {

    }
}

