package model;


import model.buildings.Building;
import model.people.MilitaryUnit;
import model.structures.Structure;
import Enum.*;
import java.util.ArrayList;



public class Block {
    int x;
    int y;

    private Building building;
    private ArrayList<Structure> structures;

    private ArrayList<MilitaryUnit> militaryUnits;

    private Tree tree;

    private FieldType fieldType;

    public Block(int x, int y, FieldType fieldType) {
        this.x = x;
        this.y = y;
        this.fieldType = fieldType;
    }

    public String BlockInfo(Boolean detailed) {
        String output = "block (" + x + ", " + y + ")";
        output += "\nfield type: " + this.fieldType;
        if(!detailed) return output;
        output += "\nbuilding: " + building.buildingType.name();
        output += "\nstructures: ";
        for (Structure structure : structures) {
            output += structure.toString();
        }
        //todo: compete this function
        return output;
    }
    public void clearBlock(Reign playing) {
        if(building.getOwner().equals(playing)) building = null;
        for (int i = structures.size() - 1; i >= 0; i++) {
           if(structures.get(i).getOwner().equals(playing)) structures.remove(i);
        }
        for(int i = militaryUnits.size() - 1; i >= 0; i++) {
            if(militaryUnits.get(i).getOwner().equals(playing)) militaryUnits.remove(i);
        }
        //todo check
    }

    public boolean isOccupied() {
        if(this.building != null) return true;
        if(this.getFieldType().equals(FieldType.Rock)) return true;
        if(this.getTree() != null) return true;
        //todo what else?
        return false;
    }

    public boolean findOpponentInBlock(Reign reign) {
        for (MilitaryUnit militaryUnit : militaryUnits) {
            if (!militaryUnit.getOwner().equals(reign)) return true;
        }
        for (Structure structure : structures) {
            if(!structure.getOwner().equals(reign)) return true;
        }
        return false;
    }

    public ArrayList<MilitaryUnit> getMilitaryUnits() {
        return militaryUnits;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public Tree getTree() {
        return tree;
    }

    public Building getBuilding() {
        return building;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }
}
