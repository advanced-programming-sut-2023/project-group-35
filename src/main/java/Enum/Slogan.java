package Enum;

import java.util.Random;

public enum Slogan {
    NONE(""),
    YOU_CAN_KILL_ME_BUT_I_WONT_BOW("You can kill me but i won't bow"),
    YOUR_BONES_WILL_BURN_IN_THIS_DESERT("Your bones will burn in this desert"),
    BEWARE_OF_MY_WRATH("Beware of my wrath"),
    OURS_IS_FURY("Ours is fury"),
    IF_IT_DOESNT_KILL_YOU_IT_WILL_MAKE_YOU_STRONGER("if it doesn't kill you, it will make you stronger!");
    // todo make new slogans
    public final String slogan;
    private Slogan(String slogan){
        this.slogan = slogan;
    }

    public String getSlogan() {
        return slogan;
    }
    public static int getRandomSlogan () {
        Random random = new Random();
        int index = random.nextInt() % Slogan.values().length;
        if(index == 0) return 2;
        else return index;
        //return Slogan.values()[index];

    }
}
