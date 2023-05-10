
import controller.MapController;
import controller.UserController;
import model.*;
import view.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        RegisterAndLoginMenu menu = new RegisterAndLoginMenu(new UserController());
        User admin = new User("amir","Amir111","amiro","amie@email.com","sa","sa",null);
        Map map = new Map(admin,200);
        MapController mapController = new MapController(map,false,null);
        EditAndShowMapMenu mapMenu = new EditAndShowMapMenu( mapController);
        mapMenu.run();
        mapController.saveTheMap(map);
        //menu.run();
    }
}