package view;

import controller.*;
import Enum.*;

import java.util.concurrent.ConcurrentMap;

public class GameMenu extends Menu{
    private final GameController gameController;

    public GameMenu(GameController gameController) {
        this.gameController = gameController;
    }

    public void run() {
        while (true) {
            input = scanner.nextLine();
            if(input.matches("map menu")) {
                EditAndShowMapMenu menu = new EditAndShowMapMenu(new MapController(gameController.getMap() , true , gameController.getPlayingReign()));
                menu.run();
            } else if(input.matches("Reign menu")) {
                ReignMenu reignMenu = new ReignMenu(new ReignController(gameController.getGame())); // todo constructor
                reignMenu.run();
            } else if(input.matches("trade menu")) {
                TradeMenu tradeMenu = new TradeMenu(new TradeController(gameController.getGame()));
                tradeMenu.run();
            } else if(input.matches("shop menu")) {
                ShopMenu shopMenu = new ShopMenu(new ShopController(gameController.getGame()));
                shopMenu.run();
            } else if((matcher = getMatcher(input , Commands.SELECT_BUILDING.regex)) != null) {
                String result = gameController.selectBuilding(Integer.parseInt(matcher.group("x")) , Integer.parseInt(matcher.group("y")));
                System.out.println(result);
                if(result.equals("select building successful")) {
                    BuildingMenu buildingMenu = new BuildingMenu(new BuildingController(gameController.getGame()));
                    buildingMenu.run();
                }
            } else if((matcher = getRealMatcher(input , Commands.DROP_BUILDING, Commands.X, Commands.Y, Commands.TYPE)) != null) {
                System.out.println(gameController.dropBuilding(matcher));
            }
        }
    }
}
