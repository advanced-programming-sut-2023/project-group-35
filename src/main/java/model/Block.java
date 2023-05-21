package model;


import model.buildings.Building;
import model.people.MilitaryUnit;
import model.structures.Structure;
import Enum.*;
import java.util.ArrayList;



public class Block {
    public int x;
    public int y;

    private Building building;
    private final ArrayList<Structure> structures = new ArrayList<>();

    private final ArrayList<MilitaryUnit> militaryUnits = new ArrayList<>();
    private boolean hasBase = false;

    public boolean isHasBase() {
        return hasBase;
    }

    public void setHasBase(boolean hasBase) {
        this.hasBase = hasBase;
    }

    private Tree tree;

    private FieldType fieldType;

    public Block(int x, int y, FieldType fieldType) {
        this.x = x;
        this.y = y;
        this.fieldType = fieldType;
    }

    public String BlockInfo(Boolean detailed) {
        boolean hasBuilding = false;
        if(building != null)
            hasBuilding = true;
        String output = "block (" + x + ", " + y + ")";
        output += "\nfield type: " + this.fieldType;
        if(!detailed) return output;
        if(hasBuilding)
        output += "\nbuilding: " + building.buildingType.name();
        else{
            output += "\nbuilding: nothing!";
        }
        output += "\nstructures: ";

            for (Structure structure : this.structures) {
                output += structure.toString();
            }

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
        if(!this.getFieldType().isSuitableForBuilding) return true;
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

    public boolean isPassable() {
        if (!this.building.buildingType.isPassableForTroop) return false;
        return this.fieldType.canTroopPass;
    }

    public boolean canPutStructure(StructuresType type) {
        if (!this.fieldType.isSuitableForBuilding) return false;
        if (this.hasABuilding()) {
            if(type.checkForEquals(StructuresType.MOVING_SHIELD, StructuresType.WALL_BREAKER, StructuresType.SIEGE_TOWER)) return false;
            if(!(this.building.buildingType.equals(BuildingType.CIRCLE_TOWER) ||
                    this.building.buildingType.equals(BuildingType.SQUARE_TOWER))) return false;
        }
        return true;
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
    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }


    public boolean isATrapFor(MilitaryUnit unit) {
        return false; //todo complete
    }
    public boolean hasABuilding() {
        return building != null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void addNewStructure(Structure structure){
        structures.add(structure);
    }
    public void removeStructure(Structure structure){
        structures.remove(structure);
    }
    public ArrayList<Structure> getStructures() {
        return structures;
    }

    public void removeUnit(MilitaryUnit militaryUnit) {
        militaryUnits.remove(militaryUnit);
    }
    public void addUnit(MilitaryUnit militaryUnit){
        militaryUnits.add(militaryUnit);
    }
    public void removeBuilding() {
        building = null;
    }


}
