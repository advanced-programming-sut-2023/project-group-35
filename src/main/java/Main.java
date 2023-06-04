//
//import controller.MapController;
//import controller.UserController;
//import model.*;
//import view.*;
//
//import java.awt.color.CMMException;
//import java.io.File;
//import java.io.IOException;
//import java.security.NoSuchAlgorithmException;
//
//public class Main {
//    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
//        File tempFile = new File("loggedIn.txt");
//        boolean exists = tempFile.exists();
//        Menu menu = new RegisterAndLoginMenu(new UserController());
//        if(exists){
//            String nameOfLoggedIn = tempFile.toString();
//            UserController userController = new UserController();
//            userController.setLoggedInUser(User.getUserByUsername(nameOfLoggedIn));
//            menu = new MainMenu(userController);
//        }
//
//        menu.run();
//        //menu.run();
//    }
//}
/* user login -u Amir -p PAsss1!@
        start a new game
          add -u Ali
          add -u Reza
          add -u Abolfazl
           next turn
           yes
           drop building -x 24 -y 24 -t "mercenary camp"
           select building -x 24 -y 24
           create unit -t slinger -c 5
           back
           select unit -x 24 -y 24
           patrol unit -x1 24 -x2 27 -y1 24 -y2 27
            */