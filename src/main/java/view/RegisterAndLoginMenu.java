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
    private UserController userController = new UserController();


    public void run() throws IOException, NoSuchAlgorithmException {
        while (true) {
            input = scanner.nextLine();
            if ((matcher = Commands.getMatcher(input,Commands.CREATE_USER)) != null &&
                    booleanErrorInCreateUser(matcher.group("contentText").trim())) {
                System.out.println(userController.register(matcher,extractUsername(input),extractPassword(input),
                        extractEmail(input),extractNickname(input),extractSlogan(input),extractPasswordConfirm(input)));
            } else if((matcher = Commands.getMatcher(input,Commands.USER_LOGIN)) != null) {
                String resOfLogin = userController.login(extractUsername(input),extractPasswordInLogin(input),input);
                System.out.println(resOfLogin);
                if(resOfLogin.equals("logged in successfully")) {
                    MainMenu mainMenu = new MainMenu(this.userController);
                    mainMenu.run();
                }
            } else if ((matcher = Commands.getMatcher(input,Commands.FORGOT_MY_PASSWORD)) != null) {
                System.out.println(userController.forgotMyPassword(extractUsername(input),extractPassword(input)));
            } else {
                System.out.println("Invalid commend!");
            }

        }
    }

    public RegisterAndLoginMenu(UserController userController) {
        this.userController = userController;
    }

    public boolean booleanErrorInCreateUser(String contentText) {
        if (Commands.getMatcher(contentText, Commands.PASSWORD_NOT_IN_LOGIN) == null
                || Commands.getMatcher(contentText, Commands.USERNAME) == null)
            return false;
        else if (Commands.getMatcher(contentText, Commands.NICKNAME) == null
                || Commands.getMatcher(contentText, Commands.EMAIL) == null)
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
    private String extractUsername(String text) {
        Matcher userNameMatcher = Commands.getMatcher(text, Commands.USERNAME);
        String username = userController.checkForQuotation(userNameMatcher.group("username"));
        return username;
    }

    private String extractPasswordConfirm(String text) {
        Matcher passwordMatcher =  Commands.getMatcher(input, Commands.PASSWORD_NOT_IN_LOGIN);
        if (passwordMatcher.group("passwordConfirm") == null) return "random";
        else return userController.checkForQuotation(passwordMatcher.group("passwordConfirm"));
    }

    private String extractPassword(String text) {
        Matcher passwordMatcher = Commands.getMatcher(text, Commands.PASSWORD_NOT_IN_LOGIN);
        String password = userController.checkForQuotation(passwordMatcher.group("password"));
        return password;

    }

    private String extractNickname(String text) {
        Matcher nickNameMatcher = Commands.getMatcher(text, Commands.NICKNAME);
        String password = userController.checkForQuotation(nickNameMatcher.group("nickName"));
        return password;
    }

    private String extractEmail(String text) {
        Matcher emailMatcher = Commands.getMatcher(text, Commands.EMAIL);
        String password = userController.checkForQuotation(emailMatcher.group("email"));
        return password;
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
    private String extractPasswordInLogin(String text){
        Matcher passwordMatcher = Commands.getMatcher(text, Commands.PASSWORD_USED_IN_LOGIN);
        String password = userController.checkForQuotation(passwordMatcher.group("password"));
        return password;
    }

}
