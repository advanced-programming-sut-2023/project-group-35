package main.java.model;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.math.BigInteger;


public class Manager {
    private static final ArrayList<User> users = new ArrayList<>();
    public static User getUserByName(String userName){
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
    public static void fileCreator(User user) throws IOException, NoSuchAlgorithmException {
        FileWriter fileWriter = new FileWriter(user.getUserName()+".txt");
        fileWriter.write(user.getUserName()+"\n"+user.getNickName()+"\n"+turnPasswordToSha256(user.getPassword())
        +"\n"+user.getEmail()+"\n"+user.getSecurityAnswer()+"\n"+user.getSecurityQuestion()+"\n"+user.highscore);
        fileWriter.close();
    }
    public static String turnPasswordToSha256(String password) throws NoSuchAlgorithmException,IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");;
        md.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        String hex = String.format("%064x", new BigInteger(1, digest));
        return hex;
    }

}
