package Server;

import controller.GameController;
import model.Game;
import model.GameMap;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Lobby {
    private Game game = null;
    private GameController gameMenuController = null;
    private final ArrayList<User> users = new ArrayList<>();
    private boolean isPublic = true;
    public long creationTime;
    private GameMap map;
    private User admin;
    private final int lobbyId;
    private int numberOfPlayers;
    private int initialGolds;
    private long idleTime = 0;
    private GroupChat groupChat = null;

    public Lobby(boolean isPublic, GameMap map, User admin, int numberOfPlayers, int initialGolds) {
        this.numberOfPlayers = numberOfPlayers;
        this.initialGolds = initialGolds;
        this.isPublic = isPublic;
        this.map = map;
        this.admin = admin;
        if (MainLobbyMenu.getLobbies().isEmpty())
            lobbyId = 0;
        else
            lobbyId = 1 + MainLobbyMenu.getLobbies().get(MainLobbyMenu.getLobbies().size() - 1).lobbyId;

    }

    public void addUser(User user) {
        if (!users.contains(user))
            users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void updateAdminAndLobby() throws IOException {
        if (users.isEmpty())
            MainLobbyMenu.getLobbies().remove(this);
        else if (!users.contains(admin)) {
            admin = users.get(0);
            Connection.getConnectionByName(admin.getUserName()).getDataOutputStream().writeUTF("Admin has changed" +
                    "In lobby with id: " + lobbyId +" You are current Admin");
        }
    }

    public User getAdmin() {
        return admin;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGameMenuController(GameController gameMenuController) {
        this.gameMenuController = gameMenuController;
    }

    public GameController getGameMenuController() {
        return gameMenuController;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public GameMap getMap() {
        return map;
    }

    public int getInitialGolds() {
        return initialGolds;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public boolean isPublic() {
        return isPublic;
    }
    public boolean checkTheTime(){
        long lng = System.currentTimeMillis();
        if(lng - creationTime > 300*1000) return true;
        return false;
    }
    public GroupChat getGroupChat() {
            groupChat = new GroupChat(admin.getUserName()+" game",new HashSet<>((Collection) admin));
        return groupChat;
    }
}

