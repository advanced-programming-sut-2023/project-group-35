package controller;

import Enum.*;
import model.*;
import model.buildings.Building;
import model.buildings.Wall;
import model.buildings.bigTower;
import model.people.*;
import model.structures.*;

public class UnitController extends GameController{
    public UnitController(Game game) {
        super(game);
    }

    public String moveUnit(int x, int y) {
        return null;
    }

    public String setUnitState(String state) {
        if(state.equals("offensive")) {
            game.getSelectedSingleUnit().setUnitState(UnitState.OFFENSIVE);
            return "state changed!";
        }
        else if(state.equals("stable")) {
            game.getSelectedSingleUnit().setUnitState(UnitState.STABLE);
            return "state changed!";
        }
        else if(state.equals("defensive")){
            game.getSelectedSingleUnit().setUnitState(UnitState.DEFENSIVE);return "state changed!";
        }
            return "your state was not valid!";
    }
    public UnitState getUnitState() {
        return game.getSelectedSingleUnit().getUnitState();
    }

    public String attackEnemy(int x , int y) {
        return null;
    }

    public String airAttack(int x, int y) {
        if(!areCoordinatesCorrect(x,y))
            return "location is not valid!";
        else if(game.getSelectedSingleUnit().getRange() < 2){
            return "selected unit can't attack ranged";
        }
        else if(getDistance(game.getSelectedSingleUnit().getBlock(),game.getMap().getBlockByLocation(x,y)) >
                game.getSelectedSingleUnit().getRange()){
            return "aim is out of board!";
        }
        else if(game.getMap().getBlockByLocation(x,y).getMilitaryUnits() == null){
            return "there is no enemy in destination";
        }
        else{
            int amountOfDamage = game.getSelectedSingleUnit().getDamage()*game.getSelectedSingleUnit().getNumber();
            game.getMap().getBlockByLocation(x,y).getMilitaryUnits().get(0).getDamaged(amountOfDamage);
            collectingGarbageUnits();
            return "attack successfully!";
        }

    }

