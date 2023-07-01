package Server;

import model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
                if (GlobalChatCommands.getMatcher(input, GlobalChatCommands.SHOW_MESSAGES) != null){
                    dataOutputStream.writeUTF(showMessagesPrivateChat());
                    dataOutputStream.flush();
                }
                else if ((matcher = GlobalChatCommands.getMatcher(input, GlobalChatCommands.SEND_MESSAGE)) != null){
                    dataOutputStream.writeUTF(sendMessagePrivate(matcher.group("message")));
                    dataOutputStream.flush();
                }
                else if ((matcher = GlobalChatCommands.getMatcher(input, GlobalChatCommands.DELETE_MESSAGE)) != null) {
                    dataOutputStream.writeUTF(deleteMessage(matcher.group("id")));
                    dataOutputStream.flush();
                }
                else if ((matcher = GlobalChatCommands.getMatcher(input, GlobalChatCommands.EDIT_MESSAGE)) != null){
                    dataOutputStream.writeUTF(
                            editPrivateMessage(matcher.group("id"), matcher.group("newContent")));
                    dataOutputStream.flush();
                }
                else if (input.matches("\\s*exit\\s*")) {
                    dataOutputStream.writeUTF("enter main menu");
                    dataOutputStream.flush();
                    return;
                } else{
                    dataOutputStream.writeUTF("invalid input");
                    dataOutputStream.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    public void editMessageInGroup(){

    }
    public void deleteMessageInGroup(){

    }
    public void sendMessageInGroup(){

    }
    public void showMessageInGroup(){

    }


}
