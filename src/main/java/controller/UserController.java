package controller;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import model.*;
import Enum.*;
import view.MainMenu;
import view.RegisterAndLoginMenu;
import com.google.gson.Gson;


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
        //loadTheData();
        //Map.loadTheMap();
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String register(String username, String password, String nickName, String email, String slogan,
                           SecurityQuestion securityQuestion, String answer) throws IOException, NoSuchAlgorithmException {
       // else if (!userName.matches("\\w+")) is this the right format for username?

        User userToBeAdded = new User(username, turnPasswordToSha256(password),
                nickName, email, securityQuestion, answer, slogan);
        User.addUser(userToBeAdded);
        saveTheData();
        return "Sign up was successful,we have " + username + " on board now";
    }


    public ResponseToUser isUsernameFormatCorrect(String username) {
        if(username.length() < 5) return ResponseToUser.SHORT_USERNAME;
        if(username.matches(".*[@#$%].*")) return ResponseToUser.INVALID_FORMAT_FOR_USERNAME;
        if(User.getUserByUsername(username) != null) return ResponseToUser.USERNAME_EXISTS;
        return ResponseToUser.CORRECT_FIELD;
    }

    public ResponseToUser checkTheEmail(String email) {
        if (!email.matches("[\\w\\.]+@[\\w\\.]+\\.[\\w\\.]+"))
            return ResponseToUser.INVALID_EMAIL_FORMAT;
        if (User.getUserByEmail(email) != null)
            return ResponseToUser.EMAIL_EXISTS;
        return ResponseToUser.CORRECT_FIELD;
    }
    public ResponseToUser login(String username, String password, boolean stayLoggedIn) throws NoSuchAlgorithmException, IOException {

        if (User.getUserByUsername(username) == null)
            return ResponseToUser.USERNAME_NOT_FOUND;
//        else if ((-1 * User.getUserByUsername(userName).getLastAttemptForLogin() + System.currentTimeMillis()) <=
//                User.getUserByUsername(userName).getAttemptsNumber() * 1000)
//            return "wait few seconds before another try";
        if (!turnPasswordToSha256(password).equals(User.getUserByUsername(username).getPassword())) {
//            User.getUserByUsername(userName).addNumberOfAttempts();
//            User.getUserByUsername(userName).setLastAttemptForLogin(System.currentTimeMillis());
            return ResponseToUser.WRONG_PASSWORD;
        }
        loggedInUser = User.getUserByUsername(username);
        if (stayLoggedIn) {
            FileWriter fileWriter = new FileWriter("loggedIn.txt");
            fileWriter.write(loggedInUser.getUserName());
            fileWriter.close();
        }
        //loggedInUser.setAttemptsNumber(0);
        setLoggedInUser(loggedInUser);
        return ResponseToUser.LOGGED_IN_SUCCESSFULLY;


    }

//    public String forgotMyPassword(Matcher matcher) throws NoSuchAlgorithmException, IOException {
//        String userName = matcher.group("username");
//        String password = matcher.group("password");
//        if (User.getUserByUsername(userName) == null)
//            return "No such user exists!";
//        else if (!RegisterAndLoginMenu.checkTheSecurityHitAndPass(User.getUserByUsername(userName)))
//            return "you didn't answered security question correctly";
//        else if (!checkPasswordErrors(password))
//            return "you get the error...";
//        else {
//            User.getUserByUsername(userName).setPassword(turnPasswordToSha256(password));
//            return "password changed successfully";
//        }
//
//    }

    public String usernameChange(Matcher matcher) {
        String newUserName = matcher.group("username");
        newUserName = checkForQuotation(newUserName);
        if(User.getUserByUsername(newUserName) != null) return "this username already exitst";
        if (!newUserName.matches("\\w+") || newUserName.length() < 1)
            return "Invalid username format!";
        else {
            loggedInUser.setUserName(newUserName);
            return "your username changed!";
        }
    }

    public String nicknameChange(Matcher matcher) {
        String newNickName = matcher.group("nickName");
        newNickName = checkForQuotation(newNickName);
        if (!newNickName.matches("\\w+") || newNickName.length() < 1)
            return "Invalid username format!";
        else {
            loggedInUser.setNickName(newNickName);
            return "your nickname changed!";
        }
    }

    public String changePassword(String newPass, String confirmPass) throws NoSuchAlgorithmException, IOException {
        String result;
        if(newPass.equals(confirmPass)) return "please check the confirmation again";
        if (!(result = checkPasswordErrors(newPass)).equals("perfect")) return result;
        loggedInUser.setPassword(turnPasswordToSha256(newPass));
        return "your password changed successfully!";

    }

    public static String checkPasswordErrors(String password) {
        if (password.length() < 6) {
            return "Your Password should be at least 6 characters";
        } else if (!password.matches(".*[A-Z].*")) {
            return "Your Password must have an uppercase letter!";
        } else if (!password.matches(".*[a-z].*")) {
            return "Your Password must have a lowercase letter!";
        } else if (!password.matches(".*[0-9].*")) {
            return "Password is not strong enough, must have a number!";
        } else if (!password.matches(".*[!@#$%^&*\\(\\)].*")) {
            return "Your Password must have a character from <!@#$%^&*()>!";
        }
        return "perfect";
    }
    public String emailChange(Matcher matcher) {
        String email = matcher.group("email");
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
        for (Slogan slogan : Slogan.values()) {
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
