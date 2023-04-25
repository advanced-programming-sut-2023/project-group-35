package view;

import controller.ReignController;
import controller.UserController;
import model.User;

import java.util.regex.Matcher;

public class ReignMenu extends Menu{
    private ReignController reignController;

    public ReignMenu(ReignController reignController) {
        this.reignController = reignController;
    }

    public void run() {
    }
}
