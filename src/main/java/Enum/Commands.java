package Enum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    // main menu
    START_GAME("\\s*start\\s+a\\s+new\\s+game\\s*"),
    // start game menu
    ADD_USER("\\s*add\\s*"),

    //login and register commands
    CREATE_USER("^\\s*user\\s+create\\s*"),
    USER_LOGIN("^\\s*user\\s+login\\s*"),
    FORGOT_MY_PASSWORD("\\s*forgot\\s+my\\s+password\\s*"),
    USER_LOGOUT("\\s*user\\s+logout\\s*"),


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
    SHOW_MAP("\\s*show\\s+map\\s*"),

    MOVE_MAP("move map (up(?<up> \\d+)?)\\s+(down(?<down> \\d+)?)\\s+(left(?<left> \\d+)?)\\s+(right(?<right> \\d+)?)"),
    SHOW_MAP_DETAILS("\\s*show\\s+details\\s*"),
    SET_TEXTURE("\\s*set\\s+texture\\s*"),
    SET_AREA_TEXTURE("\\s*set\\s+texture\\s*"),
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

    SELECT_BUILDING("select\\s+building\\s*"),
    SELECT_UNIT("select\\s+unit\\s*"),

    CREATE_UNIT("create\\s+unit\\s*"), // \\S+ or .+ ?
    CHANGE_TAX_RATE("change\\s+tax rate\\s*"),
    RATE("-r\\s+\\d+"),
    // TRADE MENU
    ADD_REQUEST("request\\s+"),
    AMOUNT("-a\\s+\\d+"),
    PRICE("-p\\s+\\d+"),
    MESSAGE("-m\\s+\\d+"),
    TYPE("-t \\s+\\d+"),
    DONATION("donate\\s+"),
    ACCEPT_REQUEST("request accept\\s+"),
    ID("-i\\s+\\d+"),
    DELETE_TRADE("delete trade\\s+"),
    TRADE_LIST("show trade list"),
    TRADE_HISTORY("show trade history"),

    // SHOP MENU
    PURCHASE("buy"),
    SELL("sell"),

    // back
    BACK("back"),
    //reign menu
    SHOW_POPULARITY_FACTORS("\\s*show\\s+popularity\\s+factors\\s*"),
    SHOW_POPULARITY("\\s*show\\s+popularity\\s*"),
    SHOW_FOOD_LIST("\\s*show\\s+food\\s+list\\s*"),
    FOOD_RATE("\\s*food\\s+rate\\*"),
    FOOD_RATE_SHOW("\\s*food\\s+rate\\s+show\\s*"),
    TAX_RATE("\\s*tax\\s+rate\\*"),
    TAX_RATE_SHOW("\\s*tax\\s+rate\\s+show\\s*"),
    FEAR_RATE_SHOW("\\s*fear\\s+rate\\s+show\\s*"),

    FEAR_RATE("\\s*fear\\s+rate\\s*"),
    // profile menu
    PROFILE_MENU("enter profile menu"),
    CHANGE_USERNAME("^\\s*profile\\s+change\\s*$"),
    CHANGE_NICKNAME("^\\s*profile\\s+change\\*$"),
    CHANGE_PASSWORD("^\\s*profile\\s+change\\s+password*$"),
    CHANGE_EMAIL("^\\s*profile\\s+change\\s*$"),
    CHANGE_SLOGAN("^\\s*profile\\s+change\\s+slogan\\s*$"),
    REMOVE_SLOGAN("^\\s*Profile\\s+remove\\s+slogan\\s*$"),
    SHOW_HIGHSCORE("^\\s*profile\\s+display\\s+highscore\\s*$"),
    SHOW_RANK("^\\s*profile\\s+display\\s+highscore\\s*$"),
    SHOW_SLOGAN("^\\s*profile\\s+display\\s+slogan\\s*$"),
    SHOW_INFO("^\\s*profile\\s+display\\s*$"),
    /// groups
    X("-x\\s+\\d+"),
    Y("-y\\s+\\d+"),
    ITEM("-t\\s+(\\S+|(\".+\")");

    public String regex;
    private Commands(String regex){
        this.regex = regex;
    }
    public static Matcher getMatcher(String input , Commands command){
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        return (matcher.find()) ? matcher : null;
    }


}
