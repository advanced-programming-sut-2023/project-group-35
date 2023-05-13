package Enum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    // main menu
    START_GAME("\\s*start\\s+a\\s+new\\s+game\\s*"),
    // start game menu
    ADD_USER("\\s*add\\s*"),
    SAVE("\\s*save\\s*"),

    //login and register commands
    CREATE_USER("^\\s*create\\s+user\\s*"),
    USER_LOGIN("^\\s*user\\s+login\\s*"),
    FORGOT_MY_PASSWORD("\\s*forgot\\s+my\\s+password\\s*"),
    USER_LOGOUT("\\s*user\\s+logout\\s*"),


    //login and register factors
    USERNAME("-u\\s+(?<username>\\S+|(\"[^\"]+\"))"),
    PASSWORD_USED_IN_LOGIN("-p\\s+(?<password>\\S+|(\"[^\"]+\"))"),
    PASSWORD_NOT_IN_LOGIN("-p\\s+((?<random>random)|((?<password>\\S+|(\"[^\"]+\"))\\s+(?<passwordConfirm>\\S+|(\"[^\"]+\"))))"),
    NICKNAME("-n\\s+(?<nickName>\\S+|(\"[^\"]+\"))"),
    EMAIL("-e\\s+(?<email>\\S*)"),
    SLOGAN("-s\\s+((?<random>random)|(?<slogan>\\S+|(\"[^\"]+\")))"),
    ISNUMERIC("-?\\d+(\\.\\d+)?"),



    // mapMenu
    MAP_MENU("^\\s*enter\\s+map\\s+menu\\s*"),
    //SHOW_MAP("show map -x (?<x>\\d+) -y (?<y>\\d+)"),
    SHOW_MAP("^\\s*show\\s+map\\s*"),

    MOVE_MAP("move map (up(?<up> \\d+)?)\\s+(down(?<down> \\d+)?)\\s+(left(?<left> \\d+)?)\\s+(right(?<right> \\d+)?)"),
    SHOW_MAP_DETAILS("^\\s*show\\s+details\\s*"),
    SET_TEXTURE("^\\s*set\\s+texture\\s*"),
    SET_AREA_TEXTURE("^\\s*set\\s+texture\\s*"),
    X1("-x1\\s+(?<x1>\\d+)"),
    X2("-x2\\s+(?<x2>\\d+)"),
    Y1("-y1\\s+(?<y1>\\d+)"),
    Y2("-y2\\s+(?<y2>\\d+)"),
    CLEAR("^\\s*clear\\s*"),
    DROP_ROCK("^\\s*drop\\s+rock\\s*"),
    DIRECTION("-d\\s+(?<direction>\\S+|(\\S+\\s+\\S+))"), //
    DROP_TREE("^\\s*drop\\s+tree\\s*"),
    TREE("-t\\s+(?<tree>\\S+)"),
    //DROP_BUILDING("drop building -x (?<x>\\d+) -y (?<y>\\d+) -t (?<type>\\S+)"),

    DROP_BUILDING("^\\s*drop\\s+building\\s*"),
    DROP_UNIT("^\\s*drop\\s+unit\\s*"),
    COUNT("-c\\s+(?<count>\\d+)"),
    // GAME MENU
    SELECT_BUILDING("^\\s*select\\s+building\\s*"),
    SELECT_UNIT("^\\s*select\\s+unit\\s*"),
    SHOW_TURNS_PASSED("\\s*show\\s+turns\\s+passed\\s*"),
    QUIT_GAME("\\s*quit\\s+game\\s*"),

     //select unit menu
    MOVE_UNIT("^\\s*move\\s+unit\\s+to\\s*"),
    MOVE_STRUCTURE("\\s*move\\s+structure\\s*"),
    BUILD_STRUCTURE("\\s*build\\s+equipment\\s*"),
    PATROL_UNIT("^\\s*patrol\\s+unit\\s*"),
    SET_UNIT_STATE("^\\s*set\\s+state\\s+(?<state>\\S+)\\s*"),
    GET_UNIT_STATE("\\s*get\\s+unit\\s+state\\s*"),
    ATTACK_ENEMY("^\\s*attack\\s+enemy\\s*"),
    DIG_MOAT("\\s*dig\\s+moat\\s*"),
    FILL_MOAT("\\s*fill\\s+moat\\s*"),
    POUR_OIL("\\s*pour\\s+oil\\s*"),
    DIG_TUNNEL("\\s*dig\\s+tunnel\\s*"),
    PUT_ON_LADDER("\\s*put\\s+on\\s+ladder\\s*"),
    PUSH_OFF_LADDER("\\s*push\\s+off\\s+ladder\\s*"),
    DISBAND_UNIT("\\S*disband\\s+unit\\s*"),
    EQUIPMENT("-e\\s+(?<equipment>\\S+|(\"[^\"]+\"))"),
    CREATE_UNIT("^\\s*create\\s+unit\\s*"), // \\S+ or .+ ?
    CHANGE_TAX_RATE("^\\s*change\\s+tax\\s+rate\\s*"),
    RATE("-r\\s+(?<rate>\\d+)"),

    // TRADE MENU
    ADD_REQUEST("^\\s*request"),
    AMOUNT("-a\\s+(?<amount>\\d+)"),
    PRICE("-p\\s+(?<price>\\d+)"),
    MESSAGE("-m\\s+(?<message>\\S+|(\"[^\"]+\"))"),
    TYPE("-t\\s+(?<type>\\S+|(\"[^\"]+\"))"),
    DONATION("^\\s*donate"),
    ACCEPT_REQUEST("^\\s*request\\s+accept"),
    ID("-i\\s+\\d+"),
    DELETE_TRADE("^\\s*delete\\s+trade\\s*"),
    TRADE_LIST("\\s*show\\s+trade\\s+list\\s*"),
    TRADE_HISTORY("\\s*show\\s+trade\\s+history\\s*"),

    // SHOP MENU
    PURCHASE("^\\s*buy"),
    SELL("^\\s*sell"),

    // back
    BACK("\\s*back\\s*"),
    EXIT("\\S*exit\\s*"),
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
    PROFILE_MENU("\\s*enter\\s+profile\\s+menu\\s*"),
    CHANGE_USERNAME("\\s*profile\\s+change\\s*"),
    CHANGE_NICKNAME("\\s*profile\\s+change\\s*"),
    CHANGE_PASSWORD("\\s*profile\\s+change\\s+password\\s*"),
    CHANGE_EMAIL("\\s*profile\\s+change\\s*"),
    CHANGE_SLOGAN("\\s*profile\\s+change\\s+slogan\\s*"),
    REMOVE_SLOGAN("\\s*Profile\\s+remove\\s+slogan\\s*"),
    SHOW_HIGHSCORE("\\s*profile\\s+display\\s+high\\*score\\s*"),
    SHOW_RANK("\\s*profile\\s+display\\s+high\\s*score\\s*$"),
    SHOW_SLOGAN("\\s*profile\\s+display\\s+slogan\\s*"),
    SHOW_INFO("\\s*profile\\s+display\\s*"),
    /// groups
    X("-x\\s+(?<x>\\d+)"),
    Y("-y\\s+(?<y>\\d+)"),
    ITEM("-t\\s+(?<item>\\S+|(\"[^\"]+\"))"),
    REMOVE_BASE("\\s*remove\\s+base\\s*");

    public String regex;
    private Commands(String regex){
        this.regex = regex;
    }
    public static Matcher getMatcher(String input , Commands command){
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        return (matcher.find()) ? matcher : null;
    }


}
