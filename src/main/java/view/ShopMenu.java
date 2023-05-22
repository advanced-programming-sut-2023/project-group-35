package view;

import controller.ShopController;
import Enum.*;
public class ShopMenu extends Menu{
    ShopController shopController;

    public ShopMenu(ShopController shopController) {
        this.shopController = shopController;
    }

    public void run() {
        System.out.println("You are in the shop menu now!");
        while (true) {
            input = scanner.nextLine();
            if(input.matches("show price list")) {
                System.out.println(shopController.showPriceList());
            } else if((matcher = getRealMatcher(input,Commands.PURCHASE,Commands.AMOUNT,Commands.ITEM)) != null) {
                System.out.println(shopController.purchase(matcher));
            } else if((matcher = getRealMatcher(input,Commands.SELL,Commands.AMOUNT,Commands.ITEM)) != null) {
                System.out.println(shopController.sell(matcher));
            } else if((input.matches(Commands.BACK.regex))){
                System.out.println("exiting shop menu...");
                return;
            } else if(input.matches("\\s*show\\s+menu\\s*")){
                System.out.println("you are in the shop menu right now!");
            }
            else System.out.println(ResponseToUser.INVALID_COMMAND);
        }
    }
}
