import controller.UserController;
import view.*;
public class Main {
    public static void main(String[] args) {
        RegisterAndLoginMenu menu = new RegisterAndLoginMenu(new UserController());
        menu.run();
    }
}