package Server;

import controller.GameController;
import controller.MapController;
import controller.UserController;
import model.GameMap;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MasterServer {
    private static ServerSocket serverSocket;
    private static final int port = 8080;

    public static void main(String[] args) {
        Thread lobbyCleanupThread = new Thread(() -> {
            while (true) {
                for (int i = MainLobbyMenu.getLobbies().size() - 1; i >= 0; i--) {
                    Lobby lobby = MainLobbyMenu.getLobbies().get(i);
                    if (lobby.getGame() == null && lobby.getUsers().size() == 1) {
                        if (lobby.checkTheTime()) {
                            MainLobbyMenu.getLobbies().remove(lobby);
                        }
                    } else {
                        lobby.setCreationTime(System.currentTimeMillis());
                    }
                }
            }
        });
        lobbyCleanupThread.start();

        try {
            UserController.loadTheData();
           // MapController.loadTheMaps();
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket);
                connection.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
