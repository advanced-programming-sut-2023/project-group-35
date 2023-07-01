package Server;

import com.beust.ah.A;
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
    public final HashSet<User> userHashSet = new HashSet<>();
    static{
        loadTheDataOfGroups();
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



}
