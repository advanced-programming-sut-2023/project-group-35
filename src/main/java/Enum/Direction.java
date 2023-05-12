package Enum;

public enum Direction {
    up_right(1, 1),
    up_left(-1, 1),
    down_left(-1, -1),
    down_right(1, -1),
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
    public static Direction getDirectionByName(String name){
        switch (name){
            case "right"-> {
                return right;
            }
            case "left"-> {
                return left;
            }
            case "up"->{
                return up;
            }
            case "down"->{
                return down;
            }
            case "up right", "right up" ->{
                return up_right;
            }
            case "down right", "right down" ->{
                return down_right;
            }
            case "up left", "left up" ->{
                return up_left;
            }
            case "down left", "left down" ->{
                return down_left;
            }
            default -> {
                return null;
            }
        }
    }
}
