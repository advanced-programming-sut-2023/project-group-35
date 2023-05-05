package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.*;
import view.MainMenu;
import view.RegisterAndLoginMenu;

public class UserController {
    private User loggedInUser;

    public String checkForQuotation(String text){
        if((text.charAt(0)=='\"') && (text.trim().charAt(text.trim().length() - 1) == '\"'))
            return text.substring(1,text.length()-1);
        return text;
    }
    public String UserController(Matcher matcher) {
        return null;
    }
    public String register(Matcher matcher,String userName,String password,String email,String nickName,
                           String slogan,String passwordConfirm) throws IOException, NoSuchAlgorithmException {
        if(password.equals("Random")) {
            password = RegisterAndLoginMenu.getRandomPassword();
            if(RegisterAndLoginMenu.checkPassword(password))
                System.out.println("It was entered correctly.");
        }

         email = checkForQuotation(matcher.group("email"));
         nickName = checkForQuotation(matcher.group("nickname"));
         slogan = null;
         passwordConfirm = null;
        String securityQuestion = null;
        String answerToSecurity = null;
        if(matcher.group("sloganText") != null)
            slogan = matcher.group("sloganText");
        //TODO:Random slogan generator;
        if(!password.equals("Random"))
            passwordConfirm = matcher.group("passwordConfirm");
        if(userName.length() < 1 || password.length() < 1 || email.length() < 1 || nickName.length() < 1)
            return "There is some field empty!";
        else if(!userName.matches("\\w+"))
            return "Invalid username format!";
        else if(User.getUserByUsername(userName) != null) {
            String suggestedUserName = RegisterAndLoginMenu.suggestNewName(userName);
            userName = suggestedUserName;
        }
        if(!RegisterAndLoginMenu.checkPasswordErrors(password))
            return "so you got the error...";
        if(passwordConfirm != null && !passwordConfirm.equals(password))
            return "You didn't repeat the password correctly";
        if(User.getUserByEmail(email) != null)
            return "Email already exists in Server!";
        else if(!email.matches("[\\w\\.]+@[\\w\\.]+\\.[\\w\\.]+"))
            return "Invalid Email format";
        securityQuestion = RegisterAndLoginMenu.getSafetyQuestion();
        answerToSecurity = RegisterAndLoginMenu.getAnswerOfQuestion();
        User userToBeAdded = new User(userName,password,nickName,email,securityQuestion,answerToSecurity,slogan);
        User.addUser(userToBeAdded);
        Manager.fileCreator(userToBeAdded);
        return "Sign up was successful,we have "+userName+"on board now";
    }
    public String login(Matcher matcher,String command) throws NoSuchAlgorithmException, IOException {
        String userName = checkForQuotation(matcher.group("username"));
        String password = checkForQuotation(matcher.group("password"));
        Boolean gonnaBeLoggedIn = null;
        Pattern stayLoggedIn = Pattern.compile("--stay-logged-in");
        Matcher matcherOfLogin = stayLoggedIn.matcher(command);
        if(matcherOfLogin.find()) gonnaBeLoggedIn = true;
        else gonnaBeLoggedIn = false;
        if(User.getUserByUsername(userName) == null)
            return "No such user found!";
        else if((User.getUserByUsername(userName).getLastAttemptForLogin() - System.currentTimeMillis()) <=
                User.getUserByUsername(userName).getAttemptsNumber()*1000)
            return "wait few seconds before another try";
        else if(!Manager.turnPasswordToSha256(password).equals(User.getUserByUsername(userName).getPassword())) {
            User.getUserByUsername(userName).addNumberOfAttempts();
            User.getUserByUsername(userName).setLastAttemptForLogin(System.currentTimeMillis());
            return "Password didn't match";
        }
        else{
         loggedInUser = User.getUserByUsername(userName);
         if(gonnaBeLoggedIn){
             FileWriter fileWriter = new FileWriter("loggedIn.txt");
             fileWriter.write(loggedInUser.getUserName());
             fileWriter.close();
         }
         loggedInUser.setAttemptsNumber(0);
         return "logged in successfully";

        }
    }
    public String forgotMyPassword(String userName,String password){
        if(User.getUserByUsername(userName) == null)
            return "No such user exists!";
        else if(!RegisterAndLoginMenu.checkTheSecurityHitAndPass(User.getUserByUsername(userName)))
                return "you didn't answered security question correctly";
        else if(!RegisterAndLoginMenu.checkPasswordErrors(password))
            return "you get the error...";
        else{
            User.getUserByUsername(userName).setPassword(password);
            return "password changed successfully";
        }

    }
    public String usernameChange(String newUserName) {
        if(!newUserName.matches("\\w+") || newUserName.length() < 1)
            return "Invalid username format!";
        else{
            loggedInUser.setUserName(newUserName);
            return "your username changed!";
        }
    }
    public String nicknameChange(String newNickName) {
        if(!newNickName.matches("\\w+") || newNickName.length() < 1)
            return "Invalid username format!";
        else{
            loggedInUser.setNickName(newNickName);
            return "your nickname changed!";
        }
    }
    public String passwordChanger(String oldPass , String newPass) throws NoSuchAlgorithmException, IOException {
        if(!loggedInUser.getPassword().equals(Manager.turnPasswordToSha256(oldPass)))
            return "You entered wrong password!";
        else if(loggedInUser.getPassword().equals(Manager.turnPasswordToSha256(newPass)))
        return "your new password is same as last one";
        else if(!RegisterAndLoginMenu.checkPasswordErrors(newPass))
            return "so you got the error...";
        else if(!RegisterAndLoginMenu.checkPassword(newPass))
            return "try the command again!";
        else{
            loggedInUser.setPassword(newPass);
            return "your password changed!";
        }
    }
    public String emailChange(String email) {
        if(User.getUserByEmail(email) != null)
            return "this email is already in database";
        else if(!email.matches("[\\w\\.]+@[\\w\\.]+\\.[\\w\\.]+"))
            return "Invalid Email format";
        else{
            loggedInUser.setEmail(email);
            return "your email changed!";
        }
    }
    public String changeOrRemoveSlogan(String slogan) {
        if(loggedInUser.getSloganOfUser() == null && slogan == null)
            return "you have no slogan to remove!";
        if(slogan == null){
            loggedInUser.setSloganOfUser(null);
            return "your slogan removed!";
        }
        else{
            loggedInUser.setSloganOfUser(slogan);
            return "your slogan changed!";
        }
    }
    public int displayHighScore() {
        return loggedInUser.getHighScore();
    }
    public int displayRank() {
         int rankOfUser = 1;
        for(User user:User.getUsers())
            if(loggedInUser.getHighScore() < user.getHighScore()) rankOfUser++;
        return rankOfUser;
    }
    public String displaySlogan() {
        return loggedInUser.getSloganOfUser();
    }
    public String displayProfile() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Your NickName: "+loggedInUser.getNickName());
        stringBuilder.append("Your Slogan: "+loggedInUser.getSloganOfUser());
        stringBuilder.append("Your highscore is "+loggedInUser.getHighScore());
        stringBuilder.append("Your rank between users "+displayRank());
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
}
