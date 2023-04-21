package main.java.view;

import main.java.Enum.*;
import main.java.model.*;

public class MainMenu extends Menu{

    private User loggedInUser;
    public void run() {
        while(true) {
            this.input = scanner.nextLine();
            if((matcher = Menu.getMatcher(input, Commands.MAP_MENU.regex)) != null) {
                EditAndShowMapMenu mapMenu = new EditAndShowMapMenu()
            }
        }

    }
}
