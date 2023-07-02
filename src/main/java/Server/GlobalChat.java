package Server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import model.User;

import java.io.*;
import java.util.ArrayList;

public class GlobalChat {
    public static final ArrayList<SentMessage> sentMessages = new ArrayList<>();
    public static final ArrayList<GroupChat> groupChats = new ArrayList<>();
    static {
        loadTheDataOfMessages();
    }
    public static void saveTheDataOfMessages() {
        Gson gson = new Gson();
        String json = gson.toJson(sentMessages);
        try {
            FileWriter myWriter = new FileWriter("PublicChatMessages.json");
            myWriter.write(json);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadTheDataOfMessages() {
        Reader reader;
        try {
            reader = new FileReader("PublicChatMessages.json");
        } catch (FileNotFoundException e) {
            return;
        }
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
        for (JsonElement jsonElement : jsonArray)
            sentMessages.add(gson.fromJson(jsonElement, SentMessage.class));
    }
    public static boolean deleteMessage(SentMessage message, User currentUser) {
        if (!message.getSender().getUserName().equals(currentUser.getUserName()))
            return false;
        sentMessages.remove(message);
        saveTheDataOfMessages();
        return true;
    }


    public static boolean editMessage(SentMessage message, User currentUser, String newMessage) {
        if (!message.getSender().getUserName().equals(currentUser.getUserName()))
            return false;
        message.setMessage(newMessage);
        saveTheDataOfMessages();
        return true;
    }
    public static void sendMessage(SentMessage message) {
        sentMessages.add(message);
        saveTheDataOfMessages();
    }
    public static SentMessage getSentMessageById(int id){
        for(SentMessage sentMessage:sentMessages){
            if(sentMessage.getId() == id) return sentMessage;
        }
        return null;
    }
    public static GroupChat getGroupChatByName(String name){
        for(GroupChat sentMessage:groupChats){
            if(sentMessage.getNameOfGroup().equals(name)) return sentMessage;
        }
        return null;
    }



}
