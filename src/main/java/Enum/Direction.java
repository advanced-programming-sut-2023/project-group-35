package main.java.Enum;

public enum Direction {
    up(0 , 1),
    down(0, -1),
    right(1 , 0),
    left(-1, 0);

    public int xChange;
    public int yChange;


    Direction(int xChange, int yChange) {
        this.xChange = xChange;
        this.yChange = yChange;
    }
}
