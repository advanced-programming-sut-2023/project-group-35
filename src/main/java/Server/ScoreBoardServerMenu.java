package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ScoreBoardServerMenu extends Thread {
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;
    private volatile boolean isRunning;

    public ScoreBoardServerMenu(DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
        this.isRunning = true;
    }

    public void run() {
        DisplayScoreBoard displayScoreBoard = new DisplayScoreBoard(dataOutputStream);
        displayScoreBoard.start();

        while (isRunning) {
            try {
                String input = dataInputStream.readUTF();
                if (input.equals("exit")) {
                    displayScoreBoard.stopPrinting();
                    return;
                } else if (input.equals("refresh")) {
                    synchronized (displayScoreBoard) {
                        displayScoreBoard.notify();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
