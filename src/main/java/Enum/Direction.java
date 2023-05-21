package Enum;

public enum Direction {
    up_right(1, 1, false),
    up_left(-1, 1, false),
    down_left(-1, -1, false),
    down_right(1, -1, false),
    up(0 , 1, true),
    down(0, -1, true),
    right(1 , 0, true),
    left(-1, 0, true);

    public int xChange;
    public int yChange;
    public boolean isMajor;


    Direction(int xChange, int yChange, boolean isMajor) {
        this.xChange = xChange;
        this.yChange = yChange;
        this.isMajor = isMajor;
    }

    public String getName() {
        return this.name().replaceAll("_", " ");
    }
    public static Direction getDirectionByName(String name){
        for (Direction value : Direction.values()) {
            if(value.getName().equals(name)) return value;
        }
        return null;
    }
    public String getNumberRegex() {
        return this.getName() + "\s*(?<number>\\d+)?";
    }
}
