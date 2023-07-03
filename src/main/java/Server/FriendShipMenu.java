package Server;

import model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class FriendShipMenu {
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    User loggedInUser;
    User targetUser;

    public FriendShipMenu(DataOutputStream dataOutputStream, DataInputStream dataInputStream, User loggedInUser) {
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
        this.loggedInUser = loggedInUser;
        targetUser = null;
    }
    public void startFriendShip() throws IOException {
            dataOutputStream.writeUTF("Friendship Menu");
            dataOutputStream.flush();
        Matcher matcher;
        while(true){
            String input = dataInputStream.readUTF();
            if((matcher = RegEx.getMatcher(input,RegEx.SHOW_PENDING)) != null){
                dataOutputStream.writeUTF(showPending(loggedInUser));
                dataOutputStream.flush();
                while(true){
                    input = dataInputStream.readUTF();
                    if((matcher = RegEx.getMatcher(input, RegEx.SHOW_DETAILS)) != null){
                        dataOutputStream.writeUTF(showDetails(Integer.parseInt(matcher.group("name")),loggedInUser.getThoseInPendingNames()));
                    }
                    else if((matcher = RegEx.getMatcher(input, RegEx.REJECT_REQUEST)) != null){
                        dataOutputStream.writeUTF(reject(Integer.parseInt(matcher.group("name")),loggedInUser.getThoseInPendingNames()));
                    } else if ((matcher = RegEx.getMatcher(input, RegEx.ACCEPT_REQUEST)) != null) {
                        dataOutputStream.writeUTF(accept(Integer.parseInt(matcher.group("name")),loggedInUser.getThoseInPendingNames()));
                    } else if (input.equals("Back")) {
                        break;
                    }
                    else if(input.equals("where")){
                        dataOutputStream.writeUTF("Friendship:Pendings");
                    }
                }
            }
            else if((matcher = RegEx.getMatcher(input,RegEx.SHOW_FREINDS)) != null){
                dataOutputStream.writeUTF(showFriends(loggedInUser));
                dataOutputStream.flush();
                while(true){
                    input = dataInputStream.readUTF();
                    if((matcher = RegEx.getMatcher(input, RegEx.SHOW_DETAILS)) != null){
                        dataOutputStream.writeUTF(showDetails(Integer.parseInt(matcher.group("name")),loggedInUser.friendsNames));
                    }
                    else if(input.equals("where")){
                        dataOutputStream.writeUTF("Friendship:Friends");
                    }
                     else if (input.equals("Back")) {
                        break;
                    }
                }
            }
            else if((matcher = RegEx.getMatcher(input,RegEx.SEND_REQUEST)) != null){
                dataOutputStream.writeUTF(sendRequest(matcher.group("name")));
                dataOutputStream.flush();
                while(targetUser != null){
                    input = dataInputStream.readUTF();
                    if((matcher = RegEx.getMatcher(input, RegEx.SHOW_DETAILS)) != null){
                        dataOutputStream.writeUTF(showDetails(targetUser));
                    }
                    else if(input.equals("where")){
                        dataOutputStream.writeUTF("Friendship:sending");
                    }
                    else if (input.equals("Back")) {
                        break;
                    }
                }
            }
            else if(input.equals("where")){
                dataOutputStream.writeUTF("FriendShip Menu");
            }
            else if(input.equals("Exit"))
                break;
        }
    }
    public String showPending(User loggedIn){
        String str = "";
        int i = 0;
        for(String string:loggedIn.thoseInPendingNames){
            i++;
            User user = User.getUserByUsername(string);
            if(user != null)
            str += "\n"+user.getUserName()+" "+user.lastEntrance+" "+user.nickName;
        }
        return str;
    }
    public String showFriends(User loggedIn){
        String str = "";
        int i = 0;
        for(String string:loggedIn.friendsNames){
            i++;
            User user = User.getUserByUsername(string);
            if(user != null)
                str += "\n"+user.getUserName()+" "+user.lastEntrance+" "+user.nickName;
        }
        return str;
    }
    public String showDetails(int number, ArrayList<String> arrayList){
        User user = User.getUserByUsername(arrayList.get(number-1));
        return user.getUserName()+" "+user.lastEntrance+" "+user.nickName;
    }
    public String showDetails(User ussr){
        return ussr.getUserName()+" "+ussr.lastEntrance+" "+ussr.nickName;
    }
    public String accept(int number,ArrayList<String>arrayList){
        User user = User.getUserByUsername(arrayList.get(number-1));
        targetUser = user;
        loggedInUser.thoseInPendingNames.remove(user.getUserName());
        loggedInUser.friendsNames.add(user.getUserName());
        targetUser.friendsNames.add(loggedInUser.getUserName());
        return "Success";
    }
    public String reject(int number,ArrayList<String> arrayList){
        User user = User.getUserByUsername(arrayList.get(number-1));
        targetUser = user;
        loggedInUser.thoseInPendingNames.remove(user.getUserName());
        return "Success";
    }
    public String sendRequest(String name){
        targetUser = User.getUserByUsername(name);
        if(targetUser == null){
            return "No Such User Exists";
        }
        targetUser.thoseInPendingNames.add(loggedInUser.getUserName());
        return "Success";
    }
}
