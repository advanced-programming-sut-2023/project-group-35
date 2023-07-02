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
    private ArrayList<Structure> structures;
    private ArrayList<MilitaryUnit> militaryUnits;
    private boolean hasBase = false;
    private Tree tree;

    private FieldType fieldType;

    public Block(int x, int y, FieldType fieldType) {
        this.x = x;
        this.y = y;
        this.fieldType = fieldType;
        this.structures = new ArrayList<>();
        this.militaryUnits = new ArrayList<>();
    }

    public String getBlockInfo(Boolean detailed) {
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
        output += "\nmilitaryUnits: ";
        for(MilitaryUnit militaryUnit: this.militaryUnits){
            output += militaryUnit.toString() + "\n";
        }
        output += "\nstructures: ";

        for (Structure structure : this.structures) {
            output += structure.toString() + "\n";
        }

        return output;
    }
    public void clearBlock(Reign playing) {
        if(building != null && building.getOwner().equals(playing)) building = null;
        if(structures == null) structures = new ArrayList<>();
        if(militaryUnits == null) militaryUnits = new ArrayList<>();
        for (int i = structures.size() - 1; i >= 0; i--) {
                if (structures.get(i).getOwner().equals(playing)) structures.remove(i);
            }
            for (int i = militaryUnits.size() - 1; i >= 0; i--) {
                if (militaryUnits.get(i).getOwner().equals(playing)) militaryUnits.remove(i);
            }
        this.setFieldType(FieldType.Ground);
    }

    public boolean isOccupied(Map map) {
        if(this.building != null) {
            System.out.println("building");
            return true;
        }
        if(this.getFieldType().equals(FieldType.Rock)) {
            System.out.println("rock");
            return true;
        }
        if(this.getTree() != null) {
            System.out.println("tree");
            return true;
        }
        if(!this.getFieldType().isSuitableForBuilding) {
            System.out.println("not suitable");
            return true;
        }
        for (Block baseBlock : map.getBaseBlocks()) {
            if(baseBlock.equals(this)){
                System.out.println("base");
                return true;
            }
        }
        return false;
    }

    public boolean findOpponentInBlock(Reign reign) {
        if(militaryUnits == null) militaryUnits = new ArrayList<>();
        if(structures == null) structures = new ArrayList<>();
        for (MilitaryUnit militaryUnit : militaryUnits) {
            if (!militaryUnit.getOwner().equals(reign)) return true;
        }
        for (Structure structure : structures) {
            if(!structure.getOwner().equals(reign)) return true;
        }
        return false;
    }

    public boolean isPassable() {

        if (this.hasABuilding() && !this.building.buildingType.isPassableForTroop) return false;
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

    public ArrayList<GameTabMenuMode> getModesOfBlock() {
        ArrayList<GameTabMenuMode> modes = new ArrayList<>();
        if(this.hasABuilding()) modes.add(GameTabMenuMode.BUILDING_SELECTED);
        for (MilitaryUnit unit : militaryUnits) {
            modes.add(GameTabMenuMode.UNIT_SELECTED);
        }
        return modes;
    }


    public ArrayList<MilitaryUnit> getMilitaryUnits() {
        if ( militaryUnits == null) militaryUnits = new ArrayList<>();
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
        if(building != null && building.getBuildingType().equals(BuildingType.KILLING_PIT)) return true;
        return false;
    }
    public boolean hasABuilding() {
        return (building != null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void addNewStructure(Structure structure){
        if(structures == null) structures = new ArrayList<>();
        structures.add(structure);
    }
    public void removeStructure(Structure structure){
        if(structures == null) structures = new ArrayList<>();
        structures.remove(structure);
    }
    public ArrayList<Structure> getStructures() {
        if(this.structures == null) {this.structures = new ArrayList<>();}
        return this.structures;
    }

    public void removeUnit(MilitaryUnit militaryUnit) {
        if (militaryUnit == null) militaryUnits = new ArrayList<>();
        militaryUnits.remove(militaryUnit);
    }
    public void addUnit(MilitaryUnit militaryUnit){
        if(militaryUnit == null) militaryUnits = new ArrayList<>();
        militaryUnits.add(militaryUnit);
    }
    public void removeBuilding() {
        building = null;
    }

    public boolean hasBase() {
        return hasBase;
    }

    public void setHasBase(boolean hasBase) {
        this.hasBase = hasBase;
    }

    public void setStructures(ArrayList<Structure> structures) {
        this.structures = structures;
    }

    public void setMilitaryUnits(ArrayList<MilitaryUnit> militaryUnits) {
        this.militaryUnits = militaryUnits;

    }

}
