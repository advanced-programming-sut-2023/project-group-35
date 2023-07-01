package Enum;

public enum ResponseToUser {
    //LOGIN AND REGISTER MENU
    USERNAME_NOT_FOUND("the username was not found"),
    SHORT_USERNAME("username should be at least 5 characters"),
    INVALID_FORMAT_FOR_USERNAME("username can't have any characters of <@#$%%>"), //todo correct this
    USERNAME_EXISTS("this username already exists in the game"),

    CORRECT_FIELD("perfect!"),
    // EMAIL
    INVALID_EMAIL_FORMAT("the email format is not correct"),
    EMAIL_EXISTS("somebody has registered with this email"),
    WRONG_PASSWORD("wrong password. please try again in "),
    LOGGED_IN_SUCCESSFULLY("you have loggedInSuccessfully"),

    // PASSWORD
    OCCUPIED("this block is occupied"),
    COORDINATES_NOT_CORRECT("the coordinates are not correct"),
    INVALID_COMMAND("this is an invalid command!\nplease try again."),
    INDEX("index out of bounds");
    public String text;

    ResponseToUser(String text) {
        this.text = text;
    }
}
