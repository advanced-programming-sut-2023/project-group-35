package Enum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CommandEnums {
    //login and register commands
    CREATE_USER("^\\s*user\\s+create\\s+(?<contentText>.+)$"),
    USER_LOGIN("^\\s*user\\s+login\\s+(?<contentText>.+)$"),
    FORGOT_MY_PASSWORD("\\s*forgot\\s+my\\s+password\\s*"),
    USER_LOGOUT("\\s*user\\s+logout\\s*"),
    QUESTION_PICK("^\\s*question\\s+pick\\s+(?<contentText>.+)$"),
    CHANGE_PASSWORD("^-u\\s+(?<username>\\S*|(\".*\"))\\s+-p\\s+(?<password>\\S*|(\".*\"))$"),

    //login and register factors

    USERNAME("-u\\s+(?<username>\\S*|(\".*\"))"),
    PASSWORD_USED_IN_LOGIN("-p\\s+(?<password>\\S*|(\".*\"))"),
    PASSWORD_NOT_IN_LOGIN("-p\\s+((?<random>random)|((?<password>\\S*|(\".*\"))\\s+(?<passwordConfirm>\\S+|(\".+\"))))"),
    NICKNAME("-n\\s+(?<nickname>\\S*|(\".*\"))"),
    EMAIL("-e\\s+(?<email>\\S*)"),
    SLOGAN("-s\\s+((?<random>random)|(?<sloganText>\\S*|(\".*\")))"),
    QUESTION_NUMBER("-q\\s+(?<questionNumber>\\d+)"),
    ANSWER("-a\\s+(?<answerText>\\S+|(\".+\"))"),
    ANSWER_CONFIRM("-c\\s+(?<answerConfirm>\\S+|(\".+\"))");
    private String regex;
    private CommandEnums(String regex){
        this.regex = regex;
    }
    public static Matcher getMatcher(String input , CommandEnums command){
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        return (matcher.find()) ? matcher : null;
    }
}
