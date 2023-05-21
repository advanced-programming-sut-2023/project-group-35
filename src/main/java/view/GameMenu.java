package view;

import controller.*;
import Enum.*;
import model.Reign;

public class GameMenu extends Menu{
    private final GameController gameController;

    public GameMenu(GameController gameController) {
        this.gameController = gameController;
    }

    public void run() {
        System.out.println("welcome to another game of crusaders!\n You are in the game menu now!\nwrite down your command!");
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
            } else if((matcher = getRealMatcher(input , Commands.SELECT_BUILDING, Commands.X,Commands.Y)) != null) {
                String result = gameController.selectBuilding(matcher);
                System.out.println(result);
                if(result.equals("select building successful")) {
                    BuildingMenu buildingMenu = new BuildingMenu(new BuildingController(gameController.getGame()));
                    buildingMenu.run();
                }
            } else if((matcher = getRealMatcher(input , Commands.DROP_BUILDING, Commands.X, Commands.Y, Commands.TYPE)) != null) {
                System.out.println(gameController.dropBuilding(matcher));
            } else if((matcher = getRealMatcher(input, Commands.SELECT_UNIT, Commands.X, Commands.Y)) != null) {
                result = gameController.selectUnit(matcher);
                if(result.equals("select units successful!")) {
                    UnitSelectMenu menu = new UnitSelectMenu(gameController.getUnitController());
                    menu.run();
                }
            } else if(input.matches("\\s*next\\s+turn\\s*")) {
                if(isUserSureToFinishTurn()) {
                    result = gameController.nextReign();
                    if(result.equals("endGame")) System.out.println(gameController.endGame());
                    else System.out.println(result);
                }

            } else if(input.matches(Commands.SHOW_TURNS_PASSED.regex)) {
                System.out.println(gameController.showTurnsPassed());
            } else if(input.matches("\\s*quit\\s+game\\s*")) {
                if(isUserSureToQuitGame()){
                    System.out.println(gameController.quitGame(gameController.getPlayingReign()));
                }
            } else System.out.println(ResponseToUser.INVALID_COMMAND);
        }
    }

    public static Integer askUserTheUnitToSelect(String units) {
        System.out.println("you have more than one unit in this block!");
        System.out.println("which one do you want to select?");
        System.out.println(units);
        return scanner.nextInt();
    }
    public boolean isUserSureToFinishTurn() {
        while(true) {
            System.out.println("are you sure you want to finish your turn?");
            input = scanner.nextLine();
            if(input.matches("\\s*yes\\s*")) return true;
            if(input.matches("\\s*no\\s*")) return false;
            System.out.println("please try again");
        }
    }
    public boolean isUserSureToQuitGame() {
        while (true) {
            System.out.println("are you sure you want to quit the game?");
            System.out.println("remember if you quit the game you don't get any scores");
            input = scanner.nextLine();
            if(input.matches("\\s*yes\\s*")) return true;
            if(input.matches("\\s*no\\s*")) return false;
            System.out.println("I couldn't understand, please try again.");
        }
    }

    public static void announceDeadReign(Reign reign, int score) {
        System.out.println("reign " + reign.getNickName() + "is now dead and out of the game!");
        System.out.println("they could collect " + score + "scores!");
    }
}
