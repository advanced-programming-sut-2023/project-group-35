package model;
import Enum.*;
import java.util.*;

public class User implements Comparable {
    private String userName;
    private String password;
    public String nickName;
    private String email;
    private SecurityQuestion securityQuestion;
    private String securityAnswer;
    public int highScore;
    public int totalScore;
    private int attemptsNumber;
    private long lastAttemptForLogin;
    private String sloganOfUser;
    private Map map;
    private static final ArrayList<User> users = new ArrayList<>();

    public User(String userName, String password, String nickName,
                String email, SecurityQuestion securityQuestion, String securityAnswer, String slogan) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.sloganOfUser = slogan;
        this.highScore = 0;
        this.attemptsNumber = 0;
        this.lastAttemptForLogin = System.currentTimeMillis();
    }

    public int getHighScore() {
        return highScore;
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
        ArrayList<Integer> arrayList = new ArrayList<>();
        int counter = 0;
        for(User user: users){
            if(userName.equals(user.getUserName()))
                arrayList.add(counter);
            counter++;
        }
        if(arrayList.size() > 0)
            return users.get(arrayList.get(arrayList.size()-1));
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
        return securityQuestion.toString();
    }

    public void setSecurityQuestion(SecurityQuestion securityQuestion) {
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

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
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
    public void addScore(int score) {
        if(score > highScore) highScore = score;
    }

    @Override
    public int compareTo(Object o) {
        return ((User)o).totalScore -this.totalScore;
    }
}
