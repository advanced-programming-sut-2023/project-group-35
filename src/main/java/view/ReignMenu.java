package view;

import controller.ReignController;
import controller.UserController;
import model.Game;
import model.Reign;
import model.User;

import java.util.regex.Matcher;
import Enum.*;
public class ReignMenu extends Menu{

    private ReignController reignController;
    public ReignMenu(ReignController reignController) {
        this.reignController = reignController;
    }

    public void run() {
        System.out.println("You are in the reign menu now!");
        while (true) {
            input = scanner.nextLine();
            if ((matcher = Commands.getMatcher(input, Commands.SHOW_POPULARITY_FACTORS)) != null) {
                System.out.println(reignController.showPopularityFactors());
            } else if((matcher = Commands.getMatcher(input, Commands.SHOW_POPULARITY)) != null) {
                System.out.println(reignController.showPopularity());
            } else if ((matcher = Commands.getMatcher(input, Commands.SHOW_FOOD_LIST)) != null) {
                System.out.println(reignController.showFoodList());
            }else if ((matcher = Commands.getMatcher(input, Commands.FOOD_RATE_SHOW)) != null) {
                System.out.println(reignController.showFoodRate());
            }else if ((matcher = Commands.getMatcher(input, Commands.TAX_RATE_SHOW)) != null) {
                System.out.println(reignController.showTaxRate());
            } else if ((matcher = getRealMatcher(input,Commands.FOOD_RATE,Commands.RATE)) != null) {
                System.out.println(reignController.setFoodRate(matcher));
            }else if ((matcher = Commands.getMatcher(input, Commands.FEAR_RATE_SHOW)) != null) {
                System.out.println(reignController.showFearRate());
            } else if ((matcher = getRealMatcher(input, Commands.TAX_RATE,Commands.RATE)) != null) {
                System.out.println(reignController.setTaxRate(matcher));
            }else if ((matcher = getRealMatcher(input, Commands.TAX_RATE,Commands.RATE)) != null) {
                System.out.println(reignController.setFearRate(matcher));
            }else if ((matcher = Commands.getMatcher(input, Commands.BACK)) != null) {
                System.out.println("exiting reign menu");
                return;
            }else {
                System.out.println(ResponseToUser.INVALID_COMMAND);
            }

        }
    }
}
