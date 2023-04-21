package main.java.model;

public class Map {
    public int dimensions;
    Block[][] blocks;

    //private final ArrayList<Block> blocks = new ArrayList<>();
    User owner;

    public Map(User owner , int dimensions) {
        this.owner = owner;
        this.dimensions= dimensions;
        blocks = new Block[dimensions][dimensions];
    }
}
