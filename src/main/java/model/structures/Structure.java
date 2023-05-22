package model.structures;

import model.*;
import model.people.*;
import Enum.*;
public class Structure {
    private StructuresType type;
    private Reign owner;
    private Engineer engineer;
    private int hp;

    private Block block;
    private boolean moving;



    public Structure(StructuresType type, Reign owner, Engineer mover, Block block) {
        this.type = type;
        this.owner = owner;
        this.engineer = mover;
        this.hp = type.getHp();
        this.block = block;
    }

    public StructuresType getType() {
        return type;
    }

    public Engineer getEngineer() {
        return engineer;
    }

    public int getHp() {
        return hp;
    }

    public Reign getOwner() {
        return owner;
    }
//    public void getDamagedAndDestroy(int damage) {
//        this.hp -= damage;
//        if(hp <= 0) {
//            this.block.removeStructure(this);
//        }
//    } // todo delete structure methods

    @Override
    public String toString() {
        return "type: " + type + "owner: " + owner;
    }

}

