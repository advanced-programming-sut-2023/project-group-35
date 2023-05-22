package view;

import Enum.*;
import controller.MapController;
import controller.UserController;
import model.*;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class MainMenu extends Menu{
    private UserController userController;
    private User loggedInUser;
    public MainMenu(UserController userController) {
        this.userController = userController;
        loggedInUser = userController.getLoggedInUser();
        if(loggedInUser == null)
            System.out.println("null");
    }
    public void run() throws NoSuchAlgorithmException, IOException {
        System.out.println("choose the menu you want to enter");
        System.out.println("map menu");
        System.out.println("start game menu");
        System.out.println("profile menu");
        System.out.println("or print <logout> if you want to logout");
        while(true) {
            this.input = scanner.nextLine();
            if(input.matches(Commands.MAP_MENU.regex)) {
                if(mapQuestion().equals("notChosen")) continue;
                EditAndShowMapMenu mapMenu = new EditAndShowMapMenu(new MapController(loggedInUser.getMap(), false, null));
                mapMenu.run();
            } else if(input.matches(Commands.START_GAME.regex)) {
                if(loggedInUser.getMap() == null)
                    System.out.println("you must have map to create game!");
                else if(loggedInUser.getMap().getNumberOfBases() < 3)
                    System.out.println("your map doesn't have enough bases!\nadd new bases to your map or choose another one from template");
                else {
                    StartGameMenu gameMenu = new StartGameMenu(loggedInUser);
                    gameMenu.run();
                }
            }
            else if(input.matches(Commands.PROFILE_MENU.regex)) {
                ProfileMenu profileMenu = new ProfileMenu(loggedInUser);
                profileMenu.run();
            }
            else if(input.matches(Commands.LOGOUT.regex)){
                if(isUserSureToLogout()){
                    File tempFile = new File("loggedIn.txt");
                    boolean exists = tempFile.exists();
                    if(exists){
                        tempFile.delete();
                    }
                    System.out.println("logout successful!");
                    return;
                }
            } else if(input.matches("\\s*show\\s+menu\\s*")){
                System.out.println("you are in the main menu!");
            }
            else{
                System.out.println("invalid commend!");
            }
        }
    }

    public String mapQuestion() {
        if(loggedInUser.getMap() == null){
            System.out.println("You don't have a map.");
            while (true) {
                System.out.println("type <choose map> to choose a template map,\ntype <create> to make a now map,\n and <exit> to go back to main menu");
                input = scanner.nextLine();
                if (input.matches("\\s*choose\\s+map\\s*")) {userController.chooseMap();
                return "map was chosen!";}
                else if (input.matches("\\s*create\\s*")){ userController.createMap();
                return "map was chosen!";}
                else if (input.matches("\\s*exit\\s*")) return "notChosen";
                else System.out.println("I didn't Understand, please try again");
            }
        }
        else {
            System.out.println("You already have a map");
            while (true) {
                System.out.println("type <new map> to choose another map,\ntype <new map> to create a new map \n otherwise type <continue>");
                input = scanner.nextLine();
                if (input.matches("new map")) userController.createMap();
                else if (input.matches("choose another")) userController.chooseMap();
                else if (input.matches("\\s*continue\\s*")){
                    System.out.println("getting into map menu...");
                    break;
                } else System.out.println("sorry, I did not understand, please try again");
            }
        }
        return "successful";
    }
    public static String getMapFromUser(String mapList) {
        System.out.println("choose one of these template maps");
        System.out.println(mapList);
        return scanner.nextLine();
    }
    public boolean isUserSureToLogout() {
        while (true) {
            System.out.println("Are you sure you want to logout?");
            input = scanner.nextLine();
            if(input.matches("\\s*yes\\s*")) return true;
            if(input.matches("\\s*no\\s*")) return false;
            System.out.println("sorry i did not understand!");
        }
    }

}
