package controller;

import model.Block;
import model.Map;
import Enum.*;
import model.Reign;


import java.util.HashMap;
import java.util.regex.*;

public class MapController {
    //private Game game;
    private final Reign reignPlaying;
    private final Map map;
    private int x;
    private int y;
    public boolean isInTheGame;

    public MapController(Map map, boolean isInTheGame, Reign playing) {
        this.map = map;
        this.isInTheGame = isInTheGame;
        reignPlaying = playing;
    }

    public String showMap(Matcher matcher) {
        this.x = Integer.parseInt(matcher.group("x"));
        this.y = Integer.parseInt(matcher.group("y"));
        if(x > map.dimensions || y > map.dimensions) return "not in the map";
        return printMap();
    }
    public String printMap() {
        //todo how to print map
        return null;
    }
    public String moveMap(Matcher matcher) {
        HashMap<Direction, Integer> move= new HashMap<>();
        move.put(Direction.up, Integer.parseInt(matcher.group("up")));
        move.put(Direction.down, Integer.parseInt(matcher.group("down")));
        move.put(Direction.left, Integer.parseInt(matcher.group("left")));
        move.put(Direction.right, Integer.parseInt(matcher.group("right")));
        for (Direction direction : Direction.values()) {
            this.x += move.get(direction) * direction.xChange;
            this.y += move.get(direction) * direction.yChange;
        }
        return printMap();
    }
    public String showDetail(Matcher matcher) {
        this.x = Integer.parseInt(matcher.group("x"));
        this.y = Integer.parseInt(matcher.group("y"));
        if(x > map.dimensions || y > map.dimensions) return "not in the map";
        return map.getBlockByLocation(x, y).BlockInfo(true);
    }

    public String setNewBase(int x, int y) {
        if(map.getBlockByLocation(x, y) == null) return ResponseToUser.INDEX.response;
        if(map.isABase(x , y)) return "this block already is a base";
        if(map.getBlockByLocation(x, y).isOccupied()) return "this block is occupied";
        if(map.getBaseBlocks().size() == 8) return "you have 8 bases you can't add more";
        //if(map.getBlockByLocation(x, y).getFieldType()) ?? che field haei nemishe?
        map.getBaseBlocks().add(map.getBlockByLocation(x, y));
        return "base was added successfully";
    }
    public String removeBase(int x, int y) {
        if(!map.isABase(x , y)) return "there is no base in this block";
        map.getBaseBlocks().remove(map.getBlockByLocation(x , y));
        return "the base was successfully removed";
    }

    public String setTextureOfBlock(int x , int y , FieldType fieldType) {
        if(isInTheGame) return "you can't change the texture of the blocks in the game";
        if(map.isABase(x, y)) return "there is a base here, you can't change the texture";
        map.getBlockByLocation(x, y).setFieldType(fieldType);
        return "the texture is now set";
    }

    public String setTextureOfArea(int x1, int x2, int y1, int y2, FieldType fieldType) {
        if(isInTheGame) return "you can't change the texture of the blocks in the game";
        for(int i = x1; i <= x2; i++) {
            for(int j = y1; j<= y2; j++) {
                //todo what if a Building is in the block?
                if(map.getBlockByLocation(x , y) != null)
                    map.getBlockByLocation(i , j).setFieldType(fieldType);
            }
        }
        return "the textures are successfully set";
    }


    public String dropRock(int x, int y, String direction) {
        return null;
        //todo direction???
    }

    public String dropTree(int x, int y, Tree treeName) {
        Block block = map.getBlockByLocation(x, y);
        if(block == null) return "index out of bounds";
        if(block.isOccupied()) return ResponseToUser.OCCUPIED.response;
        block.setTree(treeName);
        return "tree was dropped successfully";
    }

    public String clearBlock(int x, int y) {
        if(!isInTheGame) {
            map.getBlocks().remove(map.getBlockByLocation(x, y));
            map.getBlocks().add(new Block(x ,y , FieldType.Ground));
        }
        else {
            map.getBlockByLocation(x, y).clearBlock(reignPlaying);
        }
        return "block was cleared successfully";
    }

    public int getSizeOfMap() {
        return map.dimensions;
    }

    public FieldType getTextureOfBlock(int x, int y) {
        return map.getBlockByLocation(x, y).getFieldType();
    }



}
