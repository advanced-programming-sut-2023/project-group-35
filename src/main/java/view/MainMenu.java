package view;

import Enum.*;
import controller.MapController;
import controller.UserController;
import model.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class MainMenu extends Menu{
    private UserController userController;
    private User loggedInUser;
    public MainMenu(UserController userController) {
        this.userController = userController;
    }
    public void run() throws NoSuchAlgorithmException, IOException {
        while(true) {
            System.out.println("choose the menu you want to enter");
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
}
