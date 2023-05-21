package model;

import java.util.ArrayList;
import java.util.HashMap;
import Enum.*;
import model.buildings.Building;
import model.people.*;

public class
Reign {


    private String nickName;
    private User user;
    private double gold;
    private int population;
    private int unemployedPopulation;
    private int popularity;
    private int taxRate;
    private int currentTaxRate;
    private double foodRate;
    private double currentFoodRate;
    private int fearRate;
    private int foodVariety;

    private final HashMap<Resource, Integer> resources = new HashMap<>();
    private final HashMap<Resource ,Integer> resourceCapacity = new HashMap<>();
    private ArrayList<MilitaryUnit> militaryUnits = new ArrayList<>();

    private ArrayList<Building> buildings = new ArrayList<>(); // is this nessesary?
    private ArrayList<MilitaryUnit> units = new ArrayList<>(); // is this nessesary?

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
        for (Resource value : Resource.values()) {
            this.resources.put(value, 0);
            if(value.getStoredInBuilding() == null) this.resourceCapacity.put(value, 0);
            else if (value.getStoredInBuilding().equals(BuildingType.STOCK_PILE)) this.resourceCapacity.put(value, 1000);
            else resourceCapacity.put(value, 100);
            if(value.equals(Resource.SWORD)) {
                System.out.println("initialized");
                System.out.println(value.getStoredInBuilding());
            }
        }
    }

    public ArrayList<MilitaryUnit> getMilitaryUnits() {
        return militaryUnits;
    }

    public void calculatePopularityRate() {
        popularity += fearRate;
        if(currentTaxRate <= 0) popularity += (currentTaxRate * 2) - 1;
        if(currentTaxRate > 0) popularity -= currentTaxRate * 2;
        popularity += currentFoodRate * 4;
        popularity += foodVariety - 1;
    }
    public void distributeFood(Resource...resources) {
        int i = 0;
        for(Resource resource: resources) {
            if(getResourceAmount(resource) < population * foodRate) continue;
            spendResources(resource, (int) (population * foodRate));
            i++;
        }
        foodVariety = i;
        if(i == 0) currentTaxRate = 0;
    }
    public void getTaxFromPeople() {
        double tax = (Math.abs(taxRate) * 0.2 + 0.4) * population;
        if(taxRate < 0 && gold - tax < 0) {
            currentTaxRate = 0;
            return;
        }
        if(taxRate < 0) gold -= tax;
        if(taxRate > 0) gold += (taxRate * 0.2 + 0.4) * population;
        currentTaxRate = taxRate;
    }

    public void addToResource(Resource resource, int change) {
        int now = resources.get(resource);
        int newAmount = now + change;
        if(now + change > resourceCapacity.get(resource)){
            newAmount = resourceCapacity.get(resource);
        }
        resources.replace(resource, newAmount);
    }
    public void spendResources(Resource resource, int number) {
        resources.replace(resource, resources.get(resource) - number);
    }
    public int getResourceAmount(Resource resource) {
        return resources.get(resource);
    }

    public int getResourceCapacity(Resource resource) {
        System.out.println(resource);
        int x = resourceCapacity.get(resource);
        System.out.println(x);
        return x;
    }

    public void changeResourcesCapacity(BuildingType buildingType , int amount) {

        for (Resource value : Resource.values()) {
            if(value.getStoredInBuilding() == null) continue;
            if(value.getStoredInBuilding().equals(buildingType))
                resourceCapacity.replace(value, resourceCapacity.get(value) + amount);
        }
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

    public double getGold() {
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

    public double getFoodRate() {
        return foodRate;
    }

    public int getFearRate() {
        return fearRate;
    }
    public void changePopulation(int change) {
        this.population += change;
    }

    public void changePopularity(int change) {
        this.popularity += change;
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
    public ArrayList<Building> getBuildings() {
        return buildings;
    }



    public void addBuilding(Building building){buildings.add(building);}

}