    public String pourOil(Direction direction) {
        return null;
    }
    public String digTunnel(int x , int y) {
        if(!areCoordinatesCorrect(x,y))
            return "location is not valid!";
        else if(getDistance(map.getBlockByLocation(x,y),game.getSelectedSingleUnit().getBlock()) >= 2)
            return "your are too far away,try to move toward the aim!";
        Block block = map.getBlockByLocation(x,y);
        if(!(game.getSelectedSingleUnit() instanceof Tunneler))
            return "you can't dig tunnel without tunnelers!";
        else if(!(block.getBuilding()==null) || !(block.getBuilding() instanceof Wall) ||
                !(block.getBuilding().buildingType.equals(BuildingType
                .DEFENCE_TURRET))){
            return "you can't dig tunnel here!";
        }
        else{
            block.getMilitaryUnits().clear();
            block.addUnit(game.getSelectedSingleUnit());
            game.getSelectedSingleUnit().setBlock(block);
            block.removeBuilding();
            return "tunneling was amazing!";
        }
    }
    public int getDistance(Block blockIN,Block blockOUT){
        int xD = blockIN.getX() - blockOUT.getX();
        int yD = blockIN.getY() - blockOUT.getY();
        double out = xD*xD + yD*yD;
        out = Math.sqrt(out);
        int outage = (int) Math.floor(out);
        return outage;
    }
    public String buildEquipment(StructuresType equipmentType,int amount) {
        if(!(game.getSelectedSingleUnit() instanceof Engineer))
            return "You must choose bunch of engineers!";
        switch (equipmentType){
            case LADDER->{
                if(game.getPlayingReign().getResourceAmount(equipmentType.getResource()) <
                        amount*equipmentType.getAmountOfMaterial()){
                    return "You don't have enough matrials!";
                }
                else if(game.getSelectedSingleUnit().getNumber() < amount*equipmentType.getAmountOfEngineer()){
                    return "You don't have enough engineers!";
                }
                else{
                    game.getSelectedSingleUnit().setNumber((game.getSelectedSingleUnit()).getNumber() - amount);
                    LadderMen ladderMen = new LadderMen(UnitType.LADDERMAN,game.getPlayingReign(),
                            game.getSelectedSingleUnit().getBlock(),amount,UnitType.LADDERMAN.getDefencePower());
                    ladderMen.getBlock().addUnit(ladderMen);
                    ladderMen.getOwner().getMilitaryUnits().add(ladderMen);
                    ladderMen.getOwner().removeFromResources(Resource.WOOD,amount*equipmentType.getAmountOfMaterial());
                    return "ladder was created succesfully";
                }
            }
            case STAIRS ->{
                if(game.getPlayingReign().getResourceAmount(equipmentType.getResource()) <
                        amount*equipmentType.getAmountOfMaterial()){
                    return "You don't have enough matrials!";
                }
                else if(game.getSelectedSingleUnit().getNumber() < amount*equipmentType.getAmountOfEngineer()){
                    return "You don't have enough engineers!";
                }
                else if(!(game.getSelectedSingleUnit().getBlock().getBuilding() instanceof Wall
                        || game.getSelectedSingleUnit().getBlock().getBuilding() instanceof bigTower)){
                    return "You can't build stairs here!";
                }
                else{
                    game.getPlayingReign().removeFromResources(equipmentType.getResource(),amount*equipmentType.getAmountOfMaterial());
                    game.getSelectedSingleUnit().getBlock().addNewStructure(new Ladder(equipmentType.getHp(),equipmentType.isMoving(),0,null));
                    return "Stair was put down!";
                }
            }
            case MOVING_SHIELD -> {
                if(game.getPlayingReign().getResourceAmount(equipmentType.getResource()) <
                        amount*equipmentType.getAmountOfMaterial()){
                    return "You don't have enough matrials!";
                }
                else if(game.getSelectedSingleUnit().getNumber() < amount*equipmentType.getAmountOfEngineer()){
                    return "You don't have enough engineers!";
                }
                else{
                    game.getPlayingReign().removeFromResources(equipmentType.getResource(),amount*equipmentType.getAmountOfMaterial());
                    for(int i = 0; i < amount;i++)
                    game.getSelectedSingleUnit().getBlock().addNewStructure(new MovingShield(null));
                    return "you have put the moving shields down successfully";
                }
            }
            case WALL_BREAKER -> {
                if(game.getPlayingReign().getResourceAmount(equipmentType.getResource()) <
                        amount*equipmentType.getAmountOfMaterial()){
                    return "You don't have enough matrials!";
                }
                else if(game.getSelectedSingleUnit().getNumber() < amount*equipmentType.getAmountOfEngineer()){
                    return "You don't have enough engineers!";
                }
                else if(!(game.getSelectedSingleUnit().getBlock().getBuilding()==null)){
                    return "You can't build wall breaker here!";
                }
                else{
                    game.getPlayingReign().removeFromResources(equipmentType.getResource(),amount*equipmentType.getAmountOfMaterial());
                    game.getSelectedSingleUnit().setNumber((game.getSelectedSingleUnit()).getNumber()-1);
                    game.getSelectedSingleUnit().getBlock().addNewStructure(new BatteringRam(/*new Engineer*/));
                    return "wall breaker was put down!";
                }
            }
            case SIEGE_TOWER -> {
            }
                case CATAPULT -> {
                    if(game.getPlayingReign().getResourceAmount(equipmentType.getResource()) <
                            amount*equipmentType.getAmountOfMaterial()){
                        return "You don't have enough matrials!";
                    }
                    else if(game.getSelectedSingleUnit().getNumber() < amount*equipmentType.getAmountOfEngineer()){
                        return "You don't have enough engineers!";
                    }
                    else if(!(game.getSelectedSingleUnit().getBlock().getBuilding()==null)){
                        return "You can't build catapult here!";
                    }
                    else{
                        game.getPlayingReign().removeFromResources(equipmentType.getResource(),amount*equipmentType.getAmountOfMaterial());
                        game.getSelectedSingleUnit().setNumber((game.getSelectedSingleUnit()).getNumber()-1);
                        game.getSelectedSingleUnit().getBlock().addNewStructure(new Ballista(equipmentType.getHp(),equipmentType.isMoving(),equipmentType.getDamage(),null));
                        return "catapult was put down!";
                }}
                case TREBUCHET -> {
                    if(game.getPlayingReign().getResourceAmount(equipmentType.getResource()) <
                            amount*equipmentType.getAmountOfMaterial()){
                        return "You don't have enough matrials!";
                    }
                    else if(game.getSelectedSingleUnit().getNumber() < amount*equipmentType.getAmountOfEngineer()){
                        return "You don't have enough engineers!";
                    }
                    else if(!(game.getSelectedSingleUnit().getBlock().getBuilding()==null &&
                            !(game.getSelectedSingleUnit().getBlock().getBuilding() instanceof bigTower))){
                        return "You can't build trebuchet here!";
                    }
                    else{
                        game.getPlayingReign().removeFromResources(equipmentType.getResource(),amount*equipmentType.getAmountOfMaterial());
                        game.getSelectedSingleUnit().setNumber((game.getSelectedSingleUnit()).getNumber()-1);
                        game.getSelectedSingleUnit().getBlock().addNewStructure(new Ballista(equipmentType.getHp(),equipmentType.isMoving(),equipmentType.getDamage(),null));
                        return "trebuchet was put down!";
                    }
                }
                case FLAME_THROWER -> {
                    if(game.getPlayingReign().getResourceAmount(equipmentType.getResource()) <
                            amount*equipmentType.getAmountOfMaterial()){
                        return "You don't have enough matrials!";
                    }
                    else if(game.getSelectedSingleUnit().getNumber() < amount*equipmentType.getAmountOfEngineer()){
                        return "You don't have enough engineers!";
                    }
                    else if(!(game.getSelectedSingleUnit().getBlock().getBuilding()==null &&
                            !(game.getSelectedSingleUnit().getBlock().getBuilding() instanceof bigTower))){
                        return "You can't build ballista here!";
                    }
                    else{
                        game.getPlayingReign().removeFromResources(equipmentType.getResource(),amount*equipmentType.getAmountOfMaterial());
                        game.getSelectedSingleUnit().setNumber((game.getSelectedSingleUnit()).getNumber()-1);
                        game.getSelectedSingleUnit().getBlock().addNewStructure(new Ballista(equipmentType.getHp(),equipmentType.isMoving(),equipmentType.getDamage(),null));
                        return "ballista was put down!";
                    }
                }
                default->{
                return "invalid commend!";
                }
        }
        return "invalid type!";
    }
    public String digMoat(int x, int y) {
        if(!areCoordinatesCorrect(x,y))
            return "location is not valid!";
        else if(getDistance(map.getBlockByLocation(x,y),game.getSelectedSingleUnit().getBlock()) >= 2)
            return "your are too far away,try to move toward the aim!";
        if(map.getBlockByLocation(x,y).getFieldType().equals(FieldType.moat)){
            return "its already digged up?";
        }
        else if(map.getBlockByLocation(x,y).getMilitaryUnits().size() > 0)
            return "There are some units overthere!";
        else if(map.getBlockByLocation(x,y).isOccupied()){
            return "there are some buildings on location.";
        }
        map.getBlockByLocation(x,y).setFieldType(FieldType.moat);
        return "moat was digged up!";
    }

