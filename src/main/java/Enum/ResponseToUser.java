package Enum;

public enum ResponseToUser {
    // faghat sakhtam shayad asan niaz nabashe
    OCCUPIED("this block is occupied"),
    INDEX("index out of bounds");
    public String response;

    ResponseToUser(String response) {
        this.response = response;
    }
}
