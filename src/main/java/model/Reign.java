package model;

import java.util.ArrayList;
import java.util.HashMap;
import Enum.*;
import model.people.*;

public class Reign {


    private String nickName;
    private int gold;
    private User user;

    private int population;
    private int unemployedPopulation;
    private int popularity;
    private int taxRate;
    private int foodRate;
    private int fearRate;

    private final HashMap<Resource, Integer> resources = new HashMap<>();
    private final HashMap<Resource ,Integer> resourceCapacity = new HashMap<>();
    private ArrayList<MilitaryUnit> militaryUnits = new ArrayList<>();

    private ArrayList<TradeItem> tradeHistory = new ArrayList<>();
    private ArrayList<TradeItem> notification = new ArrayList<>();
    private ArrayList<TradeItem> requestsFromMe = new ArrayList<>();
    private ArrayList<TradeItem> RequestsFromOthers = new ArrayList<>();

    public Reign(User user) {
        this.user = user;
        this.nickName = user.getNickName();
        initializeResources();
    }

    public void initializeResources(){

    }

    public void addToResource(Resource resource, int number) {

    }
    public void removeFromResources(Resource resource, int number) {

    }
    public int getResourceAmount(Resource resource) {
        return resources.get(resource);
    }
    public int getResourceCapacity(Resource resource) {
        return resourceCapacity.get(resource);
    }

    public void changeResourceCapacity(Resource resource , int amount) {
        int former = resourceCapacity.get(resource);
        resourceCapacity.replace(resource, former + amount);
    }

    public String showTradeList() {
        String output = "requests from you:";
        for (TradeItem tradeItem : requestsFromMe) {
            output += "\n" + ""; //todo complete this
        }
        return output;
    }
    public String showNotification(boolean wasADonation) {
        String output = "";
        for (TradeItem tradeItem : notification) {
            if(tradeItem.isADonation() != wasADonation) continue;
            output += "\nfrom: " + tradeItem.firstReign + " to: " + tradeItem.secondReign;
            //todo complete output;
        }
        return output;
    }

    public void clearNotification() {
        notification.clear();
    }
    public ArrayList<TradeItem> getTradeHistory() {
        return tradeHistory;
    }

    public ArrayList<TradeItem> getNotification() {
        return notification;
    }

    public ArrayList<TradeItem> getRequestsFromMe() {
        return requestsFromMe;
    }

    public ArrayList<TradeItem> getRequestsFromOthers() {
        return RequestsFromOthers;
    }
    public void changeResourceAmount(Resource resource, int amount) {
        int former = resources.get(resource);
        resources.replace(resource , former + amount);
    }
    public String getHistoryOfTrades(boolean wasADonation) {
        String output = "";
        for (TradeItem tradeItem : tradeHistory) {
            if(wasADonation != tradeItem.isADonation()) continue;
            output += "\nfrom " + tradeItem.firstReign.getUser().getNickName()
                    + " to: " + tradeItem.secondReign.getUser().getNickName()
                    ; //todo complete this
        }
        return output;
    }

    public String getNickName() {
        return nickName;
    }

    public int getGold() {
        return gold;
    }

    public void spendGold(int amount) {
        gold -= amount;
    }

    public void earnGold(int amount) {
        gold += amount;
    }
    public User getUser() {
        return user;
    }

    public int getPopulation() {
        return population;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public int getFearRate() {
        return fearRate;
    }
    public void changePopulation(int change) {
        this.population += change;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public int getUnemployedPopulation() {
        return unemployedPopulation;
    }

    public void changeUnemployedPopulation(int change) {
        this.unemployedPopulation = change;
    }
}
