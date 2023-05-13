package model.people;

import Enum.*;
import model.Block;
import model.Reign;
import model.structures.Structure;

public class Engineer extends MilitaryUnit{
    private Structure structure;
    private boolean hasStructure;

    private boolean isWorking = false;

    public boolean hasOilToPour() {
        return hasOilToPour;
    }

    public void GiveOil() {
        hasOilToPour = true;
    }
    public void outOfOil() {
        hasOilToPour = false;
    }

    private boolean hasOilToPour = false;

    public Engineer(UnitType unitType, Reign owner, Block block, int number) {
        super(unitType, owner, block, number);
    }
    public boolean isWorking() {
        return isWorking;
    }
    public void setWorking(boolean working) {
        isWorking = working;
    }
    public void setStructure(Structure structure) {
        this.structure = structure;
        super.setHp(structure.getType().getHp());
        super.setRange(structure.getType().getRange());
        super.setDamage(structure.getType().getDamage());
    }
    public Structure getStructure() {
        return this.structure;
    }
    public boolean hasStructure() {
        return hasStructure;
    }



}
