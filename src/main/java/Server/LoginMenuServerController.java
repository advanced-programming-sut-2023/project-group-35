package Server;

import controller.UserController;
import model.User;
import view.Menu;
import view.RegisterAndLoginMenu;
import Enum.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;

public class LoginMenuServerController {
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    public LoginMenuServerController(DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
    }

    public User goInit() {
        User user;
        try {
            dataOutputStream.writeUTF("1-SignUp/2-Login");
            String input = dataInputStream.readUTF();
            while (true) {
                if (input.equals("exit")) return null;
                if (input.matches(".*1.*") || input.matches(".*sign up.*")){
                    user = signUp();
                    break;
                }
                else if ((input.matches(".*2.*") || input.matches(".*login.*"))) {
                    user = login();
                    break;
                }
                input = dataInputStream.readUTF();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (user != null) user.updateEnteringTime();
        return user;
    }

    private User login() {
        try {
            Matcher matcher = null;
            while (true) {
                String input = dataInputStream.readUTF();
                if (input.equals("exit")) return null;
                if ((matcher = RegEx.getMatcher(input,RegEx.USER_LOGIN)) == null) {
                    dataOutputStream.writeUTF("invalid input");
                    continue;
                }
                User user = trueLogin(matcher.group("username"), matcher.group("password"));
                if (user == null) dataOutputStream.writeUTF("invalid username or password");
                else return user;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private User signUp() {

        try {
            Matcher matcher = null;
            while (true) {
                String input = dataInputStream.readUTF();
                if (input.equals("exit")) return null;
                if ((matcher = RegEx.getMatcher(input,RegEx.USER_CREATE)) == null) {
                    dataOutputStream.writeUTF("invalid input");
                    continue;
                }
                String username = matcher.group("username");
                String res = trueSignUp(username,
                        matcher.group("nickname"),
                        matcher.group("password"),
                        matcher.group("email")
                );
                dataOutputStream.writeUTF(res);
                if(res.equals("Success!")){
                 return User.getUserByUsername(username);
                }
                else{
                    return null;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public User trueLogin(String username,String password){
        User user = User.getUserByUsername(username);
        try {
            if (user == null || (!user.getPassword().equals(UserController.turnPasswordToSha256(password)))) return null;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
    public String trueSignUp(String username,String password,String Nickname,String email){
        if (User.getUserByUsername(username) != null) return "username does'nt Exist";
        if (User.getUserByEmail(email) != null) return "Email does'nt Exist";
        if (!RegisterAndLoginMenu.checkPasswordErrors(password))
            return "Password is weak!";
        if (!username.matches("\\d")) return "invalid username";
        try {
            password = UserController.turnPasswordToSha256(password);
            User user = new User(username,password,Nickname,
                    email,SecurityQuestion.PET_NAME, "jelly", "I will fight");
            User.addUser(user);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return "Success!";
    }
}
