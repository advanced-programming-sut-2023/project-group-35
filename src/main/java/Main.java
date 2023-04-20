package main.java;

import main.java.controller.UserController;
import main.java.view.*;
public class Main {
    public static void main(String[] args) {
        RegisterAndLoginMenu menu = new RegisterAndLoginMenu(new UserController());
        //menu.run();
    }
}