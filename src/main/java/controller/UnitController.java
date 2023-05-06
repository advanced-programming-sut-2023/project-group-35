package controller;

import Enum.*;
import model.*;
import model.people.MilitaryUnit;
import view.Menu;

import java.util.regex.Matcher;

public class UnitController extends GameController{
    private MilitaryUnit selectedUnit;
    private boolean isMoving;
    public UnitController(Game game) {
        super(game);
        selectedUnit = game.getSelectedUnit();
    }

    public String moveUnit(int x, int y) {
        if (!areCoordinatesCorrect(x, y)) return ResponseToUser.COORDINATES_NOT_CORRECT.response;
        String dest;
        if (!(dest = checkTheDestination(x, y)).equals("correct")) return "you can't move this unit to " + dest;
        // if(unreachable) ???
        return "move unit successful. the unit is going to the location";
    }
    public String checkTheDestination(int x, int y) {
        if(!map.getBlockByLocation(x, y).getFieldType().canTroopPass) return "you can't move this unit to a "
                + map.getBlockByLocation(x, y).getFieldType().getName();
        if(!map.getBlockByLocation(x, y).getBuilding().buildingType.passableForTroop)
            return "units can't go to this block because of the building in the block";
        return "correct";
    }

    public String setUnitState(String state) {
        return null;
    }
    public UnitState getUnitState(String state) {
        return null;
    }

    public String attackEnemy(int x , int y) {
        return null;
    }

    public String airAttack(int x, int y) {
        return null;
    }

    public String pourOil(Direction direction) {
        return null;
    }
    public String digTunnel(int x , int y) {
        return null;
    }

    public String buildEquipment(StructuresType equipmentType) {
        return null;
    }
    public String digMoat(int x, int y) {
        return null;
    }

    public String fillMoat(int x, int y) {
        return null;
    }

    public String putOnLadder(int x, int y) {
        return null;
    }

    public String pushOffLadder(int x , int y) {
        return null;
    }
    public String climbUp(int x,int y){return null;}
    public String disbandUnit() {
        return null;
    }

    public String moveStructure() {
        return null;
    }
    public String dPS(int x,int y){return null;}

    public void deleteSelectedUnits() {
        game.setSelectedUnit(null);
    }

}
