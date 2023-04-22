package view;

import controller.ShopController;
import Enum.*;
public class ShopMenu extends Menu{
    ShopController shopController;

    public ShopMenu(ShopController shopController) {
        this.shopController = shopController;
    }

    public void run() {
        while (true) {
            input = scanner.nextLine();
            if(input.matches("show price list")) {
                System.out.println(shopController.showPriceList());
            } else if((matcher = getMatcher(input , Commands.PURCHASE.regex)) != null) {
                System.out.println(shopController.purchase(matcher));
            } else if((matcher = getMatcher(input , Commands.SELL.regex)) != null) {
                System.out.println(shopController.sell(matcher));
            } else if((input.matches(Commands.BACK.regex))) return;
            else System.out.println("invalid command");
        }
    }
}
