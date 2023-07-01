package Server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RegEx {
    CREATE_GROUP("create group -name (?<name>\\S+)"),
    CREATE_PV("create pv"),
    GET_CHAT("get chat -id (?<id>\\d+)"),
    UPDATE_CHAT("update chat"),
    ADD_USER("add user to group -id (?<id>\\d+) -u (?<username>\\S+)"),
    GET_USER("get user -username (?<username>\\S+)"),
    USER_LOGIN("login -u (?<username>\\S+) -p (?<password>\\S+)"),
    USER_CREATE("sign up -u (?<username>\\S+) -n (?<nickname>\\S+) -p (?<password>\\S+) -e (?<email>\\S+)"),
    GET_PUBLIC_CHAT("get public chat"),
    SHOW_MESSAGES("\\s*show\\s*messages\\s*"),
    SEND_MESSAGE("\\s*send\\s+-m (?<message>.*)\\s*"),
    DELETE_MESSAGE("\\s*delete message -i (?<id>\\d+)\\s*"),
    EDIT_MESSAGE("\\s*edit message -i (?<id>\\d+) -m (?<newContent>.*)\\s*"),
    GET_CHATS("get chats");


    private final String input;

    RegEx(String input) {
        this.input = input;
    }
    public static String get(RegEx regEx){
        return regEx.input;
    }
    public static Matcher getMatcher(String input , RegEx command){
        Matcher matcher = Pattern.compile(command.input).matcher(input);
        return (matcher.find()) ? matcher : null;
    }
}
