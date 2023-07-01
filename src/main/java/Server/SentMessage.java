package Server;

import model.User;

import java.util.Calendar;

public class SentMessage {
    public String message;
    public User sender;
    public String time;
    public boolean isSeen;
    public String emojiAddress;
    public int id;

    public SentMessage(String message, User sender, int id) {
        this.message = message;
        this.sender = sender;
        this.isSeen = false;
        this.emojiAddress = null;
        this.id = id;
        Calendar calendar = Calendar.getInstance();
        int minutes = calendar.get(Calendar.MINUTE);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        String minutesInM;
        String HourInM;
        if (minutes < 10) minutesInM = "0" + minutes;
        else minutesInM = Integer.toString(minutes);
        if (hours < 10) HourInM = "0" + hours;
        else HourInM = Integer.toString(hours);
        this.time = HourInM + ":" + minutesInM;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public String getEmojiAddress() {
        return emojiAddress;
    }

    public void setEmojiAddress(String emojiAddress) {
        this.emojiAddress = emojiAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
