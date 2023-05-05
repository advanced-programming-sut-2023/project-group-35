package view;

import controller.ReignController;
import controller.UserController;
import model.Game;
import model.User;

import java.util.regex.Matcher;
import Enum.*;
public class ReignMenu extends Menu{

    private ReignController reignController;
    public ReignMenu(ReignController reignController) {
        this.reignController = reignController;
    }

    public void run() {
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
            } else if ((matcher = Commands.getMatcher(input, Commands.FOOD_RATE)) != null) {
                System.out.println(reignController.setFoodRate(Integer.parseInt(matcher.group("rate"))));
            }else if ((matcher = Commands.getMatcher(input, Commands.FEAR_RATE_SHOW)) != null) {
                System.out.println(reignController.showFearRate());
            } else if ((matcher = Commands.getMatcher(input, Commands.TAX_RATE)) != null) {
                System.out.println(reignController.setTaxRate(Integer.parseInt(matcher.group("rate"))));
            }else if ((matcher = Commands.getMatcher(input, Commands.FEAR_RATE)) != null) {
                System.out.println(reignController.setFearRate(Integer.parseInt(matcher.group("rate"))));
            }else if ((matcher = Commands.getMatcher(input, Commands.BACK)) != null) {
                return;
            }else {
                System.out.println("Invalid commend!");
            }

        }
    }
}
