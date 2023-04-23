package model;

import model.buildings.*;
import model.people.MilitaryUnit;

import javax.swing.plaf.synth.*;
import java.util.*;

public class Game {
    private Map map;
    private User starter;
    private Reign playingReign;


    private Building selectedBuilding;
    private MilitaryUnit selectedUnit; // people nabayad bashe?
    //private Block selectedBlock;
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Reign> reigns = new ArrayList<>();
    int turnsPassed;

    public String showReigns() {
        String output = "REIGNS IN THE GAME:";
        int i = 1;
        for (Reign reign : reigns) {
            output += "\n" + i + "- " + "username: " + reign.getUser().getUserName()
                    + "Nick Name: " + reign.getUser().getNickName();
        }
        return output;
    }

    public Reign getPlayingReign() {
        return playingReign;
    }
    public Reign getReignByNickName(String nickName) {
        for (Reign reign : reigns) {
            if(reign.getUser().getNickName().equals(nickName)) return reign;
        }
        return null;
    }

    public void setSelectedBuilding(Building building) {
        this.selectedBuilding = building;
    }

    public Map getMap() {
        return map;
    }
    public Building getBuilding(int x , int y) {
        return getMap().getBlockByLocation(x, y).getBuilding();
    }
}
