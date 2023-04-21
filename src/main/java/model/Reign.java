package model;

import java.util.ArrayList;
import java.util.HashMap;
import Enum.*;
import model.people.*;

public class Reign {
    private User user;
    private int population;
    private int popularity;
    private int taxRate;
    private int foodRate;
    private int fearRate;

    HashMap<Resources, Integer> resources = new HashMap<>();
    private ArrayList<MilitaryUnit> militaryUnits = new ArrayList<>();

    private ArrayList<TradeItem> tradeHistory = new ArrayList<>();
    private ArrayList<TradeItem> notification = new ArrayList<>();


    public Reign(User user) {
        this.user = user;
        initializeResources();
    }
    public void initializeResources(){

    }
    public void addToResource(Resources resource, int number) {

    }
    public void removeFromResources(Resources resources , int number) {

    }
}
