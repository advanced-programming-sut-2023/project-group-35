package main.java.Enum;

public enum Commands {
    MAP_MENU("enter map menu");
    public String regex;

    Commands(String regex) {
        this.regex = regex;
    }
}