    public String fillMoat(int x, int y) {
        if(!areCoordinatesCorrect(x,y))
            return "location is not valid!";
        else if(getDistance(map.getBlockByLocation(x,y),game.getSelectedSingleUnit().getBlock()) >= 2)
            return "your are too far away,try to move toward the aim!";
        if(!map.getBlockByLocation(x,y).getFieldType().equals(FieldType.moat)){
            return "what are you gonna fill?!";
        }
        map.getBlockByLocation(x,y).setFieldType(FieldType.Ground);
        return "moat was filled";
    }

    public String putOnLadder(int x, int y) {
        if(!areCoordinatesCorrect(x,y))
            return "location is not valid!";
        else if(getDistance(map.getBlockByLocation(x,y),game.getSelectedSingleUnit().getBlock()) >= 2)
            return "your are too far away,try to move toward the aim!";
        boolean hasLadder = false;
        for(Structure structure : map.getBlockByLocation(x,y).getStructures()){
            if(structure instanceof Ladder){
                hasLadder = true;
            }
        }
        if(hasLadder){
            return "there is some ladder on the wall!";
        }
        else if(!(game.getSelectedSingleUnit() instanceof LadderMen)){
            return "the chosen unit can't put ladder on the wall!";
        }
        else if(!(map.getBlockByLocation(x,y).getBuilding() instanceof Wall)){
            return "you can't lay the ladder on nothing or that thing!";
        }
        else{
            Ladder ladder = new Ladder(999,false,0,null);
            game.getSelectedSingleUnit().setNumber((game.getSelectedSingleUnit()).getNumber()+-1);
            map.getBlockByLocation(x,y).addNewStructure(ladder);
            return "ladder was put on with success!";
        }
    }

