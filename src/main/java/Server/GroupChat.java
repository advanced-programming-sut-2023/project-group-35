package Server;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

import static Server.GlobalChat.groupChats;

public class GroupChat {
    public final ArrayList<SentMessage> messages = new ArrayList<>();
    public final HashSet<User> userHashSet;

    public String nameOfGroup;
    static{
        loadTheDataOfGroups();
    }

    public GroupChat(String nameOfGroup,HashSet<User> userHashSet) {
        this.nameOfGroup = nameOfGroup;
        this.userHashSet = userHashSet;
    }

    public String getNameOfGroup() {
        return nameOfGroup;
    }

    public static void saveTheDataOfGroups() {
        Gson gson = new Gson();
        String json = gson.toJson(groupChats);
        try {
            FileWriter myWriter = new FileWriter("PublicGroupChats.json");
            myWriter.write(json);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadTheDataOfGroups() {
        Reader reader;
        try {
            reader = new FileReader("PublicGroupChats.json");
        } catch (FileNotFoundException e) {
            return;
        }
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
        for (JsonElement jsonElement : jsonArray)
            groupChats.add(gson.fromJson(jsonElement, GroupChat.class));
    }
    public static SentMessage getSentMessageByIdGroupVersion(int id,GroupChat groupChat){
        for(SentMessage sentMessage:groupChat.messages){
            if(sentMessage.getId() == id) return sentMessage;
        }
        return null;
    }
    public static boolean deleteMessage(SentMessage message, User currentUser,GroupChat groupChat) {
        if (!message.getSender().getUserName().equals(currentUser.getUserName()))
            return false;
        groupChat.messages.remove(message);
        saveTheDataOfGroups();
        return true;
    }


    public static boolean editMessage(SentMessage message, User currentUser, String newMessage) {
        if (!message.getSender().getUserName().equals(currentUser.getUserName()))
            return false;
        message.setMessage(newMessage);
        saveTheDataOfGroups();
        return true;
    }
    public static void sendMessage(SentMessage message,GroupChat groupChat) {
        groupChat.messages.remove(message);
        saveTheDataOfGroups();
    }
    public static SentMessage getSentMessageById(int id,GroupChat groupChat){
        for(SentMessage sentMessage: groupChat.messages){
            if(sentMessage.getId() == id) return sentMessage;
        }
        return null;
    }



}
