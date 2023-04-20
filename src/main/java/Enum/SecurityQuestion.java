package main.java.Enum;

public enum SecurityQuestion {
    YOURFATHERNAME("what is your father name?"),
    YOURMOTHERNAME("what is your mother name?"),
    YOURPETNAME("what is your pet name?");
    public String question;
    private SecurityQuestion(String question){
        this.question = question;
    }

}
