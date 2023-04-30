package Enum;

public enum SecurityQuestion {
    FATHER_NAME("1.what is your father name?"),
    MOTHER_NAME("2.what is your mother name?"),
    PET_NAME("3.what is your pet name?");
    public String question;
    private SecurityQuestion(String question){
        this.question = question;
    }

}
