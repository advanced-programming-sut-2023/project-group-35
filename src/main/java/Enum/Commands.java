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
    DROP_UNIT("drop unit -x (?<x>\\d+) -y (?<y>\\d+) -t (?<type>\\S) -c (?<count>\\d+)"),

    // TRADE MENU
    ADD_REQUEST("request -t (?<type>\\S+) -a (?<amount>\\d+) -p (?<price>\\d+) -m (?<message>.+)"),
    DONATION("donate -t (?<resourceType>\\S+) -a (?<resourceAmount>\\d+) -m (?<message>.+)"),
    ACCEPT_REQUEST("request accept -i (?<id>\\d+) -m (?<message>\\.+)"),
    TRADE_LIST("show trade list"),
    TRADE_HISTORY("show trade history"),

    // SHOP MENU
    PURCHASE("buy -i (?<item>\\S+) -a (?<amount>\\d+)"),
    SELL("sell -i (?<item>\\S+) -a (?<amount>\\d+)"),

    // back
    BACK("back"),
    //reign menu
    SHOW_POPULARITY_FACTORS("\\s*show\\s+popularity\\s+factors\\s*"),
    SHOW_POPULARITY("\\s*show\\s+popularity\\s*"),
    SHOW_FOOD_LIST("\\s*show\\s+food\\s+list\\s*"),
    FOOD_RATE("\\s*food\\s+rate\\s+-r\\s+(?<rate>\\d+)\\s*"),
    FOOD_RATE_SHOW("\\s*food\\s+rate\\s+show\\s*"),
    TAX_RATE("\\s*tax\\s+rate\\s+-r\\s+(?<rate>\\d+)\\s*"),
    TAX_RATE_SHOW("\\s*tax\\s+rate\\s+show\\s*"),
    FEAR_RATE_SHOW("\\s*fear\\s+rate\\s+show\\s*"),

    FEAR_RATE("\\s*fear\\s+rate\\s+-r\\s+(?<rate>\\d+)\\s*"),
    // profile menu
    PROFILE_MENU("enter profile menu"),
    CHANGE_USERNAME("^\\s*profile\\s+change\\s+-u\\s+(?<username>\\S*|(\".*\"))\\s*$"),
    CHANGE_NICKNAME("^\\s*profile\\s+change\\s+-n\\s+(?<nickname>\\S*|(\".*\"))\\s*$"),
    CHANGE_PASSWORD("^\\s*profile\\s+change\\s+password\\s+(-o\\s+(?<old>\\S*|(\".*\"))\\s+-n\\s+(?<new>\\S*|(\".*\")))\\s*$"),
    CHANGE_EMAIL("^\\s*profile\\s+change\\s+-e\\s+(?<email>\\S*)\\s*$"),
    CHANGE_SLOGAN("^\\s*profile\\s+change\\s+slogan\\s+-s\\s+(?<slogan>\\S*|(\".*\"))\\s*$"),
    REMOVE_SLOGAN("^\\s*Profile\\s+remove\\s+slogan\\s*$"),
    SHOW_HIGHSCORE("^\\s*profile\\s+display\\s+highscore\\s*$"),
    SHOW_RANK("^\\s*profile\\s+display\\s+highscore\\s*$"),
    SHOW_SLOGAN("^\\s*profile\\s+display\\s+slogan\\s*$"),
    SHOW_INFO("^\\s*profile\\s+display\\s*$");

    public String regex;
    private Commands(String regex){
        this.regex = regex;
    }
    public static Matcher getMatcher(String input , Commands command){
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        return (matcher.find()) ? matcher : null;
    }
}