    public String pushOffLadder(int x , int y) {
        if(!areCoordinatesCorrect(x,y))
            return "location is not valid!";
        else if(getDistance(map.getBlockByLocation(x,y),game.getSelectedSingleUnit().getBlock()) >= 2)
            return "your are too far away,try to move toward the aim!";
        boolean hasLadder = false;
        Ladder ladderToRemove = null;
        for(Structure structure : map.getBlockByLocation(x,y).getStructures()){
            if(structure instanceof Ladder){
                hasLadder = true;
                ladderToRemove = (Ladder)structure;
            }
        }
        if(!hasLadder){
            return "there is no ladder on the wall!";
        }
        else{
            map.getBlockByLocation(x,y).removeStructure(ladderToRemove);
            return "ladder was successfully removed!";
        }
    }
    public String climbUp(int x,int y){
        if(!areCoordinatesCorrect(x,y))
            return "location is not valid!";
        else if(getDistance(map.getBlockByLocation(x,y),game.getSelectedSingleUnit().getBlock()) >= 2)
            return "your are too far away,try to move toward the aim!";
        boolean hasLadder = false;
        for(Structure structure : map.getBlockByLocation(x,y).getStructures()){
            if(structure instanceof Ladder){
                hasLadder = true;
            }
        }
        if(game.getSelectedSingleUnit().getBlock().getBuilding() instanceof Wall &
                map.getBlockByLocation(x,y).getBuilding() instanceof Wall){
            MilitaryUnit unitToChange = game.getSelectedSingleUnit();
            Block lastBlock = game.getSelectedSingleUnit().getBlock();
            unitToChange.setBlock(map.getBlockByLocation(x,y));
            lastBlock.removeUnit(unitToChange);
            map.getBlockByLocation(x,y).addUnit(unitToChange);
            return "units just walked over!";
        }
        else if(!hasLadder & !(game.getSelectedSingleUnit() instanceof Assassin)){
            return "you can't climb!!!";
        }
        else if(!map.getBlockByLocation(x,y).getBuilding().buildingType.equals(BuildingType.DEFENCE_TURRET) &
        !(map.getBlockByLocation(x,y).getBuilding() instanceof Wall)){
            return "the tower is too tall!";
        }
        else {
            MilitaryUnit unitToChange = game.getSelectedSingleUnit();
            Block lastBlock = game.getSelectedSingleUnit().getBlock();
            unitToChange.setBlock(map.getBlockByLocation(x,y));
            lastBlock.removeUnit(unitToChange);
            map.getBlockByLocation(x,y).addUnit(unitToChange);
            return "Units climbed up!";
        }
    }
    public String disbandUnit() {
        int numberOfSoliders = game.getSelectedSingleUnit().getNumber();
        game.getSelectedSingleUnit().setNumber(0);
        game.getSelectedSingleUnit().getOwner().setUnemployedPopulation(game.getSelectedSingleUnit().getOwner().
                getUnemployedPopulation()+numberOfSoliders);
        game.setSelectedUnits(null);
        collectingGarbageUnits();
        return "the unit was disbanded!";
    }

    public String moveStructure() {
        return null;
    }
    public String dPS(int x,int y){return null;}

    public void deleteSelectedUnits() {
        game.setSelectedUnits(null);
    }
}
