package Enum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    // main menu
    START_GAME("\\s*start\\s+a\\s+new\\s+game\\s*"),
    // start game menu
    ADD_USER("\\s*add\\s+(?<username>\\S+)\\s*"),

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
    NICKNAME("-n\\s+(?<nickName>\\S*|(\".*\"))"),
    EMAIL("-e\\s+(?<email>\\S*)"),
    SLOGAN("-s\\s+((?<random>random)|(?<slogan>\\S*|(\".*\")))"),
    ISNUMERIC("-?\\d+(\\.\\d+)?"),



    // mapMenu
    MAP_MENU("enter map menu"),
    //SHOW_MAP("show map -x (?<x>\\d+) -y (?<y>\\d+)"),
    SHOW_MAP("^\\s*show\\s+map\\s+"),

    MOVE_MAP("move map (up(?<up> \\d+)?)\\s+(down(?<down> \\d+)?)\\s+(left(?<left> \\d+)?)\\s+(right(?<right> \\d+)?)"),
    SHOW_MAP_DETAILS("show details -x (?<x>\\d+) -y (?<y>\\d+)"),
    SET_TEXTURE("set texture -x (?<x>\\d+) -y (?<y>\\d+) -t (?<type>\\S+)"),
    SET_AREA_TEXTURE("set texture -x1 (?<x1>\\d+) -y1 (?<y1>\\d+) -x2 (?<x2>\\d+) -y2 (?<y2>\\d+) -t (?<type>\\S+)"),
    X1("-x1\\s+\\d+"),
    X2("-x2\\s+\\d+"),
    Y1("-y1\\s+\\d+"),
    Y2("-y2\\s+\\d+"),
    CLEAR("clear -x (?<x>\\d+) -y (?<y>\\d+)"),
    DROP_ROCK("drop rock -x (?<x>\\d+) -y (?<y>\\d+) -d (?<direction>\\S+)"),
    DIRECTION("-d\\s+\\S+"), //
    DROP_TREE("drop tree -x (?<x>\\d+) -y (?<y>\\d+) -t (?<tree>\\S+)"),
    TREE("-t\\s+\\S+"),
    //DROP_BUILDING("drop building -x (?<x>\\d+) -y (?<y>\\d+) -t (?<type>\\S+)"),
    DROP_BUILDING("^\\s*drop\\s+building\\s+"),
    DROP_UNIT("drop unit -x (?<x>\\d+) -y (?<y>\\d+) -t (?<type>\\S) -c (?<count>\\d+)"),
    COUNT("-c\\s+\\d+"),
    // select building menu
    SELECT_BUILDING("select building -x (?<x>\\d+) -y (?<y>\\d+)"),
    SELECT_UNIT("\\s*select\\s+unit\\s*"),
    CREATE_UNIT("create unit -t (?<type>\\.+) -c (?<count>\\d+)"), // \\S+ or .+ ?
    CHANGE_TAX_RATE("change tax rate -r (?<rate>\\d+)"),
    RATE("-r\\s+\\d+"),
    // select unit menu
    MOVE_UNIT("\\s*move\\s+unit\\s+to\\s*"),
    PATROL_UNIT("\\s*patrol\\s+unit\\s*"),
    SET_UNIT_STATE("\\s*set\\s+state\\s+(?<state>\\S+)\\s+"),
    ATTACK_ENEMY("\\s*attack\\s+enemy\\s*"),

    // TRADE MENU
    ADD_REQUEST("request -t (?<type>\\S+) -a (?<amount>\\d+) -p (?<price>\\d+) -m (?<message>.+)"),
    AMOUNT("-a\\s+\\d+"),
    PRICE("-p\\s+\\d+"),
    MESSAGE("-m\\s+\\d+"),
    DONATION("donate -t (?<type>\\S+) -a (?<amount>\\d+) -m (?<message>.+)"),
    ACCEPT_REQUEST("request accept -i (?<id>\\d+) -m (?<message>\\.+)"),
    ID("-i\\s+\\d+"),
    DELETE_TRADE("delete trade -i (?<id>\\d+)"),
    TRADE_LIST("show trade list"),
    TRADE_HISTORY("show trade history"),

    // SHOP MENU
    PURCHASE("buy -i (?<item>\\S+) -a (?<amount>\\d+)"),
    SELL("sell -i (?<item>\\S+) -a (?<amount>\\d+)"),

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
    SHOW_INFO("^\\s*profile\\s+display\\s*$"),
    /// groups
    X("-x\\s+\\d+"),
    Y("-y\\s+\\d+"),
    TYPE("-t\\s+(\\S+|(\".+\")"),

    // back
    BACK("back");

    public String regex;
    private Commands(String regex){
        this.regex = regex;
    }
    public static Matcher getMatcher(String input , Commands command){
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        return (matcher.find()) ? matcher : null;
    }


}
