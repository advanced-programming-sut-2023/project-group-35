package Enum;

public enum ResponseToUser {
    OCCUPIED("this block is occupied"),
    COORDINATES_NOT_CORRECT("the coordinates are not correct"),
    INDEX("index out of bounds");
    public String response;

    ResponseToUser(String response) {
        this.response = response;
    }
}
