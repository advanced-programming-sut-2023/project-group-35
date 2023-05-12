
import controller.MapController;
import controller.UserController;
import model.*;
import view.*;

import java.awt.color.CMMException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Map map = new Map(null,200);
        MapController mapController = new MapController(map,false,null);
        EditAndShowMapMenu editAndShowMapMenu = new EditAndShowMapMenu(mapController);
        editAndShowMapMenu.run();
        
        /*RegisterAndLoginMenu menu = new RegisterAndLoginMenu(new UserController());
        menu.run();*/
        //menu.run();
    }
}