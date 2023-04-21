package model;
import Enum.*;

import java.util.ArrayList;

public class TradeItem {
    Resources resources;
    private int amount;
    private int price;
    private String message;
    private final ArrayList<TradeItem> tradeList = new ArrayList<>();

    public TradeItem(Resources resources, int amount, int price, String message) {
        this.resources = resources;
        this.amount = amount;
        this.price = price;
        this.message = message;
    }
}
