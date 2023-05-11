package model.structures;

import model.people.Engineer;
import model.people.MilitaryUnit;
import model.Reign;

public class Structure {

    private Reign owner;
    private int hp;
    private boolean moving;
    private int speed;
    private MilitaryUnit mover;



    public Structure(int hp, boolean moving, int speed, MilitaryUnit mover) {
        this.hp = hp;
        this.moving = moving;
        this.speed = speed;
        this.mover = mover;
    }

    public Reign getOwner() {
        return owner;
    }


}

