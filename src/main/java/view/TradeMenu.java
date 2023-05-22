package view;

import controller.TradeController;
import Enum.*;

public class TradeMenu extends Menu{
    TradeController tradeController;

    public TradeMenu(TradeController tradeController) {
        this.tradeController = tradeController;
    }

    public void run() {
        System.out.println("You are in the trade menu now!");
        System.out.println(tradeController.notification());
        while(true) {
            input = scanner.nextLine();
            if((matcher = getRealMatcher(input,Commands.ADD_REQUEST,Commands.AMOUNT,Commands.PRICE,Commands.TYPE,
                    Commands.MESSAGE)) != null) {
                System.out.println(tradeController.addRequest(matcher));
            } else if(input.matches("show list of Reigns")){
                System.out.println(tradeController.showMembers());
                if(tradeController.chooseSecondReign().equals("back")) continue;
                runChosenUser();
                tradeController.deleteSecondReign();
            } else if(input.matches("show trade list")) {
                System.out.println(tradeController.showTradeList());
            } else if(input.matches("show my requests from others")) {
                System.out.println(tradeController.showMyRequestsFromOthers());
            } else if((matcher = getRealMatcher(input,Commands.DELETE_TRADE,Commands.ID)) != null) {
                System.out.println(tradeController.deleteTrade(matcher));
            } else if((matcher = getRealMatcher(input,Commands.ACCEPT_REQUEST,Commands.ID,Commands.MESSAGE)) != null) {
                System.out.println(tradeController.acceptTrade(matcher));
            } else if(input.matches("show trade history")) {
                System.out.println(tradeController.showTradeHistory());
            } else if(input.matches("back")) {
                System.out.println("exiting trade menu...");
                tradeController.clearNotification();
                return;
            } else if(input.matches("\\s*show\\s+menu\\s*")) {
                System.out.println("you are in the trade menu!");
            }
            else System.out.println(ResponseToUser.INVALID_COMMAND);
        }
    }

    public static String getReignFromUser() {
        System.out.println("enter Regin's nick name to trade with them or print 'back'");
        String str = scanner.nextLine();
        return str;
    }
    public void runChosenUser() {
        while (true) {
            input = scanner.nextLine();
            if((matcher = getRealMatcher(input , Commands.ADD_REQUEST, Commands.TYPE,Commands.AMOUNT,Commands.PRICE,Commands.MESSAGE)) != null) {
                System.out.println(tradeController.addRequest(matcher));
            } else if((matcher = getRealMatcher(input, Commands.DONATION,Commands.TYPE,Commands.AMOUNT,Commands.MESSAGE)) != null) {
                System.out.println(tradeController.donate(matcher));
            }
        }
    }
    public static void nickNameNotFound() {
        System.out.println("nick name not found, please try again");
    }
}
