package Tests;

import controller.UserController;
import model.User;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import view.Menu;
import view.RegisterAndLoginMenu;
import Enum.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ExtendWith(MockitoExtension.class)

public class RegisterTest {
    @InjectMocks
    UserController userController = new UserController();
    @org.testng.annotations.Test
    public void passwordValidationTest() {
        assertEquals("Your Password doesn't has any uppercase letter!",
                RegisterAndLoginMenu.checkPasswordErrors("amirhoma1@!"));

        assertEquals("Your Password doesn't has any number!",
                RegisterAndLoginMenu.checkPasswordErrors("amirhom@!"));

        assertEquals("Password Is Short!",
                RegisterAndLoginMenu.checkPasswordErrors("amir"));

        assertEquals("Your Password doesn't has any special character!",
                RegisterAndLoginMenu.checkPasswordErrors("amirHoma11"));

        assertEquals("Your Password doesn't has any lowercase letter!",
                RegisterAndLoginMenu.checkPasswordErrors("AMIRHOMA1!!1"));
    }
    @Test
    public void errorsInRegister() throws IOException, NoSuchAlgorithmException {
        String matcherS1 = "create user -u amirhoma@ -p Amirword1!@ Amirword1!@ -n nikoo -e Amir@amir.com";
        Matcher matcher1 = Menu.getRealMatcher(matcherS1,Commands.CREATE_USER,Commands.USERNAME,Commands.PASSWORD_NOT_IN_LOGIN,Commands.NICKNAME,Commands.EMAIL);
        String matcherS2 = "create user -u amirhomai -p Amirword1!@ Amirword18@ -n nikoo -e Amir@amir.com";
        Matcher matcher2 = Menu.getRealMatcher(matcherS2,Commands.CREATE_USER,Commands.USERNAME,Commands.PASSWORD_NOT_IN_LOGIN,Commands.NICKNAME,Commands.EMAIL);
        String matcherS3 = "create user -u amirhomai -p Amirword1!@ Amirword1!@ -n nikoo -e Amir@amir.aom";
        Matcher matcher3 = Menu.getRealMatcher(matcherS3,Commands.CREATE_USER,Commands.USERNAME,Commands.PASSWORD_NOT_IN_LOGIN,Commands.NICKNAME,Commands.EMAIL);
        String matcherS4 = "create user -u amirhomai -p Amirword1!@ Amirword1!@ -n nikoo -e Amiramir.ao@m";
        Matcher matcher4 = Menu.getRealMatcher(matcherS4,Commands.CREATE_USER,Commands.USERNAME,Commands.PASSWORD_NOT_IN_LOGIN,Commands.NICKNAME,Commands.EMAIL);

        User user = new User("amirhoma","Amirword1!@","nikoo","Amir@amir.aom",SecurityQuestion.FATHER_NAME,"baqer","Im tired");
        User.addUser(user);
      /* assertEquals("Invalid username format!",
                userController.register(matcher1,null));
       assertEquals("You didn't repeat the password correctly",
                userController.register(matcher2,null));
       assertEquals("Email already exists in Server!",
                userController.register(matcher3,null));
       assertEquals("Invalid Email format",
                userController.register(matcher4,null));*/
    }
  /*  @Test
    public void randomPasswordValidationCheck() {
        assertTrue(RegisterAndLoginMenu.getRandomPassword().matches(".*[A-Z].*"));
        assertTrue(RegisterAndLoginMenu.getRandomPassword().matches(".*[a-z].*"));
       assertTrue(RegisterAndLoginMenu.getRandomPassword().matches(".*\\d+.*"));
       assertTrue(RegisterAndLoginMenu.getRandomPassword().matches(".*[!@#$%^&*_=+\\-/.].*"));
    }*/

    @Test
    public void randomSloganValidationCheck() {
        String[] Slogans = {
                "You can kill me but i won't bow",
                "Your bones will burn in this desert",
                "Beware of my wrath",
                "Ours is fury",
                "I'm tired of generating new slogans!",
                "this shouldn't happens"
        };
        assertTrue(Arrays.asList(Slogans).contains(userController.generateRandomSlogan()));
    }

}
