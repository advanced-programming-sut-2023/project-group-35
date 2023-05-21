package view;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;

import controller.UserController;
import model.User;
import Enum.*;
public class ProfileMenu extends Menu{
    private UserController profileMenu = new UserController();
    private User loggedInUser;

    public void run() throws NoSuchAlgorithmException, IOException {
        while (true) {
            input = scanner.nextLine();
            if ((matcher = getRealMatcher(input,Commands.CHANGE_USERNAME,Commands.USERNAME)) != null) {
                System.out.println(profileMenu.usernameChange(matcher));
            } else if((matcher = getRealMatcher(input,Commands.CHANGE_PASSWORD,Commands.PASSWORD_NOT_IN_LOGIN)) != null) {
                System.out.println(profileMenu.passwordChanger(matcher));
            } else if ((matcher = getRealMatcher(input,Commands.CHANGE_EMAIL,Commands.EMAIL)) != null) {
                System.out.println(profileMenu.emailChange(matcher));
            }else if ((matcher = Commands.getMatcher(input, Commands.CHANGE_SLOGAN)) != null ||
                    (matcher = Commands.getMatcher(input, Commands.REMOVE_SLOGAN)) != null) {
                System.out.println(profileMenu.changeOrRemoveSlogan(extractSlogan(input)));
            }else if ((matcher = getRealMatcher(input,Commands.CHANGE_NICKNAME,Commands.NICKNAME)) != null) {
                System.out.println(profileMenu.nicknameChange(matcher));
            } else if ((matcher = getRealMatcher(input, Commands.SHOW_HIGHSCORE)) != null) {
                System.out.println(profileMenu.displayHighScore());
            }else if ((matcher = getRealMatcher(input, Commands.SHOW_RANK)) != null) {
                System.out.println(profileMenu.displayRank());
            }else if ((matcher = getRealMatcher(input, Commands.SHOW_SLOGAN)) != null) {
                System.out.println(profileMenu.displaySlogan());
            }else if ((matcher = getRealMatcher(input, Commands.SHOW_INFO)) != null) {
                System.out.println(profileMenu.displayProfile());
            }else if ((matcher = Commands.getMatcher(input, Commands.BACK)) != null) {
                System.out.println("exiting profile menu...");
                return;
            }else {
                System.out.println(ResponseToUser.INVALID_COMMAND);
            }

        }

    }

    public ProfileMenu(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        profileMenu.setLoggedInUser(loggedInUser);
    }


    private String extractSlogan(String text) {
        String slogan = null;
        if (Commands.getMatcher(text, Commands.SLOGAN) != null) {
            Matcher sloganMarcher = Commands.getMatcher(text, Commands.SLOGAN);
            slogan = profileMenu.checkForQuotation(sloganMarcher.group("slogan"));
            if (slogan.length() < 1)
                return null;
        }
        return slogan;
    }




}
