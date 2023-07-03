package Server;

import model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

public class MainLobbyMenu {
    private static final ArrayList<Lobby> lobbies = new ArrayList<>();
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    User currentUser;

    public MainLobbyMenu(DataInputStream dataInputStream, DataOutputStream dataOutputStream, User currentUser) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.currentUser = currentUser;
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
    public static ArrayList<Lobby> getLobbies(){
        return lobbies;
    }
    public void gameMenuStart(){

    }
}
