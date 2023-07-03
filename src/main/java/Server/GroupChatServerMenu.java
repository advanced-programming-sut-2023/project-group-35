package Server;

import model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;

public class GroupChatServerMenu {
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private User currentUser;
    private GroupChat groupChat;

    public GroupChatServerMenu(DataOutputStream dataOutputStream, DataInputStream dataInputStream, User currentUser) {
        this.currentUser = currentUser;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        groupChat = null;
    }

    public void startGroupChat() {
        try {
             startGroup();
            Matcher matcher;
            while (true) {
                String input = dataInputStream.readUTF();
                if (RegEx.getMatcher(input, RegEx.SHOW_MESSAGES) != null){
                    dataOutputStream.writeUTF(showMessageInGroup(groupChat));
                    dataOutputStream.flush();
                }
                else if ((matcher = RegEx.getMatcher(input, RegEx.SEND_MESSAGE)) != null){
                    dataOutputStream.writeUTF(sendMessageInGroup(groupChat,currentUser,matcher.group("message")));
                    dataOutputStream.flush();
                }
                else if ((matcher = RegEx.getMatcher(input, RegEx.DELETE_MESSAGE)) != null) {
                    dataOutputStream.writeUTF(deleteMessageInGroup(matcher.group("id"),currentUser,groupChat));
                    dataOutputStream.flush();
                }
                else if ((matcher = RegEx.getMatcher(input, RegEx.EDIT_MESSAGE)) != null){
                    dataOutputStream.writeUTF(editMessageInGroup(matcher.group("id"), matcher.group("newContent"),currentUser,groupChat));
                    dataOutputStream.flush();
                }
                else if (input.matches("\\s*exit\\s*")) {
                    dataOutputStream.writeUTF("Main Menu");
                    dataOutputStream.flush();
                    return;
                }else if(input.equals("where")){
                    dataOutputStream.writeUTF("Group Chat");
                }else{
                    dataOutputStream.writeUTF("invalid input");
                    dataOutputStream.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startGroup() throws IOException {
        GroupChat aim;
        String input;
        int flag = 0;
        while (true){
        try {
            dataOutputStream.writeUTF("Enter name of The Group");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            input = dataInputStream.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            aim = GlobalChat.getGroupChatByName(input);
            if(aim == null) {
                dataOutputStream.writeUTF("Such Group Doesnt Exist!");
                dataOutputStream.flush();
            }
            else{
                groupChat = aim;
                break;
            }
            dataOutputStream.writeUTF("Do You Want To Create One?y/n");
            dataOutputStream.flush();
            input = dataInputStream.readUTF();
            if(input.equals("n"))
                break;
            else {
                HashSet<User> newGroup = new HashSet<>();
                newGroup.add(currentUser);
                while (true) {
                    dataOutputStream.writeUTF("Do You Want To Add Another Member?y/n");
                    dataOutputStream.flush();
                    input = dataInputStream.readUTF();
                    if(input.equals("n")){
                        dataOutputStream.writeUTF("Enter name of Group");
                        dataOutputStream.flush();
                        input = dataInputStream.readUTF();
                        GroupChat newGroupChat = new GroupChat(input,newGroup);
                        GlobalChat.groupChats.add(newGroupChat);
                        GroupChat.saveTheDataOfGroups();
                        this.groupChat = newGroupChat;
                        flag = 1;
                        break;
                    }
                    else{
                        User userToBeAdd;
                        if((userToBeAdd = User.getUserByUsername(input)) == null) {
                            dataOutputStream.writeUTF("This User Doesn't Exist!");
                            dataOutputStream.flush();
                            continue;
                        }
                        newGroup.add(userToBeAdd);
                    }
                }
            }
            if(flag > 0.5) break;
        }
    }
    public String editMessageInGroup(String idStr,String newMessage,User loggedIn,GroupChat groupChat){
        try {
            int id = Integer.parseInt(idStr);
            SentMessage sentMessageWanted;
            if((sentMessageWanted = GroupChat.getSentMessageByIdGroupVersion(id,groupChat)) == null)
                    return "id is invalid";
            else if(!sentMessageWanted.getSender().equals(loggedIn))
                    return "this message is not yours";
            else{
                    sentMessageWanted.setMessage(newMessage);
                    return "successful";
            }
        } catch (NumberFormatException e) {
            return e.getMessage();
        }
    }
    public String deleteMessageInGroup(String idStr,User loggedIn,GroupChat groupChat){
        try {
            int id = Integer.parseInt(idStr);
            SentMessage sentMessageWanted;
            if((sentMessageWanted = GroupChat.getSentMessageByIdGroupVersion(id,groupChat)) == null)
                return "id is invalid";
            else if(!sentMessageWanted.getSender().equals(loggedIn))
                return "this message is not yours";
            else{
                groupChat.messages.remove(sentMessageWanted);
                return "successful";
            }
        } catch (NumberFormatException e) {
            return e.getMessage();
        }

    }
    public String sendMessageInGroup(GroupChat groupChat,User loggedIn,String message){
        int id;
        if (GlobalChat.sentMessages.size() == 0) id = 1;
        else id = GlobalChat.sentMessages.get(GlobalChat.sentMessages.size()-1).getId() +1;
        SentMessage messageSex = new SentMessage(message,loggedIn,id);
        if(groupChat.userHashSet.contains(loggedIn))
            groupChat.messages.add(messageSex);
        return "message has been sent";
    }
    public String showMessageInGroup(GroupChat groupChat){
        ArrayList<SentMessage> messages = GlobalChat.getGroupChatByName(groupChat.nameOfGroup).messages;
        StringBuilder str = new StringBuilder();
        for (SentMessage message : messages) {
            if(!message.isSeen) message.setSeen(true);
            str.append(message).append("\n");
        }
        return str.toString();
    }


}
