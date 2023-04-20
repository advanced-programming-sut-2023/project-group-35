package main.java.view;

import main.java.Enum.*;
import main.java.controller.UserController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import main.java.model.Manager;


public class RegisterAndLoginMenu extends Menu{
    private UserController userController = new UserController();


    private String input;
    private Matcher matcher;

    public void run() throws IOException, NoSuchAlgorithmException {
        Scanner scn = new Scanner(System.in);
        while (true) {
            input = scn.nextLine();
            if ((matcher = CommandEnums.getMatcher(input, CommandEnums.CREATE_USER)) != null &&
                    booleanErrorInCreateUser(matcher.group("contentText").trim())) {
                System.out.println(userController.register(matcher));
            } else {

            }

        }
    }

    public RegisterAndLoginMenu(UserController userController) {
        this.userController = userController;
    }

    public boolean booleanErrorInCreateUser(String contentText) {
        if (CommandEnums.getMatcher(contentText, CommandEnums.PASSWORD_USED_IN_LOGIN) == null || CommandEnums.getMatcher(contentText, CommandEnums.USERNAME) == null)
            return false;
        else if (CommandEnums.getMatcher(contentText, CommandEnums.NICKNAME) == null || CommandEnums.getMatcher(contentText, CommandEnums.EMAIL) == null)
            return false;
        return true;
    }

    public static String getSafetyQuestion() {
        int counter = 1;
        String lastLine;
        String question = null;
        Scanner scn = new Scanner(System.in);
        System.out.println("pick one question :");
        for (SecurityQuestion questionSecurity : SecurityQuestion.values()) {
            System.out.println(questionSecurity.question);
        }
        int numberChosen;
        while (true) {
            lastLine = scn.nextLine();
            numberChosen = Integer.parseInt(lastLine);
            if (numberChosen > 3 || numberChosen < 1)
                System.out.println("Invalid question number");
            else break;
        }

        for (SecurityQuestion securityQuestion : SecurityQuestion.values()) {
            counter++;
            if (counter == numberChosen) {
                question = securityQuestion.question;
                break;
            }
        }

        return question;
    }

    public static String getAnswerOfQuestion() {
        Scanner scn = new Scanner(System.in);
        String nextLine = null;
        nextLine = scn.nextLine();
        return nextLine;
    }

    public static String getRandomPassword() {
        String randomPassword = "a";
        int firstUppercaseIndex = (int) 'A';
        int firstLowercaseIndex = (int) 'a';
        char randomLowercase = 'a';
        char randomUppercase = 'A';
        int randomInt = 0;
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            int letterIndex = random.nextInt(26);
            randomInt = random.nextInt(99);
            randomLowercase = (char) (firstLowercaseIndex + letterIndex);
            randomUppercase = (char) (firstUppercaseIndex + letterIndex);
        }
        randomPassword = randomPassword + randomLowercase + randomUppercase + randomInt;
        System.out.println("our suggested random password is: " + randomPassword + "now retype it.");
        return randomPassword;
        //TODO:add non letter
    }

    public static boolean checkPassword(String password) {
        Scanner scn = new Scanner(System.in);
        String userTry = scn.nextLine();
        while (true) {
            if (userTry.equals(password))
                return true;
            else {
                System.out.println("You failed to repeat the password correctly!");
            }
        }
    }

    public static boolean checkPasswordErrors(String password) {
        if (password.length() < 6) {
            System.out.println("Password Is Short!");
            return false;
        } else if (!password.matches(".*[A-Z].*")) {
            System.out.println("Your Password doesn't has any uppercase letter!");
            return false;
        } else if (!password.matches(".*[a-z].*")) {
            System.out.println("Your Password doesn't has any lowercase letter!");
            return false;
        } else if (!password.matches(".*[0-9].*")) {
            System.out.println("Your Password doesn't has any number!");
            return false;
        } else if (!password.matches(".*[!@#$%^&*\\(\\)]")) {
            System.out.println("Your Password doesn't has any special character!");
            return false;
        }
        return true;
    }


    public String getRandomSlogan() {
        return null;
    }

    public static String suggestNewName(String oldUserName) {
        String string;
        Scanner scn = new Scanner(System.in);
        System.out.println("Your username exists,our suggested user name is: " + oldUserName + Manager.getSizeOfUser() + "if you agree print ok else enter a new name.");
        string = scn.nextLine();
        if (string.trim().equals("ok"))
            return oldUserName + Manager.getSizeOfUser();
        string = scn.nextLine();
        return string;
    }

}
