package model;

import java.util.*;

public class User implements Comparable {
    private String userName;
    private String password;
    public String nickName;
    private String email;
    private String securityQuestion;
    private String securityAnswer;
    public int highScore;
    public int totalScore;
    private int attemptsNumber;
    private long lastAttemptForLogin;
    private String sloganOfUser;

    private static final ArrayList<User> users = new ArrayList<>();

    public User(String userName, String password, String nickName,
                String email, String securityQuestion, String securityAnswer,String slogan) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.sloganOfUser = slogan;
        this.highScore = 0;
        this.attemptsNumber = 0;
        this.lastAttemptForLogin = 0;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void setAttemptsNumber(int attemptsNumber) {
        this.attemptsNumber = attemptsNumber;
    }

    public int getAttemptsNumber() {
        return attemptsNumber;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static User getUserByUsername(String userName){
        for(User user: users){
            if(userName.equals(user.getUserName()))
                return user;
        }
        return null;
    }
    public static User getUserByEmail(String email){
        for(User user: users){
            if(email.equalsIgnoreCase(user.getEmail()))
                return user;
        }
        return null;
    }
    public static int getSizeOfUser(){
        return users.size();
    }
    public static void addUser(User user){
        users.add(user);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }
    public void addNumberOfAttempts(){ attemptsNumber++;}

    public long getLastAttemptForLogin() {
        return lastAttemptForLogin;
    }

    public void setLastAttemptForLogin(long lastAttemptForLogin) {
        this.lastAttemptForLogin = lastAttemptForLogin;
    }

    public String getSloganOfUser() {
        return sloganOfUser;
    }

    public void setSloganOfUser(String sloganOfUser) {
        this.sloganOfUser = sloganOfUser;
    }

    @Override
    public int compareTo(Object o) {
        return ((User)o).totalScore -this.totalScore;
    }
}
