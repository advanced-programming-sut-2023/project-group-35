package view;

import java.io.*;
import java.security.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Enum.*;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class Menu extends Application {
    public static final Scanner scanner = new Scanner(System.in);
    protected String input;
    protected String result;
    protected Matcher matcher;
    protected static Stage stage;
    public static Color successGreenColor = Color.rgb(30,103, 45);
    public static Color failRedColor = Color.rgb(145, 38, 32);
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Menu.stage = stage;
        stage.setFullScreen(true);
//        File tempFile = new File("loggedIn.txt");
//        boolean exists = tempFile.exists();
//        Menu menu = new RegisterAndLoginMenu(new UserController());
//        if(exists){
//            String nameOfLoggedIn = tempFile.toString();
//            UserController userController = new UserController();
//            userController.setLoggedInUser(User.getUserByUsername(nameOfLoggedIn));
//            menu = new MainMenu(userController);
//        }
//        menu.run();
        new RegisterAndLoginMenu().start(Menu.stage);
    }

    public void run() throws IOException, NoSuchAlgorithmException {

    }

    public static Matcher getMatcher(String input , String regex){
        Matcher matcher = Pattern.compile(regex).matcher(input);
        return matcher.matches()? matcher : null;
    }
    public static Matcher findRegex(String input , Commands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        //System.out.println("input: " + input + "  \ncommands: " + command.regex);
        return matcher.find()? matcher : null;
    }
    public static Matcher getRealMatcher(String input, Commands starter, Commands... groups) {

        if( findRegex(input , starter) == null) {
            //System.out.println("find regex did not work");
            return null;
        }
        input = input.replaceFirst(starter.regex , "");
        //System.out.println("new input; " + input);
        String orderedInput = "";
        Matcher matcher1;
        for (Commands group : groups) {
            if((matcher1 = findRegex(input , group)) == null){
                //System.out.println("group" + group + "not found");
                return null;
            }
            input = input.replaceFirst(group.regex, "");
            orderedInput += " " + matcher1.group();
            //System.out.println(input);
        }
        //System.out.println("at last: " + input);
        if (!input.matches("\\s*")) return null;
        //System.out.println("regex group: " + appendGroups(groups));
        //System.out.println(orderedInput);
        return getMatcher(orderedInput , appendGroups(groups));
    }
    public static String appendGroups(Commands[] groups) {
        String output = "\\" + "s*";
        for (Commands group : groups) {
            output += group.regex + "\\" + "s+";
        }
        output = output.replaceFirst("\\+$", "*");
        return output;
    }

    public int getInt(Matcher matcher, String regex) {
        return Integer.parseInt(matcher.group(regex));
    }



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
    public static AtomicBoolean alertForConfirmation(String alertName, String contentText, String title) {
        Alert alert = new Alert(Alert.AlertType.NONE, alertName, ButtonType.YES, ButtonType.CANCEL);
        alert.setContentText(contentText);
        alert.setTitle(title);
        AtomicBoolean toReturn = new AtomicBoolean(false);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    toReturn.set(false);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if(response == ButtonType.CANCEL)
                toReturn.set(true);
        });
        return toReturn;
    }
}
