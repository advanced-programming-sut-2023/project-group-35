package Enum;

public enum ResponseToUser {
    OCCUPIED("this block is occupied"),
    COORDINATES_NOT_CORRECT("the coordinates are not correct"),
    INVALID_COMMAND("this is an invalid command!\nplease try again."),
    INDEX("index out of bounds");
    public String response;

    ResponseToUser(String response) {
        this.response = response;
    }
}
