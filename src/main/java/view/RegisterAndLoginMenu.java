package view;

import Enum.*;
import model.*;
import controller.UserController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;




public class RegisterAndLoginMenu extends Menu{
    private UserController userController;


    public void run() throws IOException, NoSuchAlgorithmException {
        System.out.println("welcome to crusaders!\n please login or sign up if you don't have an account.");
        while (true) {
            input = scanner.nextLine();
            if ((matcher = getRealMatcher(input,Commands.CREATE_USER,Commands.USERNAME, Commands.PASSWORD_NOT_IN_LOGIN, Commands.EMAIL,
                    Commands.NICKNAME)) != null) {
                System.out.println(userController.register(matcher,extractSlogan(input)));
            } else if((matcher = getRealMatcher(input,Commands.USER_LOGIN, Commands.USERNAME, Commands.PASSWORD_USED_IN_LOGIN)) != null) {
                String resOfLogin = userController.login(matcher,input);
                System.out.println(resOfLogin);
                if(resOfLogin.equals("logged in successfully")) {
                    MainMenu mainMenu = new MainMenu(this.userController);
                    UserController.saveTheData();
                    mainMenu.run();
                }
            } else if ((matcher = getRealMatcher(input,Commands.FORGOT_MY_PASSWORD,
                    Commands.USERNAME,Commands.PASSWORD_USED_IN_LOGIN)) != null) {
                System.out.println(userController.forgotMyPassword(matcher));
            }
            else if((matcher = getRealMatcher(input,Commands.EXIT))!= null){
                if(isUserSureToExit()) {
                    UserController.saveTheData();
                    System.out.println("game is now finished");
                    return;
                }
            } else if(input.matches("\\s*show\\s+menu\\s*")){
                System.out.println("you are in register and login menu");
            } else {
                System.out.println(ResponseToUser.INVALID_COMMAND);
            }

        }
    }

    public RegisterAndLoginMenu(UserController userController) {
        this.userController = userController;
    }


    public static SecurityQuestion getSafetyQuestion() {
        int counter = 1;
        String lastLine;
        SecurityQuestion question = null;
        Scanner scn = new Scanner(System.in);
        System.out.println("pick one question :");
        for (SecurityQuestion questionSecurity : SecurityQuestion.values()) {
            System.out.println(questionSecurity.question);
        }
        int numberChosen;
        while (true) {
            lastLine = scn.nextLine();
            if(!Commands.getMatcher(lastLine,Commands.ISNUMERIC).matches()) {
                System.out.println("Wtf, we asked you for a number!");
                continue;
            }
            numberChosen = Integer.parseInt(lastLine);
            if (numberChosen > 3 || numberChosen < 1)
                System.out.println("Invalid question number");
            else break;
        }

        for (SecurityQuestion securityQuestion : SecurityQuestion.values()) {
            counter++;
            if (counter == numberChosen) {
                question = securityQuestion;
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
            randomInt = random.nextInt(999);
            randomLowercase = (char) (firstLowercaseIndex + letterIndex);
            randomUppercase = (char) (firstUppercaseIndex + letterIndex);
        }
        randomPassword = randomPassword + randomLowercase + randomUppercase + randomInt + "!@";
        System.out.println("our suggested random password is: " + randomPassword + " now retype it.");
        return randomPassword;
    }

    public static boolean checkPassword(String password) {
        Scanner scn = new Scanner(System.in);
        String userTry = scn.nextLine();
        while (true) {
            if (userTry.equals(password))
                return true;
            else {
                System.out.println("You failed to repeat the password correctly!");
                return false;
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
        } else if (!password.matches(".*[!@#$%^&*\\(\\)].*")) {
            System.out.println("Your Password doesn't has any special character!");
            return false;
        }
        return true;
    }

    public static String suggestNewName(String oldUserName) {
        String string;
        Scanner scn = new Scanner(System.in);
        System.out.println("Your username exists,our suggested user name is: " + oldUserName + User.getSizeOfUser() +
                " if you agree print ok else enter a new name.");
        string = scn.nextLine();
        if (string.trim().equals("ok"))
            return oldUserName + User.getSizeOfUser();
        return string;
    }
    public static boolean checkTheSecurityHitAndPass(User user){
        System.out.println(user.getSecurityQuestion());
        Scanner scn = new Scanner(System.in);
        String lastLine = scn.nextLine();
        if(lastLine.equals(user.getSecurityAnswer()))
            return true;
        return false;
    }

    private String extractSlogan(String text) {
        String slogan = null;
        if (Commands.getMatcher(text, Commands.SLOGAN) != null) {
            Matcher sloganMarcher = Commands.getMatcher(text, Commands.SLOGAN);
            slogan = userController.checkForQuotation(sloganMarcher.group("slogan"));
            if(slogan == null)
                return null;
            if (slogan.length() < 1)
                return null;
            return slogan;
        }
        return null;
    }
    public boolean isUserSureToExit() {
        while (true) {
            System.out.println("are you sure you want to exit the game?");
            if (scanner.nextLine().matches("yes")) return true;
            if (scanner.nextLine().matches("no")) return false;
            System.out.println("please try again");
        }
    }

}
