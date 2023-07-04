package view;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;

import controller.ReignController;
import controller.ShopController;
import controller.TradeController;
import controller.UserController;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import model.Game;
import model.User;

public class Menu extends Application {
    public static final Scanner scanner = new Scanner(System.in);
    protected String input;
    protected String result;
    protected Matcher matcher;
    protected static Stage stage;
    public UserController userController = new UserController();
    public static Color successGreenColor = Color.rgb(30,103, 45);
    public static Color failRedColor = Color.rgb(145, 38, 32);
    public static int gameButtonHeight = 30;
    public static int gameButtonWidth = 80;
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        UserController.loadTheData();
        Menu.stage = stage;
        stage.setFullScreen(true);
        String fromFile = null;
        try {
            fromFile = Files.readString(Paths.get("loggedIn.txt"));
            System.out.println("loggedIn: " + fromFile);
        }
        catch (Exception exp){}

        User user;
        if ((user = User.getUserByUsername(fromFile)) != null) {
            UserController userController = new UserController();
            userController.setLoggedInUser(user);
            UserController.loggedInUser = user;
            startMainMenu(userController);
        } else {
            new LoginMenu().start(Menu.stage);
        }
    }
    public static void startMainMenu(UserController userController) throws Exception {
        MainMenu mainMenu = new MainMenu();
        mainMenu.setUserController(userController);
        mainMenu.start(Menu.stage);
    }
    public static void startMainMenu(User loggedInUser) throws Exception {
        MainMenu mainMenu = new MainMenu();
        UserController userController1 = new UserController();
        userController1.setLoggedInUser(UserController.loggedInUser);
        mainMenu.setUserController(userController1);
        mainMenu.start(Menu.stage);
    }
    public static void startProfileMenu(User loggedInUser) throws Exception {
        ProfileMenu profileMenu = new ProfileMenu();
        UserController userController1 = new UserController();
        userController1.setLoggedInUser(loggedInUser);
        profileMenu.setUserController(userController1);
        profileMenu.start(Menu.stage);
    }

    public static void startReignMenu(Game game) throws Exception {
        ReignMenu reignMenu = new ReignMenu();
        reignMenu.setReignController(new ReignController(game));
        Stage reign = new Stage();
        reignMenu.start(reign);
    }
    public static void startShopMenu(Game game) throws Exception {
        ShopMenu shopMenu = new ShopMenu();
        shopMenu.setShopController(new ShopController(game));
        ShopMenu.game = game;
        shopMenu.start(stage);
    }
        //System.out.println(reader.toString());

        //boolean exists = tempFile.exists();
//        Menu menu = new RegisterAndLoginMenu(new UserController());
        //if(exists){
        //    String isLoggedIn = tempFile.toString();
        //    System.out.println(isLoggedIn);

//            UserController userController = new UserController();
//            userController.setLoggedInUser(User.getUserByUsername(nameOfLoggedIn));
//            menu = new MainMenu(userController);
       // }





    public void run() throws IOException, NoSuchAlgorithmException {

    }

//    public static Matcher getMatcher(String input , String regex){
//        Matcher matcher = Pattern.compile(regex).matcher(input);
//        return matcher.matches()? matcher : null;
//    }
//    public static Matcher findRegex(String input , Commands command) {
//        Matcher matcher = Pattern.compile(command.regex).matcher(input);
//        //System.out.println("input: " + input + "  \ncommands: " + command.regex);
//        return matcher.find()? matcher : null;
//    }
//    public static Matcher getRealMatcher(String input, Commands starter, Commands... groups) {
//
//        if( findRegex(input , starter) == null) {
//            //System.out.println("find regex did not work");
//            return null;
//        }
//        input = input.replaceFirst(starter.regex , "");
//        //System.out.println("new input; " + input);
//        String orderedInput = "";
//        Matcher matcher1;
//        for (Commands group : groups) {
//            if((matcher1 = findRegex(input , group)) == null){
//                //System.out.println("group" + group + "not found");
//                return null;
//            }
//            input = input.replaceFirst(group.regex, "");
//            orderedInput += " " + matcher1.group();
//            //System.out.println(input);
//        }
//        //System.out.println("at last: " + input);
//        if (!input.matches("\\s*")) return null;
//        //System.out.println("regex group: " + appendGroups(groups));
//        //System.out.println(orderedInput);
//        return getMatcher(orderedInput , appendGroups(groups));
//    }
//    public static String appendGroups(Commands[] groups) {
//        String output = "\\" + "s*";
//        for (Commands group : groups) {
//            output += group.regex + "\\" + "s+";
//        }
//        output = output.replaceFirst("\\+$", "*");
//        return output;
//    }
//
//    public int getInt(Matcher matcher, String regex) {
//        return Integer.parseInt(matcher.group(regex));
//    }
//
//

    public static String buildATextInputDialog(String contentText, String title) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText(contentText);
        dialog.setTitle(title);
        dialog.showAndWait();
        return dialog.getEditor().getText();
    }
    public static void buildInformationAlert(String massage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(massage);
        alert.show();
    }

//    public int getRandomIntInRange(int range) {
//        Random random = new Random();
//        random.
//    }
    public static AtomicBoolean alertForConfirmation(String alertName, String contentText, String title) {
        Alert alert = new Alert(Alert.AlertType.NONE, alertName, ButtonType.YES, ButtonType.CANCEL);
        alert.setContentText(contentText);
        alert.setTitle(title);
        AtomicBoolean toReturn = new AtomicBoolean(false);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    toReturn.set(true);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if(response == ButtonType.CANCEL)
                toReturn.set(false);
        });
        return toReturn;
    }
}
