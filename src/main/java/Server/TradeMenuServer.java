package Server;

import controller.UserController;
import model.Game;
import model.Reign;
import model.TradeItem;
import Enum.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;

public class TradeMenuServer {
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    Game game;
    private Reign secondReign;
    Reign playingReign;
    TradeMenuServer(DataInputStream dataInputStream, DataOutputStream dataOutputStream, Game game){
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.game = game;
        playingReign= game.getPlayingReign();
    }
    public void run() throws IOException {
        dataOutputStream.writeUTF("You are in the trade menu now!");
        dataOutputStream.writeUTF(notification());
        String input;
        Matcher matcher;
        while(true) {
            input = dataInputStream.readUTF();
            if((matcher = Commands.getMatcher(input,Commands.ACCEPT_TRADE)) != null) {
                System.out.println(addRequest(matcher));
            } else if(input.matches("show list of Reigns")){
                System.out.println(showMembers());
                if(chooseSecondReign().equals("back")) continue;
                runChosenUser();
                deleteSecondReign();
            } else if(input.matches("show trade list")) {
                System.out.println(showTradeList());
            } else if(input.matches("show my requests from others")) {
                System.out.println(showMyRequestsFromOthers());
            }
            else if((matcher = Commands.getMatcher(input,Commands.ACCEPT_REQUEST)) != null) {
                System.out.println(acceptTrade(matcher));
            } else if(input.matches("show trade history")) {
                System.out.println(showTradeHistory());
            } else if(input.matches("back")) {
                System.out.println("exiting trade menu...");
                clearNotification();
                return;
            } else if(input.matches("\\s*show\\s+menu\\s*")) {
                System.out.println("you are in the trade menu!");
            }
            else System.out.println(ResponseToUser.INVALID_COMMAND);
        }
    }
    public void runChosenUser() throws IOException {
        String input;
        Matcher matcher;;
        while (true) {
            input = dataInputStream.readUTF();
            if((matcher = Commands.getMatcher(input , Commands.ADD_REQUEST)) != null) {
                dataOutputStream.writeUTF(addRequest(matcher));
            }
        }
    }
    public static void nickNameNotFound() {
        System.out.println("nick name not found, please try again");
    }

    public String showTradeList () {
        return playingReign.showTradeList()  + "\n" + TradeItem.showTradeList();
    }
    public String showMyRequestsFromOthers () {
        String output = "requests form other reigns: ";
        for (TradeItem request : playingReign.getRequestsFromOthers()) {
            output += "\n" + "product: " + request.getResource().name().toLowerCase()
                    + "amount: " + request.getAmount()
                    + "price" + request.getPrice();
            if(request.getSecondReign() != null) output += "from " + request.getSecondReign().getNickName();
        }
        return output;
    }
    public String showMembers() {
        return game.showReigns();
    }
    public String chooseSecondReign() {
        while(true) {
            String entry = "";
            for(Reign reign:game.getReigns()){
                entry += "\n"+reign.getNickName();
            }
            if(entry.matches("back")) return "back";
            secondReign = game.getReignByNickName(entry);
            if(secondReign == null) nickNameNotFound();
            else return "found";
        }
    }

    public String addRequest(Matcher matcher) {
        int amount = Integer.parseInt(matcher.group("amount"));
        int price = Integer.parseInt(matcher.group("price"));
        if(price <= 0) return "you cant add a request with no price";
        if(!hasEnoughBalance(price)) return "you don't have enough balance";
        Resource resource = Resource.getResourceByName(UserController.checkForQuotation(matcher.group("type")));
        if(resource == null) return "you have entered the wrong resource";
        TradeItem tradeItem = new TradeItem(playingReign, secondReign, resource, amount , price , UserController.checkForQuotation(matcher.group("message")));
        playingReign.spendGold(price);
        TradeItem.getTradeList().add(0 , tradeItem);
        playingReign.getRequestsFromOthers().add(0 , tradeItem);
        if(secondReign != null) {
            secondReign.getNotification().add(0 , tradeItem);
            secondReign.getRequestsFromMe().add(0 , tradeItem);
        }
        return "add request successful";
    }


    public String acceptTrade(Matcher matcher) {
        int id = Integer.parseInt(matcher.group("id"));
        TradeItem tradeItem = TradeItem.getTradeItemById(id);
        if(tradeItem == null) return "this item does not exist in the list";
        secondReign = tradeItem.getFirstReign();
        if(tradeItem.getSecondReign() != null && tradeItem.getSecondReign().equals(playingReign)) return "this request is not from you";
        if(playingReign.getResourceAmount(tradeItem.getResource()) < tradeItem.getAmount())
            return "you don't have enough resource to give" + secondReign.getNickName();
        tradeItem.setMessage(UserController.checkForQuotation(matcher.group("message")));
        tradeItem.setSecondReign(playingReign);
        playingReign.earnGold(tradeItem.getPrice());
        playingReign.changeResourceAmount(tradeItem.getResource(), -tradeItem.getAmount());
        secondReign.changeResourceAmount(tradeItem.getResource(), tradeItem.getAmount());
        playingReign.getRequestsFromMe().remove(tradeItem);
        secondReign.getRequestsFromOthers().remove(tradeItem);
        playingReign.getTradeHistory().add(tradeItem);
        secondReign.getTradeHistory().add(tradeItem);
        TradeItem.getTradeList().remove(tradeItem);
        secondReign.getNotification().add(tradeItem);
        secondReign = null;
        return "the trade was accepted successfully";
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
    public String donate(Matcher matcher) {
        int amount = Integer.parseInt(matcher.group("amount"));
        if (amount <= 0) return "you can't donate resources with zero amount";
        Resource resource = Resource.getResourceByName(matcher.group("type"));
        if(resource == null) return "you have entered the wrong resource";
        if(playingReign.getResourceAmount(resource) < amount) return "you don't have enough resources";
        TradeItem tradeItem = new TradeItem(playingReign, secondReign, resource, amount , 0 , UserController.checkForQuotation(matcher.group("message")));
        secondReign.getNotification().add(0 , tradeItem);
        playingReign.getTradeHistory().add(0 , tradeItem);
        secondReign.getTradeHistory().add(0 , tradeItem);
        return "donation successful";
    }
    public String showTradeHistory() {
        return "Donations: \n" + playingReign.getHistoryOfTrades()
                + "\n Trades: \n" + playingReign.getHistoryOfTrades();
    }
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
