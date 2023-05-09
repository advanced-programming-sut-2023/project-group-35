package controller;

import Enum.*;
import model.*;
import model.people.MilitaryUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class UnitController extends GameController{
    private MilitaryUnit selectedUnit;
    private boolean isMoving;
    public UnitController(Game game) {
        super(game);
        selectedUnit = game.getSelectedUnit();
    }

    public String moveUnitCommand(int x, int y) {
        if (!areCoordinatesCorrect(x, y)) return ResponseToUser.COORDINATES_NOT_CORRECT.response;
        if (x == selectedUnit.getBlock().x && y == selectedUnit.getBlock().y) return "troops are already in this block";
        String destMessage;
        if (!(destMessage = checkTheDestination(x, y)).equals("correct")) return destMessage;
        selectedUnit.setDestination(map.getBlockByLocation(x, y));
        String result = move(selectedUnit);
        if(result.equals("no path")) return "there is no path to this block";
        if(result.equals("killed")) return "the Military unit was killed by a trap";
        if(result.equals("arrived")) return "move unit successful. the unit is in the dest block";
        return "the unit is moving toward the destination block";
    }

    public String patrolUnit(int x1, int x2, int y1, int y2) {
        if(!areCoordinatesCorrect(x1, y2) || !areCoordinatesCorrect(x2, y2))
            return ResponseToUser.COORDINATES_NOT_CORRECT.response;
        if(!checkTheDestination(x1, y1).equals("correct") || !checkTheDestination(x2, y2).equals("correct"))
            return "you can't go into one of these blocks";
        //todo check if the unit can patrol
        if (findAPath(map.getBlockByLocation(x1, y1), map.getBlockByLocation(x2, y2)) == null)
            return "there is no path to connect these blocks";
        if (findAPath(selectedUnit.getBlock(), map.getBlockByLocation(x1, y1)) == null)
            return "there is no path to go to the first block";
        selectedUnit.setDestination(map.getBlockByLocation(x1, y1));
        selectedUnit.setSecondDestBlock(map.getBlockByLocation(x2, y2));
        String result = patrol(selectedUnit);
        if(result.equals("killed")) return "unit was killed by a trap";
        return "the patrol has begun";
    }
    public String patrol(MilitaryUnit unit) {
        String result = move(unit);
        if(result.equals("no path") || result.equals("killed")) unit.stopMoving();
        if(result.equals("arrived")) unit.changeDestForPatrol();
        return result;
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
            openList.remove(openList.firstEntry().getKey(), openList.firstEntry().getValue());
            closed.add(block);
            Block nextBlock;
            for (Direction value : Direction.values()) {
                nextBlock = map.getNeighborBlock(block, value);
                if(nextBlock == null) continue;
                if (!nextBlock.isPassable()) continue;
                if(closed.contains(nextBlock)) continue;
                if (nextBlock.equals(dest)) {
                    father.put(nextBlock, block);
                    return arrayListMaker(father, dest);
                }
                int g = gOfBlocks.get(block) + 1;
                int h = getH(nextBlock, dest);
                // todo check the previous f
                openList.put(h + g, nextBlock);
                if(gOfBlocks.containsKey(nextBlock)) gOfBlocks.replace(nextBlock, g);
                else gOfBlocks.put(nextBlock, g);
                father.put(nextBlock, block);
            }
            if(openList.size() == 0) return null;
        }

    }

    public String move(MilitaryUnit unit) {
        ArrayList<Block> path = findAPath(unit.getBlock(), unit.getDestBlock());
        if(path == null) return "no path";
        for(int i = 1; i<= unit.unitType.speed; i++) {
            unit.moveTo(path.get(i));
            if(path.get(i).isATrapFor(unit)){
                game.removeUnit(unit);
                return "killed";
            }
            if(path.get(i).equals(unit.getDestBlock())) return "arrived";
        }
        return "still moving";
    }

    public ArrayList<Block> arrayListMaker(HashMap<Block, Block> father, Block dest) {
        ArrayList<Block> output = new ArrayList<>();
        output.add(dest);
        Block currectBlock = dest;
        Block fatherB;
        while (true) {
            fatherB = father.get(currectBlock);
            if(father.equals(currectBlock)) break;
            output.add(0, fatherB);
            currectBlock = fatherB;
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
        UnitState unitState = UnitState.getUnitState(state);
        if(unitState.equals(selectedUnit.getUnitState())) return "the unit is already in this state";
        selectedUnit.setUnitState(unitState);
        return "the unit state is selected to" + state;
    }


    public String attackEnemy(int x , int y) {
        Block block = map.getBlockByLocation(x, y);
        if (areCoordinatesCorrect(x, y)) return ResponseToUser.COORDINATES_NOT_CORRECT.response;


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


}
