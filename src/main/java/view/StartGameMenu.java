package view;

import controller.GameController;
import controller.UnitController;
import javafx.stage.Stage;
import model.*;
import Enum.*;

import java.util.ArrayList;

public class StartGameMenu extends Menu{
    //private User playingUser;
    private int leftUsersToAdd;
    private final GameController gameController;

    public StartGameMenu(User starter) {
        //this.playingUser = starter;
        leftUsersToAdd = starter.getMap().getNumberOfBases() - 1;
        gameController = new GameController(new Game(starter, starter.getMap()));
        for (Block block : starter.getMap().getBlocks()) {
            if(block.getStructures() == null) block.setStructures(new ArrayList<>());
            if(block.getMilitaryUnits() == null) block.setMilitaryUnits(new ArrayList<>());
        }
        gameController.setUnitController();
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

//    public void run() {
//        System.out.println("YOU ARE IN THE START GAME MENU");
//        System.out.println("print back to go back to main menu");
//        while (true) {
//            printLeftUsersToEnter();
//            input = scanner.nextLine();
//            if(input.matches(Commands.BACK.regex)) {
//                if(isUserSureToExit()) {
//                    System.out.println("exiting start game menu...");
//                    return;
//                }
//            } else if((matcher = getRealMatcher(input,Commands.ADD_USER,Commands.USERNAME)) != null) {
//                String result = gameController.addUser(matcher);
//                System.out.println(result);
//                if(result.equals("user added successfully")) leftUsersToAdd--;
//                if(leftUsersToAdd == 0) {
//                    System.out.println("great!\nlet's start the game!");
//                    GameMenu gameMenu = new GameMenu(gameController);
//                    gameMenu.run();
//                }
//            } else if(input.matches("\\s*show\\s+menu\\s*")) {
//                System.out.println("you are in the start game menu!");
//            }
//            else System.out.println(ResponseToUser.INVALID_COMMAND);
//        }
//    }
    public void printLeftUsersToEnter() {
        System.out.println("there are " + leftUsersToAdd + "users left to add");
        System.out.println("print their username to add them");
    }
    public boolean isUserSureToExit() {
        System.out.println("are you sure you want to exit start game menu?");
        System.out.println("print yes to exit");
        input = scanner.nextLine();
        if(input.matches("\\s*yes\\s*")) return true;
        return false;
    }
}
