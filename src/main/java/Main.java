
import controller.UserController;
import view.*;

import java.awt.color.CMMException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;

import Enum.Commands;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        RegisterAndLoginMenu menu = new RegisterAndLoginMenu(new UserController());
        menu.run();
//        String command = " set texture -y2 -x1 77 -t salam -x2 3 -y1 33 ";
//        //Matcher matcher = Menu.getRealMatcher(command, Commands.CLEAR, Commands.X, Commands.Y);
//        //System.out.println("x group: " + matcher.group("x"));
//        Commands[] a = {Commands.X1, Commands.X2, Commands.Y2, Commands.Y1, Commands.TYPE};
//        //System.out.println(Menu.appendGroups(a));
//        Matcher matcher = Menu.getRealMatcher(command, Commands.SET_AREA_TEXTURE, a);
//        if(matcher == null) {
//            System.out.println("null");
//        }
//        else System.out.println(matcher.group("y2"));


    }
}