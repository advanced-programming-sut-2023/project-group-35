package Enum;

import java.util.Random;

public enum Slogan {
    FIRST("You can kill me but i won't bow"),
    SECOND("Your bones will burn in this desert"),
    THIRD("Beware of my wrath"),
    FOURTH("Ours is fury"),
    FIFTH("I'm tired of generating new slogans!");
    // todo make new slogans
    public final String slogan;
    private Slogan(String slogan){
        this.slogan = slogan;
    }

    public String getSlogan() {
        return slogan;
    }
    public static Slogan getRandomSlogan () {
        Random random = new Random();
        int index = random.nextInt() % Slogan.values().length;
        return Slogan.values()[index];

    }
}
