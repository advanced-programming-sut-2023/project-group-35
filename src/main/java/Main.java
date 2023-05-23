
import controller.MapController;
import controller.UserController;
import model.*;
import view.*;

import java.awt.color.CMMException;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        File tempFile = new File("loggedIn.txt");
        boolean exists = tempFile.exists();
        Menu menu = new RegisterAndLoginMenu(new UserController());
        if(exists){
            String nameOfLoggedIn = tempFile.toString();
            UserController userController = new UserController();
            userController.setLoggedInUser(User.getUserByUsername(nameOfLoggedIn));
            menu = new MainMenu(userController);
        }

        menu.run();
        //menu.run();
    }
} /* user login -u Amir -p PAsss1!@
        start a new game
          add -u Ali
          add -u Reza
          add -u Abolfazl
           enter trade menu
            request -t apple -a 10 -p 20 -m ok
            */