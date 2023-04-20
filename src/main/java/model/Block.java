package main.java.model;


import main.java.model.buildings.Building;
import main.java.model.people.MilitaryUnit;
import main.java.model.structures.Structure;
import main.java.Enum.*;
import java.util.ArrayList;



public class Block {
    int x;
    int y;
    private Building building;
    private ArrayList<Structure> structures;
    private ArrayList<MilitaryUnit> militaryUnits;
    public FieldType fieldType;

}
