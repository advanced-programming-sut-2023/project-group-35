package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import Enum.*;
import model.buildings.StoreHouse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

public class Map implements Cloneable{
    //User owner;
    String ownerUsername;
    String name;
    public int dimensions;
    private final ArrayList<Block> blocks = new ArrayList<>();

    //private final ArrayList<Base> baseBuildings = new ArrayList<>();
    private final ArrayList<Block> baseBlocks = new ArrayList<>();

    public ArrayList<Block> getBaseBlocks() {
        return baseBlocks;
    }

    public static final ArrayList<Map> templateMaps = new ArrayList<>();


    public Map(String ownerUsername, int dimensions, String name) {
        this.ownerUsername = ownerUsername;
        this.dimensions= dimensions;
        addBlocks(dimensions);
        this.name = name;
    }
    public static Map generateDefaultMap(String username) {
        Map map = new Map(username,200, "default");
        makeNewBase(map, 10, 10);
        makeNewBase(map, 10, 20);
        makeNewBase(map, 20, 20);
        System.out.println("bases: " + map.getNumberOfBases());
        return map;
    }
    public static void makeNewBase(Map map, int x, int y) {
        Block block = map.getBlockByLocation(x, y);
        block.setHasBase(true);
        map.baseBlocks.add(block);
        System.out.println("added base: " + block.x + block.y);
    }
    public static void removeBaseOutSideTheGame(Map map, int x , int y) {
        map.baseBlocks.remove(map.getBlockByLocation(x, y));
    }
    public static void removeBase(Map map, Reign reign) {
        //todo if nessesary
    }

    public void setName(String name) {
        this.name = name;
    }

    public void turnUnnamedMapIntoNamed(Map map,String name){
        map.setName(name);
    }
    public void addBlocks(int dim) {
        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                blocks.add(new Block(i , j, FieldType.Ground));
            }
        }
    }
    public void addRegin(Reign reign) {
//        Base base = baseBuildings.get(0);
//        base.setOwner(reign);
        Block block = baseBlocks.get(0);
        for (Direction value : Direction.values()) {
            if(!value.isMajor) continue;
            Block block1 = this.getNeighborBlock(block, value);
            if(!block1.isOccupied(this)) {
                block1.setBuilding(new StoreHouse(BuildingType.STOCK_PILE, reign, block));
                break;
            }
        }
//        baseBuildings.remove(0);
        baseBlocks.remove(0);
    }
    public static Map getTemplateMapByName(String name) {
        ArrayList<Integer> indexes = new ArrayList<>();
        int count = 0;
        for (Map map : templateMaps) {
            count++;
            if(map.name.equals(name))
                indexes.add(count-1);
        }
        if(indexes.size() < 1)
            return null;
        else
            return templateMaps.get(indexes.get(indexes.size()-1));
    }
    public static String getMapList() {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> mapNamesUsed = new ArrayList<>();
        for(Map map: templateMaps){
            if(!isMapNameRepeated(mapNamesUsed,map.name)){
                stringBuilder.append(map.name+" ");
                mapNamesUsed.add(map.name);
            }
        }
        return stringBuilder.toString();
    }
    public static boolean isMapNameRepeated(ArrayList<String> arrayList,String string){
        for(String s1:arrayList){
            if(s1.equals(string))
                return true;
        }
        return false;
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

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    //public ArrayList<Base> getBaseBuildings() {
//        return baseBuildings;
//    }
    public boolean isABase(int x , int y) {
//        for (Base base : baseBuildings) {
//            Block block = base.getBlock();
//            if(block.x == x && block.y == y) return true;
//        }
        for (Block baseBlock : baseBlocks) {
            if(baseBlock.getX() == x && baseBlock.getY() == y) return true;
        }
        if(getBlockByLocation(x,y).hasBase())
            return true;
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
        Map mapToEdit;
        Reader reader;
        try {
            reader = new FileReader("dataBaseMap.json");
        } catch (FileNotFoundException e) {
            return;
        }
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
        for (JsonElement jsonElement : jsonArray) {
            Map.addTemplateMap(gson.fromJson(jsonElement, Map.class));
            mapToEdit = templateMaps.get(templateMaps.size()-1);
            for (Block block : mapToEdit.getBlocks()) {
                block.setMilitaryUnits(new ArrayList<>());
                block.setStructures(new ArrayList<>());
            }
        }

    }
    public String getName() {
        return name;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    @Override
    public Map clone() {
        try {
            Map clone = (Map) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
