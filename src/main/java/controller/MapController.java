package main.java.controller;

import main.java.model.*;
import main.java.Enum.*;

public class MapController {
    //private Game game;
    private Map map;

    public MapController(Map map) {
        this.map = map;
    }

    public int getSizeOfMap() {
        return 0;
    }

    public FieldType getTextureOfPixel(int x, int y) {
        return null;
    }

    public void setTextureOfArea(int x1, int x2, int y1, int y2, FieldType fieldType) {
    }

    public void clearPixel(int x, int y) {

    }

    public String dropRock(int x, int y, String direction) {
        return null;
    }

    public String dropTree(int x, int y, TreeName treeName) {
        return null;
    }

}
