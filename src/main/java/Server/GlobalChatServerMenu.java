package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;

import model.User;

public class GlobalChatServerMenu {
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    User loggedInUser;

    public GlobalChatServerMenu(DataOutputStream dataOutputStream, DataInputStream dataInputStream, User loggedInUser) {
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
        this.loggedInUser = loggedInUser;
    }
    public void startGlobal(){
        try {
            try {
                dataOutputStream.writeUTF("Global Chat");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Matcher matcher;
            while (true) {
                String input = dataInputStream.readUTF();
                if (RegEx.getMatcher(input, RegEx.SHOW_MESSAGES) != null)
                    dataOutputStream.writeUTF(showMessagesGlobalChat(loggedInUser));
                else if ((matcher = RegEx.getMatcher(input,RegEx.SEND_MESSAGE)) != null) {
                    dataOutputStream.writeUTF(sendMessageGlobal(loggedInUser, matcher.group("message")));
                    dataOutputStream.writeUTF(showMessagesGlobalChat(loggedInUser));
                }
                else if ((matcher = RegEx.getMatcher(input,RegEx.DELETE_MESSAGE)) != null) {
                    dataOutputStream.writeUTF(deleteGlobalMessage(loggedInUser, Integer.parseInt(matcher.group("id"))));
                    dataOutputStream.writeUTF(showMessagesGlobalChat(loggedInUser));
                }
                else if ((matcher = RegEx.getMatcher(input,RegEx.EDIT_MESSAGE)) != null) {
                    dataOutputStream.writeUTF(editGlobalMessage(matcher.group("newContent"), loggedInUser, Integer.parseInt(matcher.group("id"))));
                    dataOutputStream.writeUTF(showMessagesGlobalChat(loggedInUser));
                }
                else if (input.matches("\\s*exit\\s*")) {
                    dataOutputStream.writeUTF("Main Menu");
                    return;
                }else if(input.equals("where")){
                    dataOutputStream.writeUTF("Global Chat");
                }
                else
                    dataOutputStream.writeUTF("invalid input");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String showMessagesGlobalChat(User currentUser) {
        for (SentMessage message : GlobalChat.sentMessages){
            if (!message.getSender().equals(currentUser)) message.setSeen(true);
        }
        ArrayList<SentMessage> messages = GlobalChat.sentMessages;
        StringBuilder str = new StringBuilder();
        for(SentMessage message : messages){
            str.append(message).append("\n");
        }
        return str.toString();
    }
    private String sendMessageGlobal(User currentUser,String message){
        int id;
        if (GlobalChat.sentMessages.size() == 0) id = 1;
        else id = GlobalChat.sentMessages.get(GlobalChat.sentMessages.size()-1).getId() +1;
        SentMessage sentMessageToAdd = new SentMessage(message,currentUser,id);
        GlobalChat.sendMessage(sentMessageToAdd);
        return "Message Was Sent!";
    }
    private String editGlobalMessage(String newMessage,User currentUser,int idOfMessage){
        SentMessage sentMessageToCheck = GlobalChat.getSentMessageById(idOfMessage);
        if(sentMessageToCheck == null) return "No Such Message Exists!";
        else if(!sentMessageToCheck.getSender().equals(currentUser)) return"You Don't Have The Access!";
        GlobalChat.getSentMessageById(idOfMessage).setMessage(newMessage);
        return "Success!";
    }
    private String deleteGlobalMessage(User currentUser,int idOfMessage){
        SentMessage sentMessageToCheck = GlobalChat.getSentMessageById(idOfMessage);
        if(sentMessageToCheck == null) return "No Such Message Exists!";
        else if(!sentMessageToCheck.getSender().equals(currentUser)) return"You Don't Have The Access!";
        GlobalChat.sentMessages.remove(GlobalChat.getSentMessageById(idOfMessage));
        return "Success!";
    }

}
