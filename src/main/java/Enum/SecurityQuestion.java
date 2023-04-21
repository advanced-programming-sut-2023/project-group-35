package Enum;

public enum SecurityQuestion {
    FATHER_NAME("what is your father name?"),
    MOTHER_NAME("what is your mother name?"),
    PET_NAME("what is your pet name?");
    public String question;
    private SecurityQuestion(String question){
        this.question = question;
    }

}
