package main.java.model;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.math.BigInteger;


public class Manager {
    //todo these functions should be transferred to controller and the class should be deleted

    public static void fileCreator(User user) throws IOException, NoSuchAlgorithmException {
        FileWriter fileWriter = new FileWriter(user.getUserName()+".txt");
        fileWriter.write(user.getUserName()+"\n"+user.getNickName()+"\n"+turnPasswordToSha256(user.getPassword())
        +"\n"+user.getEmail()+"\n"+user.getSecurityAnswer()+"\n"+user.getSecurityQuestion()+"\n"+user.highScore);
        fileWriter.close();
    } //todo change to json
    public static String turnPasswordToSha256(String password) throws NoSuchAlgorithmException,IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");;
        md.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        String hex = String.format("%064x", new BigInteger(1, digest));
        return hex;
    }

}
