package model;
import Enum.*;

import java.util.ArrayList;

public class TradeItem {

    Reign firstReign;
    Reign secondReign;
    Resource resource;
    private int amount;
    private int price;
    private int itemId;
    private static int numOfTrades = 0;
    private String message;
    private boolean isADonation = false;
    private boolean isGlobalRequest = false;
    private boolean isChecked = false;
    private boolean accepted = false;
    private static final ArrayList<TradeItem> tradeList = new ArrayList<>();

    public TradeItem(Reign firstReign, Reign secondReign, Resource resource, int amount, int price, String message) {
        this.firstReign = firstReign;
        this.secondReign = secondReign;
        this.resource = resource;
        this.amount = amount;
        this.price = price;
        this.message = message;
        if(secondReign == null) isGlobalRequest = true;
        if(price == 0) isADonation = true;
        numOfTrades ++;
        this.itemId = numOfTrades;
    }

    public int getItemId() {
        return itemId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public static String showTradeList() {
        String output = "Global: ";
        for (TradeItem tradeItem : getTradeList()) {
            output += "\nitemId: " + tradeItem.itemId + " first reign: " + tradeItem.getFirstReign().getNickName() + " second reign: " + tradeItem.secondReign
                    + "resource: " + tradeItem.getResource() + " amount: " + tradeItem.getAmount() + " for the price : " + tradeItem.getPrice();
        }
        return output;
    }

    public static TradeItem getTradeItemById(int id) {
        for (TradeItem tradeItem : tradeList) {
            if(tradeItem.itemId == id) return tradeItem;
        }
        return null;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public Resource getResource() {
        return resource;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public boolean isADonation() {
        return isADonation;
    }

    public Reign getFirstReign() {
        return firstReign;
    }

    public Reign getSecondReign() {
        return secondReign;
    }
    public static ArrayList<TradeItem> getTradeList() {
        return tradeList;
    }

    public void setSecondReign(Reign secondReign) {
        this.secondReign = secondReign;
    }
}
