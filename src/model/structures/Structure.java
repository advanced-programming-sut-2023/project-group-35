package model.structures;

import model.people.Engineer;
import model.people.MilitaryUnit;

public class Structure {
    private int hp;
    private boolean moving;
    private int speed;
    private Engineer mover;


    public Structure(int hp, boolean moving, int speed, Engineer mover) {
        this.hp = hp;
        this.moving = moving;
        this.speed = speed;
        this.mover = mover;
    }
}

