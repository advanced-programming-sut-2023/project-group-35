package model;


import model.buildings.Building;
import model.people.MilitaryUnit;
import model.structures.Structure;

import java.awt.*;
import java.util.ArrayList;
import Enum.FieldType;

public class Block {
    int x;
    int y;
    private Building building;
    private ArrayList<Structure> structures;
    private ArrayList<MilitaryUnit> militaryUnits;
    public FieldType fieldType;

}
