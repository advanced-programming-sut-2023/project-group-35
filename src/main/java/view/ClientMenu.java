package view;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientMenu {
    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORT);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            final boolean[] isRunning = {true};

            Thread inputThread = new Thread(() -> {
                while (isRunning[0]) {
                    try {
                        String input = dataInputStream.readUTF();
                        if (input.equals("Dead")) {
                            isRunning[0] = false;
                            break;
                        }
                        System.out.println(input);
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            inputThread.start();

            Thread outputThread = new Thread(() -> {
                String input = null;
                while (isRunning[0]) {
                    try {
                        if (input != null) {
                            dataOutputStream.writeUTF(input);
                        }
                        input = scanner.nextLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            outputThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
