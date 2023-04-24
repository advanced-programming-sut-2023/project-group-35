
import controller.UserController;
import view.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        RegisterAndLoginMenu menu = new RegisterAndLoginMenu(new UserController());
        menu.run();
        //menu.run();
    }
}