package model;

import model.buildings.*;
import model.people.MilitaryUnit;

import java.util.*;

public class Game {
    private Map map;
    private User starter;
    private Reign playingReign;

    private Building selectedBuilding;
    private ArrayList<MilitaryUnit> selectedUnits; // people nabayad bashe?
    //private Block selectedBlock;
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Reign> reigns = new ArrayList<>();
    int turnsPassed;

    public String showReigns() {
        String output = "REIGNS IN THE GAME:";
        int i = 1;
        for (Reign reign : reigns) {
            output += "\n" + i + "- " + "username: " + reign.getUser().getUserName()
                    + "Nick Name: " + reign.getNickName();
        }
        return output;
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

    public void setSelectedBuilding(Building building) {
        this.selectedBuilding = building;
    }

    public void setSelectedUnits(ArrayList<MilitaryUnit> selectedUnits) {
        this.selectedUnits = selectedUnits;
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
}
