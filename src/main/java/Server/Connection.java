package Server;

import controller.UserController;
import model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Connection extends Thread{
    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private User currentUser = null;
    private static final ArrayList<Connection> connections = new ArrayList<>();

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        connections.add(this);
    }

    public static void purgeThemAll(){
        Iterator iterator = connections.iterator();
        Connection connection = null;
        while (iterator.hasNext()) {
             connection = (Connection)iterator.next();
            if (connection.isAlive()) {
                    iterator.remove();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public static ArrayList<Connection> getConnections() {
        purgeThemAll();
        return connections;
    }
    public static Connection getConnectionByName(String username){
        ArrayList<Connection> fkAL = Connection.getConnections();
        for(Connection cnc:fkAL){
            if(cnc.getCurrentUser().getUserName().equals(username)) return cnc;
        }
        return null;
    }
    @Override
    public void run() {
        UserController.loadTheData();
        while (true) {
            currentUser = new LoginMenuServerController(dataOutputStream, dataInputStream).goInit();
            if (currentUser == null) return;
            handleUser();
        }
    }
    public void handleUser(){
        try {
            dataOutputStream.writeUTF("Choose Where To Go!");
            while (true) {
                if(dataInputStream.available()!=0) {
                    String input = dataInputStream.readUTF();
                    if(input.equals("friendship menu"))
                        new FriendShipMenu(dataOutputStream,dataInputStream,currentUser).startFriendShip();
                   /* else if(input.equals("lobby"))
                        new GamesMenu(dataOutputStream,dataInputStream,currentUser);*/
                    else if (input.equals("global chat"))
                        new GlobalChatServerMenu(dataOutputStream, dataInputStream, currentUser).startGlobal();
                    else if (input.equals("private chat"))
                        new PrivateChatMenu(dataOutputStream, dataInputStream, currentUser).privateChat();
                    else if (input.equals("group chat"))
                        new GroupChatServerMenu(dataOutputStream, dataInputStream, currentUser).startGroupChat();
                    else if (input.equals("scoreboard")){
                        ScoreBoardServerMenu scoreBoardServerMenu = new ScoreBoardServerMenu(dataOutputStream, dataInputStream);
                        scoreBoardServerMenu.start();
                        scoreBoardServerMenu.join();
                    }
                    else if (input.matches("\\s*logout\\s*")) return;
                    else dataOutputStream.writeUTF("invalid input");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
