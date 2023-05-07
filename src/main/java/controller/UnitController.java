package controller;

import Enum.*;
import model.*;
import model.people.MilitaryUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class UnitController extends GameController{
    private MilitaryUnit selectedUnit;
    private boolean isMoving;
    public UnitController(Game game) {
        super(game);
        selectedUnit = game.getSelectedUnit();
    }

    public String moveUnit(int x, int y) {
        if (!areCoordinatesCorrect(x, y)) return ResponseToUser.COORDINATES_NOT_CORRECT.response;
        if (x == selectedUnit.getBlock().x && y == selectedUnit.getBlock().y) return "troops are already in this block";
        String dest;
        if (!(dest = checkTheDestination(x, y)).equals("correct")) return dest;
        ArrayList<Block> path = findAPath(selectedUnit.getBlock(), map.getBlockByLocation(x, y));
        if(path == null) return "there is no path to this block";
        // if(unreachable) ???
        return "move unit successful. the unit is going to the location";
    }
    public ArrayList<Block> findAPath(Block start, Block dest) {
        ArrayList<Block> closed = new ArrayList<>();
        TreeMap<Integer, Block> openList = new TreeMap<>();
        HashMap<Block, Integer> gOfBlocks = new HashMap<>();
        HashMap<Block, Block> father = new HashMap<>();

        father.put(start, start);
        openList.put(0, start);
        while(true) {
            Block block = openList.firstEntry().getValue();
            closed.add(block);
            Block nextBlock;
            for (Direction value : Direction.values()) {
                nextBlock = map.getNeighborBlock(block, value);
                if (!nextBlock.isPassable()) continue;
                if (nextBlock.equals(dest)) {
                    father.put(nextBlock, block);
                    return arrayListMaker(father, dest);
                }
                int g = gOfBlocks.get(block);
                int h = getH(nextBlock, dest);
                // todo check the previous f
                openList.put(h + g, nextBlock);
                if(gOfBlocks.containsKey(nextBlock)) gOfBlocks.replace(nextBlock, g + h);
                else gOfBlocks.put(nextBlock, g + h);
                father.put(nextBlock, block);
            }

        }

    }
    public ArrayList<Block> arrayListMaker(HashMap<Block, Block> hierarchy, Block dest) {
        ArrayList<Block> output = new ArrayList<>();
        output.add(dest);
        Block currectBlock = dest;
        Block father;
        while (true) {
            father = hierarchy.get(currectBlock);
            if(father.equals(currectBlock)) break;
            output.add(0, hierarchy.get(currectBlock));
            currectBlock = father;
        }
        return output;
    }

    public int getH(Block current, Block dest) {
        int x = Math.abs(current.x - dest.x);
        int y = Math.abs(current.y - dest.y);
        if(x > y) return x;
        return y;
    }
    public String checkTheDestination(int x, int y) {
        if(!map.getBlockByLocation(x, y).getFieldType().canTroopPass) return "you can't move this unit to a "
                + map.getBlockByLocation(x, y).getFieldType().getName();
        if(!map.getBlockByLocation(x, y).getBuilding().buildingType.isPassableForTroop)
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
