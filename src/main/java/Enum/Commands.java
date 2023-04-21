package Enum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
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
    ANSWER_CONFIRM("-c\\s+(?<answerConfirm>\\S+|(\".+\"))"),

    // mapMenu
    MAP_MENU("enter map menu"),
    SHOW_MAP("show map -x (?<x>\\d+) -y (?<y>\\d+)"),
    MOVE_MAP("move map (up(?<up> \\d+)?)\\s+(down(?<down> \\d+)?)\\s+(left(?<left> \\d+)?)\\s+(right(?<right> \\d+)?)"),
    SHOW_MAP_DETAILS("show details -x (?<x>\\d+) -y (?<y>\\d+)"),
    SET_TEXTURE("set texture -x (?<x>\\d+) -y (?<y>\\d+) -t (?<type>\\S+)"),
    SET_AREA_TEXTURE("set texture -x1 (?<x1>\\d+) -y1 (?<y1>\\d+) -x2 (?<x2>\\d+) -y2 (?<y2>\\d+) -t (?<type>\\S+)"),
    CLEAR("clear -x (?<x>\\d+) -y (?<y>\\d+)"),
    DROP_ROCK("drop rock -x (?<x>\\d+) -y (?<y>\\d+) -d (?<direction>\\S)"),
    DROP_TREE("drop tree -x (?<x>\\d+) -y (?<y>\\d+) -t (?<tree>\\S+)"),
    DROP_BUILDING("drop building -x (?<x>\\d+) -y (?<y>\\d+) -t (?<type>\\S+)"),
    DROP_UNIT("drop unit -x (?<x>\\d+) -y (?<y>\\d+) -t (?<type>\\S) -c (?<count>\\d+)");


    public String regex;
    private Commands(String regex){
        this.regex = regex;
    }
    public static Matcher getMatcher(String input , Commands command){
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        return (matcher.find()) ? matcher : null;
    }
}
