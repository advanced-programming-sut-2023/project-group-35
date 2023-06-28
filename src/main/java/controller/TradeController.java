package controller;

import model.*;
import Enum.*;
import view.TradeMenu;

import java.util.ArrayList;
import java.util.regex.*;

public class TradeController extends GameController{
    private Reign secondReign;

    public TradeController(Game game) {
        super(game);
    }

    public Reign getSecondReign() {
        return secondReign;
    }

    public String showTradeList () {
        return playingReign.showTradeList()  + "\n" + TradeItem.showTradeList();
    }
    public ArrayList<String> showMyRequestsFromOthers () {
        ArrayList<String> requests = new ArrayList<>();
        String output = "";
        for (TradeItem request : playingReign.getRequestsFromOthers()) {
            output =  "product: " + request.getResource().name().toLowerCase()
                    + "amount: " + request.getAmount()
                    + "price" + request.getPrice();
            if(request.getSecondReign() != null) output += "from " + request.getSecondReign().getNickName();
            output +="checked status:"+request.isChecked();
            if(request.isChecked())
                    output += "is it accepted: "+ request.isAccepted();
            requests.add(output);
        }
        return requests;
    }
    public ArrayList<String> showRequestsFromMe () {
        ArrayList<String> requests = new ArrayList<>();
        String output = "";
        for (TradeItem request : playingReign.getRequestsFromMe()) {
            output =  "product: " + request.getResource().name().toLowerCase()
                    + "amount: " + request.getAmount()
                    + "price" + request.getPrice();
            if(request.getSecondReign() != null) output += "from " + request.getSecondReign().getNickName();
            output +="checked status:"+request.isChecked();
            if(request.isChecked())
                output += "is it accepted: "+ request.isAccepted();
            requests.add(output);
        }
        return requests;
    }
    public String turnIntoString(TradeItem tradeItem) {
        String output = "";
            output =  "product: " + tradeItem.getResource().name().toLowerCase()
                    + "amount: " + tradeItem.getAmount()
                    + "price" + tradeItem.getPrice();
            if(tradeItem.getSecondReign() != null) output += "from " + tradeItem.getSecondReign().getNickName();
            output +="checked status:"+tradeItem.isChecked();
            if(tradeItem.isChecked())
                output += "is it accepted: "+ tradeItem.isAccepted();
            return output;
        }

    public String showMembers() {
        return game.showReigns();
    }
    public String chooseSecondReign(String nickname) {
            secondReign = game.getReignByNickName(nickname);
            if(secondReign == null) return "Wrong";
            else return "found";
        }


    public String addRequest(int amount,Resource resource,String message) {
        int price = resource.sellPrice-1;
        if(price <= 0) return "you cant add a request with no price";
        if(!hasEnoughBalance(price)) return "you don't have enough balance";
        if(resource == null) return "you have entered the wrong resource";
        TradeItem tradeItem = new TradeItem(playingReign, secondReign, resource, amount , price , message);
        playingReign.spendGold(price);
        TradeItem.getTradeList().add(0 , tradeItem);
        playingReign.getRequestsFromOthers().add(0 , tradeItem);
        if(secondReign != null) {
            secondReign.getNotification().add(0 , tradeItem);
            secondReign.getRequestsFromMe().add(0 , tradeItem);
        }
        return "add request successful";
    }



    public String acceptTrade(int id,String message) {
        TradeItem tradeItem = TradeItem.getTradeItemById(id);
        if(tradeItem == null) return "this item does not exist in the list";
        secondReign = tradeItem.getFirstReign();
        if(tradeItem.getSecondReign() != null && tradeItem.getSecondReign().equals(playingReign)) return "this request is not from you";
        if(playingReign.getResourceAmount(tradeItem.getResource()) < tradeItem.getAmount())
            return "you don't have enough resource to give" + secondReign.getNickName();
        tradeItem.setMessage(message);
        tradeItem.setSecondReign(playingReign);
        playingReign.earnGold(tradeItem.getPrice());
        playingReign.changeResourceAmount(tradeItem.getResource(), -tradeItem.getAmount());
        secondReign.changeResourceAmount(tradeItem.getResource(), tradeItem.getAmount());
        playingReign.getTradeHistory().add(tradeItem);
        secondReign.getTradeHistory().add(tradeItem);
        TradeItem.getTradeList().remove(tradeItem);
        secondReign.getNotification().add(tradeItem);
        tradeItem.setAccepted(true);
        tradeItem.setChecked(true);
        return "the trade was accepted successfully";
    }
    public String rejectTrade(int id,String message){
        TradeItem tradeItem = TradeItem.getTradeItemById(id);
        if(tradeItem == null) return "this item does not exist in the list";
        secondReign = tradeItem.getFirstReign();
        if(tradeItem.getSecondReign() != null && tradeItem.getSecondReign().equals(playingReign)) return "this request is not from you";
        tradeItem.setMessage(message);
        tradeItem.setSecondReign(playingReign);
        playingReign.getTradeHistory().add(tradeItem);
        secondReign.getTradeHistory().add(tradeItem);
        TradeItem.getTradeList().remove(tradeItem);
        secondReign.getNotification().add(tradeItem);
        tradeItem.setAccepted(false);
        tradeItem.setChecked(true);
        return "the trade was rejected successfully";
    }
    public String deleteTrade(Matcher matcher) {
        int id = Integer.parseInt(matcher.group("id"));
        TradeItem tradeItem = TradeItem.getTradeItemById(id);
        if(tradeItem == null) return "this item does not exist";
        if(!tradeItem.getFirstReign().equals(playingReign)) return "you did not add this request";
        TradeItem.getTradeList().remove(tradeItem);
        playingReign.getRequestsFromOthers().remove(tradeItem);
        secondReign = tradeItem.getSecondReign();
        if(secondReign != null) {
            secondReign.getNotification().remove(tradeItem);
            secondReign.getRequestsFromMe().remove(tradeItem);
        }
        playingReign.earnGold(tradeItem.getPrice());
        return "your request was successfully removed";
    }
    public String donate(int amount,Resource resource,String message) {
        if (amount <= 0) return "you can't donate resources with zero amount";
        if(resource == null) return "you have entered the wrong resource";
        if(playingReign.getResourceAmount(resource) < amount) return "you don't have enough resources";
        TradeItem tradeItem = new TradeItem(playingReign, secondReign, resource, amount , 0 , message);
        secondReign.getNotification().add(0 , tradeItem);
        playingReign.getTradeHistory().add(0 , tradeItem);
        secondReign.getTradeHistory().add(0 , tradeItem);
        return "donation successful";
    }
    /*public String showTradeHistory() {
        return "Donations: \n" + playingReign.getHistoryOfTrades(true)
                + "\n Trades: \n" + playingReign.getHistoryOfTrades(false);
    }*/
    public String notification() {
        return "recent trades for you or donations: \n" + playingReign.showNotification(true)
                + "\n Trades: \n" + playingReign.showNotification(false);
    }
    public void clearNotification() {
        playingReign.clearNotification();
    }

    public void deleteSecondReign() {
        secondReign = null;
    }

    public boolean hasEnoughBalance(int price) {
        if(price > playingReign.getGold()) return false;
        return true;
    }

}
