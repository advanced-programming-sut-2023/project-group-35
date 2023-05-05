package Enum;

public enum Slogans {
    FIRST("You can kill me but i won't bow"),
    SECOND("Your bones will burn in this desert"),
    THIRD("Beware of my wrath"),
    FOURTH("Ours is fury"),
    FIFTH("I'm tired of generating new slogans!");

    public String slogan;
    private Slogans(String slogan){
        this.slogan = slogan;
    }

    public String getSlogan() {
        return slogan;
    }
}
