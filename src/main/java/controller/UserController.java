package controller;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import model.*;
import Enum.*;
import view.MainMenu;
import view.RegisterAndLoginMenu;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


public class UserController {
    private User loggedInUser;

    public static String checkForQuotation(String text) {
        if (text == null)
            return text;
        if ((text.charAt(0) == '\"') && (text.trim().charAt(text.trim().length() - 1) == '\"'))
            return text.substring(1, text.length() - 1);
        return text;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public UserController() {
        loadTheData();
        Map.loadTheMap();
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String register(ُString password,String userName,String nickName,String passwordConfirm,String email,
                           String slogan) throws IOException, NoSuchAlgorithmException {
        nickName = checkForQuotation(nickName);
        if (password.equals("random")) {
            password = RegisterAndLoginMenu.getRandomPassword();
            if (RegisterAndLoginMenu.checkPassword(password))
                System.out.println("It was entered correctly.");
                passwordConfirm = password;
        }
        if (slogan != null && slogan.equals("Random")) {
            slogan = generateRandomSlogan();
        }
        if (slogan != null && slogan.length() < 1)
            return "there is an empty field!";
        SecurityQuestion securityQuestion = null;
        String answerToSecurity = null;
        if (userName.length() < 1 || password.length() < 1 || email.length() < 1 || nickName.length() < 1)
            return "There is some field empty!";
        else if (!userName.matches("\\w+"))
            return "Invalid username format!";
        else if (User.getUserByUsername(userName) != null) {
            String suggestedUserName = RegisterAndLoginMenu.suggestNewName(userName);
            userName = suggestedUserName;
        }
        if (!RegisterAndLoginMenu.checkPasswordErrors(password))
            return "so you got the error...";
        if (passwordConfirm != null && !passwordConfirm.trim().equals(password.trim()))
            return "You didn't repeat the password correctly";
        if (User.getUserByEmail(email) != null)
            return "Email already exists in Server!";
        else if (!email.matches("[\\w\\.]+@[\\w\\.]+\\.[\\w\\.]+"))
            return "Invalid Email format";
        securityQuestion = RegisterAndLoginMenu.getSafetyQuestion();
        answerToSecurity = RegisterAndLoginMenu.getAnswerOfQuestion();
        User userToBeAdded = new User(userName, turnPasswordToSha256(password),
                nickName, email, securityQuestion, answerToSecurity, slogan);
        User.addUser(userToBeAdded);
        return "Sign up was successful,we have " + userName + " on board now";
    }

    public String login(String userName,String password,boolean isLoggedIn) throws NoSuchAlgorithmException, IOException {
        password = checkForQuotation(password);
        userName = checkForQuotation(userName);
        if (User.getUserByUsername(userName) == null)
            return "No such user found!";
        else if ((-1 * User.getUserByUsername(userName).getLastAttemptForLogin() + System.currentTimeMillis()) <=
                User.getUserByUsername(userName).getAttemptsNumber() * 1000)
            return "wait few seconds before another try";
        else if (!turnPasswordToSha256(password).equals(User.getUserByUsername(userName).getPassword())) {
            User.getUserByUsername(userName).addNumberOfAttempts();
            User.getUserByUsername(userName).setLastAttemptForLogin(System.currentTimeMillis());
            return "Password didn't match";
        } else {
            loggedInUser = User.getUserByUsername(userName);
            if (isLoggedIn) {
                FileWriter fileWriter = new FileWriter("loggedIn.txt");
                fileWriter.write(loggedInUser.getUserName());
                fileWriter.close();
            }
            loggedInUser.setAttemptsNumber(0);
            setLoggedInUser(loggedInUser);
            return "logged in successfully";

        }
    }

    public String forgotMyPassword(String userName,String password) throws NoSuchAlgorithmException, IOException {
        if (User.getUserByUsername(userName) == null)
            return "No such user exists!";
        else if (!RegisterAndLoginMenu.checkTheSecurityHitAndPass(User.getUserByUsername(userName)))
            return "you didn't answered security question correctly";
        else if (!RegisterAndLoginMenu.checkPasswordErrors(password))
            return "you get the error...";
        else {
            User.getUserByUsername(userName).setPassword(turnPasswordToSha256(password));
            return "password changed successfully";
        }

    }

    public String usernameChange(String newUserName) {
        newUserName = checkForQuotation(newUserName);
        if (!newUserName.matches("\\w+") || newUserName.length() < 1)
            return "Invalid username format!";
        else {
            loggedInUser.setUserName(newUserName);
            return "your username changed!";
        }
    }

    public String nicknameChange(String newNickName) {
        newNickName = checkForQuotation(newNickName);
        if (!newNickName.matches("\\w+") || newNickName.length() < 1)
            return "Invalid username format!";
        else {
            loggedInUser.setNickName(newNickName);
            return "your nickname changed!";
        }
    }

    public String passwordChanger(String oldPass,String newPass) throws NoSuchAlgorithmException, IOException {
        if (!loggedInUser.getPassword().equals(turnPasswordToSha256(oldPass)))
            return "You entered wrong password!";
        else if (loggedInUser.getPassword().equals(turnPasswordToSha256(newPass)))
            return "your new password is same as last one";
        else if (!RegisterAndLoginMenu.checkPasswordErrors(newPass))
            return "so you got the error...";
        else if (!RegisterAndLoginMenu.checkPassword(newPass))
            return "try the command again!";
        else {
            loggedInUser.setPassword(turnPasswordToSha256(newPass));
            return "your password changed!";
        }
    }

    public String emailChange(String email) {
        if (User.getUserByEmail(email) != null)
            return "this email is already in database";
        else if (!email.matches("[\\w\\.]+@[\\w\\.]+\\.[\\w\\.]+"))
            return "Invalid Email format";
        else {
            loggedInUser.setEmail(email);
            return "your email changed!";
        }
    }

    public String generateRandomSlogan() {
        Random randomNumber = new Random();
        int numberChosen = randomNumber.nextInt(3);
        int counter = 0;
        for (Slogans slogan : Slogans.values()) {
            if (counter < numberChosen + 1 && counter > numberChosen) {
                System.out.println("Your new slogan is: " + slogan.getSlogan());
                return slogan.getSlogan();
            }
        }
        System.out.println("Your new slogan is: this shouldn't happens");
        return "this shouldn't happens";
    }

    public String changeOrRemoveSlogan(String slogan) {
        if (loggedInUser.getSloganOfUser() == null && slogan == null)
            return "you have no slogan to remove!";
        if (slogan == null) {
            loggedInUser.setSloganOfUser(null);
            return "your slogan removed!";
        } else {
            loggedInUser.setSloganOfUser(slogan);
            return "your slogan changed!";
        }
    }

    public int displayHighScore() {
        return loggedInUser.getHighScore();
    }

    public int displayRank() {
        int rankOfUser = 1;
        for (User user : User.getUsers())
            if (loggedInUser.getHighScore() < user.getHighScore()) rankOfUser++;
        return rankOfUser;
    }

    public String displaySlogan() {
        if (loggedInUser.getSloganOfUser() == null)
            return "you don't have an slogan!\nuse <profile change slogan> to choose a slogan!";
        return loggedInUser.getSloganOfUser();
    }

    public String displayProfile() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Your NickName: " + loggedInUser.getNickName() + "\n");
        stringBuilder.append("Your Slogan: " + loggedInUser.getSloganOfUser() + "\n");
        stringBuilder.append("Your highscore is " + loggedInUser.getHighScore() + "\n");
        stringBuilder.append("Your rank between users is <" + displayRank() + ">");
        String out = stringBuilder.toString();
        return out;
    }


    public void chooseMap() {
        while (true) {
            String mapName = MainMenu.getMapFromUser(Map.getMapList());
            Map map = Map.getTemplateMapByName(mapName);
            if (map != null) {
                loggedInUser.setMap(map);
                break;
            }
        }
    }
    public void createMap(){
        System.out.println("enter map name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Map newMap = new Map(loggedInUser,100,name);
        loggedInUser.setMap(newMap);
        Map.getTemplateMaps().add(newMap);
    }

    public static String turnPasswordToSha256(String password) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        ;
        md.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        String hex = String.format("%064x", new BigInteger(1, digest));
        return hex;
    }

    public static void saveTheData() {
        Gson gson = new Gson();
        String json = gson.toJson(User.getUsers());
        try {
            FileWriter myWriter = new FileWriter("dataBase.json");
            myWriter.write(json);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadTheData() {
        Reader reader;
        try {
            reader = new FileReader("dataBase.json");
        } catch (FileNotFoundException e) {
            return;
        }
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
        for (JsonElement jsonElement : jsonArray)
            User.getUsers().add(gson.fromJson(jsonElement, User.class));
    }
}
