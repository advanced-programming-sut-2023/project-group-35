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
        while(true) {
            this.input = scanner.nextLine();
            if(input.matches(Commands.MAP_MENU.regex)) {
                if(mapQuestion().equals("notChosen")) continue;
                EditAndShowMapMenu mapMenu = new EditAndShowMapMenu(new MapController(loggedInUser.getMap(), false, null));
                mapMenu.run();
            } else if(input.matches(Commands.START_GAME.regex)) {
                StartGameMenu gameMenu = new StartGameMenu(loggedInUser);
                gameMenu.run();
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
            } else{
                System.out.println("invalid commend!");
            }
        }
    }

    public String mapQuestion() {
        if(loggedInUser.getMap() == null){
            System.out.println("you don't have a map.\ntype yes if you want to choose one right now");
            if((input = scanner.nextLine()).matches("yes")) userController.chooseMap();
            else return "notChosen";
        }
        else {
            System.out.println("you already have a map. if you want to build a new map type new map");
            if((input = scanner.nextLine()).matches("new map")) userController.chooseMap();
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
