package Server;

import model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;

public class PrivateChatMenu {
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private User currentUser;
    private GroupChat privateChat;
    private GroupChat privateChatForTarget;

    public PrivateChatMenu(DataOutputStream dataOutputStream, DataInputStream dataInputStream, User currentUser) {
        this.currentUser = currentUser;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        privateChat = null;
    }
    public void privateChat() {
        try {
            startPV();
            Matcher matcher;
            while (true) {
                String input = null;
                try {
                    input = dataInputStream.readUTF();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (RegEx.getMatcher(input, RegEx.SHOW_MESSAGES) != null)
                    dataOutputStream.writeUTF(showMessageInPV(privateChat));
                else if ((matcher = RegEx.getMatcher(input, RegEx.SEND_MESSAGE)) != null)
                    dataOutputStream.writeUTF(sendMessageInPV(privateChat,privateChatForTarget,currentUser,matcher.group("message")));
                else if ((matcher = RegEx.getMatcher(input, RegEx.DELETE_MESSAGE)) != null)
                    dataOutputStream.writeUTF(deleteMessageInPV(matcher.group("id"),currentUser,privateChat,privateChatForTarget,true));
                else if ((matcher = RegEx.getMatcher(input, RegEx.EDIT_MESSAGE)) != null)
                    dataOutputStream.writeUTF(editMessageInPV(matcher.group("id"), matcher.group("newContent"),currentUser,privateChat,privateChatForTarget));
                else if((matcher = RegEx.getMatcher(input,RegEx.DELETE_FOR_ME)) != null)
                    dataOutputStream.writeUTF(deleteMessageInPV(matcher.group("id"),currentUser,privateChat,privateChatForTarget,false));
                else if (input.matches("\\s*exit\\s*")) {
                    dataOutputStream.writeUTF("enter main menu");
                    return;
                } else
                    dataOutputStream.writeUTF("invalid input");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startPV() throws IOException {
        User targetUser = null;
        while (true){
            dataOutputStream.writeUTF("enter target user's username");
            dataOutputStream.flush();
            targetUser = User.getUserByUsername(dataInputStream.readUTF());
            if (targetUser!=null) break;
            else dataOutputStream.writeUTF("enter a valid username");
        }
        dataOutputStream.writeUTF("user : " + targetUser.getUserName() +" is selected");
        dataOutputStream.flush();
        HashSet<User> usersSet = new HashSet<>();
        usersSet.add(currentUser);
        usersSet.add(targetUser);
        privateChat = new GroupChat(targetUser.getUserName(),usersSet);
        privateChatForTarget = new GroupChat(currentUser.getUserName(),usersSet);
        GlobalChat.groupChats.add(privateChat);
        GlobalChat.groupChats.add(privateChatForTarget);
    }
    public String editMessageInPV(String idStr,String newMessage,User loggedIn,GroupChat groupChat1,GroupChat groupChat2){
        try {
            int id = Integer.parseInt(idStr);
            SentMessage sentMessageWanted;
            SentMessage sentMessage2;
            if((sentMessageWanted = GroupChat.getSentMessageByIdGroupVersion(id,groupChat1)) == null ||(sentMessage2 = GroupChat.getSentMessageByIdGroupVersion(id,groupChat2)) == null)
                return "id is invalid";
            else if(!sentMessageWanted.getSender().equals(loggedIn))
                return "this message is not yours";
            else{
                sentMessageWanted.setMessage(newMessage);
                sentMessage2.setMessage(newMessage);
                return "successful";
            }
        } catch (NumberFormatException e) {
            return e.getMessage();
        }
    }
    public String deleteMessageInPV(String idStr,User loggedIn,GroupChat groupChat1,GroupChat groupChat2,boolean isTwoSided){
        try {
            int id = Integer.parseInt(idStr);
            SentMessage sentMessageWanted;
            SentMessage sentMessageWanted2;
            if((sentMessageWanted = GroupChat.getSentMessageByIdGroupVersion(id,groupChat1)) == null || (sentMessageWanted2 = GroupChat.getSentMessageByIdGroupVersion(id,groupChat2)) == null)
                return "id is invalid";
            else{
                if(isTwoSided) {
                groupChat1.messages.remove(sentMessageWanted);
                groupChat2.messages.remove(sentMessageWanted2);
                }
                else{
                    groupChat1.messages.remove(sentMessageWanted);
                }
                return "successful";
            }
        } catch (NumberFormatException e) {
            return e.getMessage();
        }

    }
    public String sendMessageInPV(GroupChat groupChat1,GroupChat groupChat2,User loggedIn,String message){
        int id;
        if (GlobalChat.sentMessages.size() == 0) id = 1;
        else id = GlobalChat.sentMessages.get(GlobalChat.sentMessages.size()-1).getId() +1;
        SentMessage messageSex = new SentMessage(message,loggedIn,id);
        if(groupChat1.userHashSet.contains(loggedIn)) {
            groupChat1.messages.add(messageSex);
            groupChat2.messages.add(messageSex);
        }
        return "message has been sent";
    }
    public String showMessageInPV(GroupChat groupChat){
        ArrayList<SentMessage> messages = groupChat.messages;
        StringBuilder str = new StringBuilder();
        for (SentMessage message : messages) {
            if(!message.isSeen) message.setSeen(true);
            str.append(message).append("\n");
        }
        return str.toString();
    }


}
