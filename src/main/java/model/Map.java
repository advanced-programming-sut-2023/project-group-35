package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import model.*;
import Enum.*;
import model.buildings.Building;

import javax.swing.plaf.synth.Region;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

public class Map {
    User owner;
    String name;
    public int dimensions;



    private final ArrayList<Block> blocks = new ArrayList<>();

    private final ArrayList<Block> baseBlocks = new ArrayList<>();

    public static final ArrayList<Map> templateMaps = new ArrayList<>();


    public Map(User owner , int dimensions,String name) {
        this.owner = owner;
        this.dimensions= dimensions;
        addBlocks(dimensions);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void turnUnnamedMapIntoNamed(Map map,String name){
        map.setName(name);
    }
    public void addBlocks(int dim) {
        for(int i = 1; i < dim; i++) {
            for(int j = 1; j < dim; j++) {
                blocks.add(new Block(i , j, FieldType.Ground));
            }
        }
    }
    public static Map getTemplateMapByName(String name) {
        for (Map map : templateMaps) {
            if(map.name.equals(name)) return map;
        }
        return null;
    }
    public static String getMapList() {
        return null;
        // ??????
    }
    public static ArrayList<Map> getTemplateMaps(){
        return templateMaps;
    }
    public Block getBlockByLocation(int x , int y) {
        for (Block block : blocks) {
            if(block.x == x && block.y == y) return block;
        }
        return null;
    }
    public Block getNeighborBlock(Block firstBlock , Direction direction) {
        for (Block block : blocks) {
            if(block.x == firstBlock.x + direction.xChange
                    && block.y == firstBlock.y + direction.yChange) return block;
        }
        return null;
    }

    public void addRegin(Reign reign) {
        baseBlocks.get(0).setBuilding(new Building(BuildingType.BASE, reign, baseBlocks.get(0)));
        baseBlocks.remove(0);
    }
    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public ArrayList<Block> getBaseBlocks() {
        return baseBlocks;
    }
    public boolean isABase(int x , int y) {
        for (Block block : baseBlocks) {
            if(block.x == x && block.y == y) return true;
        }
        return false;
    }
    public boolean findUnitsOfOpponent(Block block , int range, Reign reign) {
        for(int i = block.x - range; i <= block.x + range; i++) {
            for(int j = block.y - range; j <= block.y + range; j++) {
                if(this.getBlockByLocation(block.x , block.y).findOpponentInBlock(reign)) return true;
            }
        }
        return false;
    }
    public int getNumberOfBases() {
        return baseBlocks.size();
    }
    public static void addTemplateMap(Map map){
        templateMaps.add(map);
    }
    public static void loadTheMap(){
        Reader reader;
        try {
            reader = new FileReader("dataBaseMap.json");
        } catch (FileNotFoundException e) {
            return;
        }
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
        for (JsonElement jsonElement : jsonArray)
            Map.addTemplateMap(gson.fromJson(jsonElement, Map.class));

    }
}
