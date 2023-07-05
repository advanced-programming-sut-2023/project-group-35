package Server;

import model.Game;
import model.GameMap;
import model.Map;
import model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static Server.GameData.playTheHistory;

public class MainLobbyMenu {
    private static final ArrayList<Lobby> lobbies = new ArrayList<>();
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    User currentUser;

    public MainLobbyMenu(DataOutputStream dataOutputStream,
                     DataInputStream dataInputStream,
                     User currentUser) {
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
        this.currentUser = currentUser;
    }

    public void lobbyMenuStart() throws IOException, InterruptedException {
        dataOutputStream.writeUTF("You have entered the lobby");
        while (true) {
            if (dataInputStream.available() != 0) {
                String input = dataInputStream.readUTF();
                if (input.equals("create")) {
                    createGame();
                } else if (input.equals("join")) {
                    joinGame();
                } else if (input.equals("show games")) {
                    showGames();
                } else if (input.equals("enter main menu"))
                    return;
                else if(input.equals("reply games"))
                    replyGames();
                else
                    dataOutputStream.writeUTF("Invalid input");
            }
        }
    }

    private void showGames() throws IOException {
        for (Lobby lobby : getLobbies()) {
            if (lobby.isPublic()) {
                String output = "ID: " + lobby.getLobbyId() + "  -  " + "Capacity: " + lobby.getUsers().size()
                        + "/" + lobby.getNumberOfPlayers() + "\nUsers:";
                for (User user : lobby.getUsers()) {
                    output += "\nUsername: " + user.getUserName();
                }
                dataOutputStream.writeUTF(output);
            }
        }
    }
    public void createGame() throws IOException, InterruptedException {
        Integer numberOfPlayers = getNumberOfPlayers();
        if (numberOfPlayers == null)
            return;
        Map map = getChosenMap(numberOfPlayers);
        if (map == null)
            return;
        Integer earlyGameGolds = 1000;
        dataOutputStream.writeUTF("Game created.");
        Lobby lobby = new Lobby(true, map, currentUser, numberOfPlayers, earlyGameGolds);
        getLobbies().add(lobby);
        lobby.getUsers().add(currentUser);
        lobbyOnRun(lobby);
    }

    public void joinGame() throws IOException {
        dataOutputStream.writeUTF("Enter the ID:");
        while (true) {
            if (dataInputStream.available() != 0) {
                String input = dataInputStream.readUTF();
                int id = 0;
                try {
                    id = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    dataOutputStream.writeUTF("Enter a number");
                    showGames();
                    continue;
                }
                Lobby lobby = null;
                for (Lobby lobby2 : getLobbies())
                    if (lobby2.getLobbyId() == id) {
                        lobby = lobby2;
                        break;
                    }
                if (lobby == null){
                    dataOutputStream.writeUTF("Invalid ID");
                    showGames();
                }
                else {
                    if (lobby.getUsers().size() == lobby.getNumberOfPlayers())
                        dataOutputStream.writeUTF("This lobby is full!");
                    else {
                        lobby.getUsers().add(currentUser);
                        lobbyOnRun(lobby);
                    }
                }
            }
        }
    }

    public void lobbyOnRun(Lobby lobby) throws IOException {
        Map gameMap = lobby.getMap();
        int numberOfPlayers = lobby.getNumberOfPlayers();
        int earlyGameGolds = 1000;
        while (true) {
            if (!getLobbies().contains(lobby) || lobby.checkTheTime()) {
                dataOutputStream.writeUTF("The lobby was deleted due to being idle for too long!");
                return;
            } else if (lobby.getGame() == null && lobby.getUsers().size() == numberOfPlayers
                    && currentUser.equals(lobby.getAdmin())) {
                Game game = new Game(currentUser,lobby.getUsers(),gameMap);
                lobby.setGame(game);
                lobby.setGameMenuController(new GameControllerPhaseOne(game));
                new GameMenuPhaseOne(dataOutputStream, dataInputStream, currentUser, lobby.getGameMenuController(),
                        lobby.getGame()).gameHandler();
            } else if (lobby.getGame() != null && lobby.getGameMenuController() != null
                    && !currentUser.equals(lobby.getAdmin())) {
                new GameMenuPhaseOne(dataOutputStream, dataInputStream, currentUser, lobby.getGameMenuController(),
                        lobby.getGame()).gameHandler();
            } else if (dataInputStream.available() != 0) {
                String input = dataInputStream.readUTF();
                if (input.equals("exit")) {
                    dataOutputStream.writeUTF("You left the lobby");
                    lobby.removeUser(currentUser);
                    if (currentUser.equals(lobby.getAdmin())) {
                        dataOutputStream.writeUTF("You left the lobby. The lobby will be deleted.");
                        getLobbies().remove(lobby);
                    } else {
                        dataOutputStream.writeUTF("You left the lobby.");
                        lobby.removeUser(currentUser);
                    }
                    return;
                } else if (input.equals("show users")) {
                    String output = "Users in the lobby:";
                    for (User user : lobby.getUsers()) {
                        output += "\nUsername: " + user.getUserName();
                    }
                    dataOutputStream.writeUTF(output);
                } else {
                        dataOutputStream.writeUTF("Invalid input");
                }
            }
        }
    }

    private Integer getNumberOfPlayers() throws IOException {
        dataOutputStream.writeUTF("Enter the number of players:");
        while (true) {
            if (dataInputStream.available() != 0) {
                String input = dataInputStream.readUTF();
                try {
                    int numberOfPlayers = Integer.parseInt(input);
                    if (numberOfPlayers < 2 || numberOfPlayers > 8) {
                        dataOutputStream.writeUTF("Number of players must be between 2 and 8");
                    } else {
                        return numberOfPlayers;
                    }
                } catch (NumberFormatException e) {
                    dataOutputStream.writeUTF("Enter a number");
                }
            }
        }
    }
    private Map getChosenMap(int numberOfPlayers) throws IOException {
        dataOutputStream.writeUTF("Choose a map:");
        Map mapo;
        while (true) {
            if (dataInputStream.available() != 0) {
                String input = dataInputStream.readUTF();
                mapo = new Map(currentUser.getUserName(),100,currentUser.nickName);
                for(int i = 0;i < numberOfPlayers;i++){
                    mapo.getBlockByLocation(i*5,i*5).hasBase = true;
                }
                break;
            }
        }
        return mapo;
    }

    public static ArrayList<Lobby> getLobbies() {
        return lobbies;
    }
    public void replyGames(){
        try {
            dataOutputStream.writeUTF("List of Games:");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String list = "";
        int i = 0;
        for(ArrayList<GameData> arrayList:GameData.historyOfMatches){
            i++;
            list += "\n"+i+"'Game";
        }
        try {
            dataOutputStream.writeUTF(list);
            dataOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String res = "";
        while(true) {
            try {
                res = dataInputStream.readUTF();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(res.equals("back"))
                break;
            else if(res.matches("\\d")|| (GameData.historyOfMatches.size()+1 > Integer.parseInt(res))){
                playTheHistory(GameData.historyOfMatches.get(Integer.parseInt(res)));
            }
        }
    }
    public void playTheHistory(ArrayList<GameData> playing) {
        long currentTime = System.currentTimeMillis();
        for (GameData gd : playing) {
            while (true) {
                if (System.currentTimeMillis() - currentTime > gd.StoppageTime)
                    break;
            }
            try {
                dataOutputStream.writeUTF(gd.order + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                dataOutputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}



