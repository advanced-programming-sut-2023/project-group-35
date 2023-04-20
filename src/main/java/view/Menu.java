package main.java.view;

import java.io.*;
import java.security.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
    public static final Scanner scanner = new Scanner(System.in);

    public void run() throws IOException, NoSuchAlgorithmException {

    }
    public static Matcher getMatcher(String input , String regex){
        Matcher matcher = Pattern.compile(regex).matcher(input);
        return matcher.matches()? matcher: null;
    }
}
