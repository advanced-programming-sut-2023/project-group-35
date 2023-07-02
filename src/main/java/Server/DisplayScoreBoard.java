package Server;

import model.User;
import view.ScoreBoard;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DisplayScoreBoard extends Thread {
    private volatile boolean keepPrinting;
    private final DataOutputStream dataOutputStream;

    public DisplayScoreBoard(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
        this.keepPrinting = true;
    }

    @Override
    public void run() {
        try {
            while (keepPrinting) {
                synchronized (this) {
                    dataOutputStream.writeUTF(getScoreboardInString());
                    wait(10 * 1000);
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void stopPrinting() {
        keepPrinting = false;
        synchronized (this) {
            notify();
        }
    }

    private static String getScoreboardInString() {
        StringBuilder str = new StringBuilder();
        ArrayList<String> usersInformation = ScoreBoard.fillData(ScoreBoard.sortIt(User.getUsers()));
        str.append("| rank | username | score | last entrance |\n");
            for (String information : usersInformation) {
                str.append(" ").append(information).append(" |");
            str.append("\n");
        }
        return str.toString();
    }
}
