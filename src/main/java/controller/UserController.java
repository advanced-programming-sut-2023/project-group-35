package main.java.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import main.java.model.*;
import main.java.view.RegisterAndLoginMenu;

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
    public String register(Matcher matcher) throws IOException, NoSuchAlgorithmException {
        //TODO:creater matchers;
        String userName = checkForQuotation(matcher.group("username"));
        String password = checkForQuotation(matcher.group("password"));
        if(password.equals("Random")) {
            password = RegisterAndLoginMenu.getRandomPassword();
            if(RegisterAndLoginMenu.checkPassword(password))
                System.out.println("It was entered correctly.");
        }

        String email = checkForQuotation(matcher.group("email"));
        String nickName = checkForQuotation(matcher.group("nickname"));
        String slogan = null;
        String passwordConfirm = null;
        String securityQuestion = null;
        String answerToSecurity = null;
        if(matcher.group("sloganText") != null)
            slogan = matcher.group("sloganText");
        if(!password.equals("Random"))
            passwordConfirm = matcher.group("passwordConfirm");
        if(userName.length() < 1 || password.length() < 1 || email.length() < 1 || nickName.length() < 1)
            return "There is some field empty!";
        else if(!userName.matches("\\w+"))
            return "Invalid username format!";
        else if(Manager.getUserByName(userName) != null) {
            String suggestedUserName = RegisterAndLoginMenu.suggestNewName(userName);
            userName = suggestedUserName;
        }
        if(!RegisterAndLoginMenu.checkPasswordErrors(password))
            return "so you got the error...";
        if(passwordConfirm != null && !passwordConfirm.equals(password))
            return "You didn't repeat the password correctly";
        if(Manager.getUserByEmail(email) != null)
            return "Email already exists in Server!";
        else if(!email.matches("[\\w\\.]+@[\\w\\.]+\\.[\\w\\.]+"))
            return "Invalid Email format";
        securityQuestion = RegisterAndLoginMenu.getSafetyQuestion();
        answerToSecurity = RegisterAndLoginMenu.getAnswerOfQuestion();
        User userToBeAdded = new User(userName,password,nickName,email,securityQuestion,answerToSecurity,0);
        Manager.addUser(userToBeAdded);
        Manager.fileCreator(userToBeAdded);
        return "Sign up was succesful,we have "+userName+"on board now";
    }
    public String questionPick(Matcher matcher) {
        return null;
    }
    public String login(Matcher matcher) {
        return null;
    }
    public String usernameChange(String newUsername) {
        return null;
    }
    public String nicknameChange(String newNickName) {
        return null;
    }
    public String passwordChanger(Matcher matcher , String confirmPass) {
        return null;
    }
    public String emailChange(String email) {
        return null;
    }
    public String changeOrRemoveSlogan(String slogan) {
        return null;
    }
    public void displayHighScore() {
        return;
    }
    public void displayRank() {
        return;
    }
    public void displaySlogan() {
        return;
    }
    public String displayProfile() {
        return null;
    }
}
