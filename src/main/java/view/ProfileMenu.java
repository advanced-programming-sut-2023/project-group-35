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
            if ((matcher = Commands.getMatcher(input, Commands.CHANGE_USERNAME)) != null) {
                System.out.println(profileMenu.usernameChange(extractUsername(input)));
            } else if((matcher = Commands.getMatcher(input, Commands.CHANGE_PASSWORD)) != null) {
                System.out.println(profileMenu.passwordChanger(extractPassword(input),extractPasswordConfirm(input)));
                //TODO:new matcher
            } else if ((matcher = Commands.getMatcher(input, Commands.CHANGE_EMAIL)) != null) {
                System.out.println(profileMenu.emailChange(extractEmail(input)));
            }else if ((matcher = Commands.getMatcher(input, Commands.CHANGE_SLOGAN)) != null ||
                    (matcher = Commands.getMatcher(input, Commands.REMOVE_SLOGAN)) != null) {
                System.out.println(profileMenu.changeOrRemoveSlogan(extractSlogan(input)));
            }else if ((matcher = Commands.getMatcher(input, Commands.CHANGE_NICKNAME)) != null) {
                System.out.println(profileMenu.nicknameChange(extractNickname(input)));
            } else if ((matcher = Commands.getMatcher(input, Commands.SHOW_HIGHSCORE)) != null) {
                System.out.println(profileMenu.displayHighScore());
            }else if ((matcher = Commands.getMatcher(input, Commands.SHOW_RANK)) != null) {
                System.out.println(profileMenu.displayRank());
            }else if ((matcher = Commands.getMatcher(input, Commands.SHOW_SLOGAN)) != null) {
                System.out.println(profileMenu.displaySlogan());
            }else if ((matcher = Commands.getMatcher(input, Commands.SHOW_INFO)) != null) {
                System.out.println(profileMenu.displayProfile());
            }else if ((matcher = Commands.getMatcher(input, Commands.BACK)) != null) {
                return;
            }else {
                System.out.println("Invalid commend!");
            }

        }

    }

    public ProfileMenu(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    private String extractUsername(String text) {
        Matcher userNameMatcher = Commands.getMatcher(text, Commands.USERNAME);
        String username = profileMenu.checkForQuotation(userNameMatcher.group("username"));
        return username;
    }
    private String extractPassword(String text) {
        Matcher passwordMatcher = Commands.getMatcher(text, Commands.PASSWORD_NOT_IN_LOGIN);
        String password = profileMenu.checkForQuotation(passwordMatcher.group("password"));
        return password;

    }
    private String extractEmail(String text) {
        Matcher emailMatcher = Commands.getMatcher(text, Commands.EMAIL);
        String password = profileMenu.checkForQuotation(emailMatcher.group("email"));
        return password;
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
    private String extractPasswordConfirm(String text) {
        Matcher passwordMatcher =  Commands.getMatcher(text, Commands.PASSWORD_NOT_IN_LOGIN);
        if (passwordMatcher.group("notPassword") != null) return "random";
        else return profileMenu.checkForQuotation(passwordMatcher.group("passwordConfirm"));
    }
    private String extractNickname(String text) {
        Matcher nickNameMatcher = Commands.getMatcher(text, Commands.NICKNAME);
        String password = profileMenu.checkForQuotation(nickNameMatcher.group("nickName"));
        return password;
    }



}
