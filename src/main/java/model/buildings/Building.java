package model.buildings;
import Enum.*;
import model.Reign;

import java.util.ArrayList;

public class Building {
    private Reign owner;
    private int hp;
    public BuildingType buildingType;

    public static ArrayList<Building> allBuildings = new ArrayList<>();
    public Building(Reign owner, int hp, BuildingType buildingType) {
        owner = owner;
        this.hp = hp;
        this.buildingType = buildingType;
    }

    public void nextTurn() {

    }
    public void getDamaged(int amountOfDamage){}
    public Reign getOwner() {
        return owner;
    }


}
