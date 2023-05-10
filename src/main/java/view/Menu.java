package view;

import java.io.*;
import java.security.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Enum.*;

public class Menu {
    public static final Scanner scanner = new Scanner(System.in);
    protected String input;
    protected Matcher matcher;

    public void run() throws IOException, NoSuchAlgorithmException {

    }
    public static Matcher getMatcher(String input , String regex){
        Matcher matcher = Pattern.compile(regex).matcher(input);
        return matcher.matches()? matcher: null;
    }
    public static boolean findRegex(String input , Commands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        System.out.println(command.regex);
        return matcher.find();
    }
    public static Matcher getRealMatcher(String input, Commands starter, Commands... groups) {
        if(!findRegex(input , starter)) {
            System.out.println("1");
            return null;
        } else {
            System.out.println("found");
        }
        String newInput = input;
        newInput = newInput.replaceFirst(starter.regex , "");
        for (Commands group : groups) {
            if(!findRegex(input , group)) return null;
            newInput = newInput.replaceFirst(group.regex, "");
        }
        if (!newInput.matches("\\s*")) return null;
        return getMatcher(input , appendGroups(groups));
    }
    public static String appendGroups(Commands[] groups) {
        String output = "\\" + "\\" + "s*";
        for (Commands group : groups) {
            output += group + "\\" + "\\" + "s+";
        }
        output = output.replaceFirst("\\+$", "*");
        return output;
    }

}
