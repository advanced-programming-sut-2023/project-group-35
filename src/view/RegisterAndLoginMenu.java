package view;


import controller.UserController;

import java.util.regex.Matcher;


public class RegisterAndLoginMenu {
    private UserController userController;

    private String input;
    private Matcher matcher;

    public void run() {
    }

    public RegisterAndLoginMenu(UserController userController){
        this.userController = userController;
    }
    public String getSafetyQuestion() {
        return null;
    }
    public String getRandomPassword() {
        return null;
    }
    public String getRandomSlogan() {
        return null;
    }

}
